package com.example.tomatomall.serviceImpl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.example.tomatomall.RRVO.ResultOrderPayVO;
import com.example.tomatomall.exception.TomatoMallException;
import com.example.tomatomall.po.OrderDetail;
import com.example.tomatomall.po.Orders;
import com.example.tomatomall.repository.OrderDetailRepository;
import com.example.tomatomall.repository.OrderRepository;
import com.example.tomatomall.service.AliPayService;
import com.example.tomatomall.service.CartsService;
import com.example.tomatomall.service.OrderService;
import com.example.tomatomall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CartsService cartsService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AliPayService aliPayService;

    @Autowired
    private ProductService productService;

    @Value("${alipay.alipay-public-key}")
    private String ALI_PUBLIC_KEY;

    @Autowired
    private OrderDetailRepository orderDetailRepository;


    @Override
    public ResultOrderPayVO createOrder(Integer orderId) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(TomatoMallException::OrderNotFound);

        if(!"PENDING".equals(order.getStatus())){
            throw TomatoMallException.OrderPayStatusError();
        }
        // 2. 调用支付宝服务生成支付表单
        String paymentForm = aliPayService.createPaymentForm(
                order.getId().toString(),
                order.getTotalAmount(),
                "订单支付"
        );

        // 3. 构建返回结果
        ResultOrderPayVO result = new ResultOrderPayVO();
        result.setPaymentForm(paymentForm);
        result.setOrderId(order.getId().toString());
        result.setTotalAmount(String.valueOf(order.getTotalAmount()));
        result.setPaymentMethod(order.getPaymentMethod());
        return result;
    }

    @Override
    public void updateOrderStatus(String orderId, String alipayTradeNo, String amount) {
        // 1. 查找订单
        Orders order = orderRepository.findById(Integer.parseInt(orderId))
                .orElseThrow(TomatoMallException::OrderNotFound);

        // 2. 验证订单状态（防止重复处理）
        if (!"PENDING".equals(order.getStatus())) {
            throw TomatoMallException.OrderStatusError();
        }

        // 3. 更新订单信息
        order.setStatus("PAID");
        order.setAlipayTradeNo(alipayTradeNo);
        order.setPaidAmount(new BigDecimal(amount));
        order.setPayTime(LocalDateTime.now());

        // 4. 保存更新
        orderRepository.save(order);
    }

    @Override
    public String handleAlipayNotify(HttpServletRequest request, HttpServletResponse response) throws IOException, AlipayApiException{
        // 1. 解析支付宝回调参数（通常是 application/x-www-form-urlencoded）
        Map<String, String> params = request.getParameterMap().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue()[0]));

        // 2. 验证支付宝签名（防止伪造请求）
        boolean signVerified = AlipaySignature.rsaCheckV1(params, ALI_PUBLIC_KEY, "UTF-8", "RSA2");
        if (!signVerified) {
            response.getWriter().print("fail"); // 签名验证失败，返回 fail
            return null;
        }

        // 3. 处理业务逻辑（更新订单、减库存等）
        String tradeStatus = params.get("trade_status");
        if ("TRADE_SUCCESS".equals(tradeStatus)) {
            String orderId = params.get("out_trade_no"); // 您的订单号
            String alipayTradeNo = params.get("trade_no"); // 支付宝交易号
            String amount = params.get("total_amount"); // 支付金额

            // 更新订单状态（注意幂等性，防止重复处理）
            updateOrderStatus(orderId, alipayTradeNo, amount);

            // 扣减库存（建议加锁或乐观锁）
            Orders order = orderRepository.findById(Integer.valueOf(orderId))
                    .orElseThrow(TomatoMallException::OrderNotFound);
            List<OrderDetail> orderTailList = orderDetailRepository.findByOrder(order);
            for (OrderDetail orderTail: orderTailList) {
                productService.reduceStock(orderTail.getProduct().getId(), orderTail.getCarts().getQuantity());
                //cartsService.deleteCart(String.valueOf(orderTail.getProduct().getId()));
            }
        }

        // 4. 必须返回纯文本的 "success"（支付宝要求）
        response.getWriter().print("success");
        return null;
    }


}
