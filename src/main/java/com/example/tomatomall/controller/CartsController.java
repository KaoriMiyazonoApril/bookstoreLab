package com.example.tomatomall.controller;

import com.example.tomatomall.service.CartsService;
import com.example.tomatomall.vo.*;
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
        return Response.buildSuccess(cartsService.
                addCart(request.getProductId(), request.getQuantity()));
    }

    //从当前用户的购物车中删除指定的商品
    @DeleteMapping("/{cartItemId}")
    public Response<Boolean> deleteCart(@PathVariable String cartItemId) {
        return Response.buildSuccess(cartsService.deleteCart(cartItemId));
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
}
