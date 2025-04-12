package com.example.tomatomall.controller;

import com.example.tomatomall.RRVO.*;
import com.example.tomatomall.service.CartsService;
import com.example.tomatomall.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartsController {

    @Autowired
    CartsService cartsService;

    //将商品添加至当前用户的购物车
    @PostMapping
    public Response<AddCartResultVO> addCart(@RequestBody AddCartRequestVO request) {
        Response<AddCartResultVO> response = Response.buildSuccess(cartsService.
                addCart(request.getProductId(), request.getQuantity()));
        response.setMsg(null);
        return response;
    }

    //从当前用户的购物车中删除指定的商品
    @DeleteMapping("/{cartItemId}")
    public Response<String> deleteCart(@PathVariable String cartItemId) {
        Response<String> response = Response.buildSuccess(cartsService.deleteCart(cartItemId));
        if(response.getData()==null){
            response.setCode("400");
            response.setMsg("购物车商品不存在");
            return response;
        }
        response.setMsg(null);
        return response;
    }

    //修改当前用户的购物车中指定商品的数量
    @PatchMapping("/{cartItemId}")
    public Response<Boolean> updateCart(@PathVariable String cartItemId,
                                        @RequestBody ReviseCartRequestVO request) {
        return Response.buildSuccess(cartsService.updateCart(cartItemId, request.getQuantity() ));
    }

    //获取当前用户的购物车信息
    @GetMapping
    public Response<CartResultVO> getCart() {
        return Response.buildSuccess(cartsService.getCart());
    }

    //提交订单
    @PostMapping("/checkout")
    public Response<CheckoutResultVO> checkout(@RequestBody CheckoutRequestVO request) {
        return Response.buildSuccess(cartsService.checkout(request));
    }
    //取消订单
    @DeleteMapping("/checkout/{orderId}")
    public Response<CheckoutResultVO> cancelOrder(@PathVariable String orderId) {
        return Response.buildSuccess(cartsService.cancelOrder(orderId));
    }
}
