package com.example.tomatomall.serviceImpl;

import com.example.tomatomall.RRVO.CheckoutRequestVO;
import com.example.tomatomall.RRVO.CheckoutResultVO;
import com.example.tomatomall.exception.TomatoMallException;
import com.example.tomatomall.po.*;
import com.example.tomatomall.repository.OrderDetailRepository;
import com.example.tomatomall.repository.OrderRepository;
import com.example.tomatomall.service.CartsService;
import com.example.tomatomall.service.ProductService;
import com.example.tomatomall.util.SecurityUtil;
import com.example.tomatomall.RRVO.AddCartResultVO;
import com.example.tomatomall.RRVO.CartResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.tomatomall.repository.CartsRepository;
import com.example.tomatomall.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartsServiceImpl implements CartsService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartsRepository cartsRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    // 新增商品到当前用户购物车
    @Override
    public AddCartResultVO addCart(String productId, Integer quantity) {
        Account currentUser = securityUtil.getCurrentUser();
        if (currentUser == null) {
            throw TomatoMallException.notLogin();
        }
        if (currentUser.getId() == null) {
            throw  TomatoMallException.invalidUserId();
        }
        Product product = productRepository.findById(Integer.valueOf(productId))
                .orElseThrow(TomatoMallException::ProductNotFound);
        Carts existingCartItem = cartsRepository.findByAccountAndProduct(
                currentUser,
                product
        );
        Integer stock=productService.getAmount(Integer.valueOf(productId)).getAmount();
        if(stock==null||stock==0){
            throw TomatoMallException.productSoldOut();
        }
        if(stock < quantity) {
            throw TomatoMallException.notEnoughStock();
        }

        if (existingCartItem != null) {
            if(stock < existingCartItem.getQuantity()+quantity){
                throw TomatoMallException.notEnoughStock();
            }
            existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
            cartsRepository.save(existingCartItem);
        }else{
            Carts carts = new Carts();
            carts.setAccount(currentUser);
            carts.setProduct(product);
            carts.setQuantity(quantity);
            cartsRepository.save(carts);
            existingCartItem = carts;
        }
        AddCartResultVO resultVO = new AddCartResultVO();
        resultVO.setCartItemId(String.valueOf(existingCartItem.getCartItemId()));
        resultVO.setProductId(productId);
        resultVO.setTitle(product.getTitle());
        resultVO.setPrice(product.getPrice());
        resultVO.setDescription(product.getDescription());
        resultVO.setCover(product.getCover());
        resultVO.setQuantity(existingCartItem.getQuantity());
        return resultVO;
    }

    // 从当前用户购物车删除商品
    @Override
    public String deleteCart(String cartItemId) {
        Account currentUser = securityUtil.getCurrentUser();
        if (currentUser == null) {
            throw TomatoMallException.notLogin();
        }
        if (currentUser.getId() == null) {
            throw TomatoMallException.invalidUserId();
        }

        Carts cartItem=cartsRepository.findByCartItemId(Integer.valueOf(cartItemId));
        if(cartItem==null){
            return null;
        }
        cartsRepository.delete(cartItem);
        return "删除成功";
    }

    // 更新购物车商品数量
    @Override
    public Boolean updateCart(String cartItemId, Integer quantity) {
        Account currentUser=securityUtil.getCurrentUser();
        if (currentUser == null) {
            throw TomatoMallException.notLogin();
        }
        if (currentUser.getId() == null) {
            throw TomatoMallException.invalidUserId();
        }
        Carts cartItem=cartsRepository.findByCartItemId(Integer.valueOf(cartItemId));
        if(cartItem==null){
            throw TomatoMallException.cartItemNotFound();
        }
        Product product=productRepository.findById(cartItem.getProduct().getId())
                .orElseThrow(TomatoMallException::ProductNotFound);
        Integer stock=productService.getAmount(product.getId()).getAmount();
        if(stock==null||stock==0){
            throw TomatoMallException.productSoldOut();
        }
        if(stock<quantity){
            throw TomatoMallException.notEnoughStock();
        }
        cartItem.setQuantity(quantity);
        cartsRepository.save(cartItem);
        return true;
    }

    // 获取当前用户购物车
    @Override
    public CartResultVO getCart() {
        Account currentUser = securityUtil.getCurrentUser();
        if (currentUser == null) {
            throw TomatoMallException.notLogin();
        }
        List<Carts> cartItems = cartsRepository.findByAccount(currentUser);
        if (cartItems == null) {
            throw TomatoMallException.getItemListFailed();
        }
        List<AddCartResultVO> result = new ArrayList<>();
        double totalAmount = 0;
        int total = 0;
        for (Carts cartItem : cartItems) {
            Product product = productRepository.findById(cartItem.getProduct().getId())
                    .orElseThrow(TomatoMallException::ProductNotFound);
            AddCartResultVO item = new AddCartResultVO();
            item.setCartItemId(String.valueOf(cartItem.getCartItemId()));
            item.setProductId(String.valueOf(cartItem.getProduct().getId()));
            item.setTitle(product.getTitle());
            item.setPrice(product.getPrice());
            item.setDescription(product.getDescription());
            item.setQuantity(cartItem.getQuantity());
            item.setCover(product.getCover());

            result.add(item);
            totalAmount += cartItem.getQuantity()*product.getPrice();
            total ++;
        }

        CartResultVO cartResult = new CartResultVO();
        cartResult.setItems(result);
        cartResult.setTotalAmount(totalAmount);
        cartResult.setTotal(total);
        return cartResult;
    }

    @Override
    public CheckoutResultVO checkout(CheckoutRequestVO request) {
        // 1. 获取当前用户
        Account currentUser = securityUtil.getCurrentUser();
        if (currentUser == null) {
            throw TomatoMallException.notLogin();
        }

        // 2. 获取购物车商品
        List<String> cartItemIds = request.getCartItemIds();

        // 3. 验证库存并计算总金额
        double totalAmount = 0;
        for (String cartItemId : cartItemIds) {
            Carts cartItem = cartsRepository.findByCartItemId(Integer.valueOf(cartItemId));
            if (cartItem == null) {
                throw TomatoMallException.cartItemNotFound();
            }
            Product product = cartItem.getProduct();
            if (product == null) {
                throw TomatoMallException.ProductNotFound();
            }
            if (productService.getAmount(product.getId()).getAmount() < cartsRepository.findByCartItemId
                    (Integer.valueOf(cartItemId)).getQuantity()) {
                System.out.println(productService.getAmount(product.getId()).getAmount());
                System.out.println(productService.getAmount(product.getId()).getFrozen());
                System.out.println(cartsRepository.findByCartItemId
                        (Integer.valueOf(cartItemId)).getQuantity());
                throw TomatoMallException.notEnoughStock();
            }

            totalAmount += cartsRepository.findByCartItemId
                    (Integer.valueOf(cartItemId)).getQuantity() * product.getPrice();
        }

        // 4. 创建订单
        Orders order = new Orders();
        order.setAccount(currentUser);
        order.setTotalAmount(totalAmount);
        order.setPaymentMethod(request.getPaymentMethod());
        order.setStatus("PENDING");
        order.setCreateTime(LocalDateTime.now());
        orderRepository.save(order);

        for (String cartItemId : request.getCartItemIds()) {
            Carts cartItem = cartsRepository.findByCartItemId(Integer.valueOf(cartItemId));
            if (cartItem != null) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrder(order);
                orderDetail.setProduct(cartItem.getProduct());
                orderDetail.setCarts(cartItem);
                orderDetailRepository.save(orderDetail);
            }
        }

        List<String> sucCartItemIds = new ArrayList<>();

        // 5. 锁定库存
        try {
            for (String cartItemId : cartItemIds) {
                productService.lockStock(cartsRepository.findByCartItemId(Integer.valueOf(cartItemId)).getProduct().getId(),
                        cartsRepository.findByCartItemId(Integer.valueOf(cartItemId)).getQuantity());
                sucCartItemIds.add(cartItemId);
            }
        } catch(TomatoMallException e){
            for (String sucCartItemId : sucCartItemIds) {
                productService.releaseStock(cartsRepository.findByCartItemId(Integer.valueOf(sucCartItemId)).getProduct().getId(),
                        cartsRepository.findByCartItemId(Integer.valueOf(sucCartItemId)).getQuantity());
            }
            throw e;
        }


        // 6. 返回结果
        CheckoutResultVO result = new CheckoutResultVO();
        result.setOrderId(order.getId().toString());
        result.setUsername(currentUser.getUsername());
        result.setTotalAmount(totalAmount);
        result.setPaymentMethod(request.getPaymentMethod());
        result.setCreateTime(order.getCreateTime().toString());
        result.setStatus(order.getStatus());

        return result;
    }

    @Override
    public CheckoutResultVO cancelOrder(String orderId) {
        // 1. 获取当前用户
        Account currentUser = securityUtil.getCurrentUser();
        if (currentUser == null) {
            throw TomatoMallException.notLogin();
        }

        // 2. 获取订单
        Orders order = orderRepository.findById(Integer.valueOf(orderId))
                .orElseThrow(TomatoMallException::OrderNotFound);

        // 3. 验证订单状态
        if (!"PENDING".equals(order.getStatus())) {
            throw TomatoMallException.OrderStatusError();
        }

        // 4. 释放库存
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrder(order);
        for (OrderDetail detail : orderDetails) {
            productService.releaseStock(detail.getProduct().getId(), detail.getCarts().getQuantity());
        }

        // 5. 更新订单状态
        order.setStatus("CANCELED");
        orderRepository.save(order);

        // 6. 返回结果
        CheckoutResultVO result = new CheckoutResultVO();
        result.setOrderId(order.getId().toString());
        result.setUsername(currentUser.getUsername());
        result.setTotalAmount(order.getTotalAmount());
        result.setPaymentMethod(order.getPaymentMethod());
        result.setCreateTime(order.getCreateTime().toString());
        result.setStatus(order.getStatus());

        return result;
    }
}
