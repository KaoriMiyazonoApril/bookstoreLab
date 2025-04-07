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

    @PostMapping
    public Response<AddCartResultVO> addCart(@RequestBody AddCartRequestVO request) {
        return Response.buildSuccess(cartsService.
                addCart(request.getProductId(), request.getQuantity()));
    }

    @DeleteMapping("/{cartItemId}")
    public Response<Boolean> deleteCart(@PathVariable String cartItemId) {
        return Response.buildSuccess(cartsService.deleteCart(cartItemId));
    }

    @PatchMapping("/{cartItemId}")
    public Response<Boolean> updateCart(@PathVariable String cartItemId,
                                        @RequestBody ReviseCartRequestVO request) {
        return Response.buildSuccess(cartsService.updateCart(cartItemId, request.getQuantity() ));
    }

    @GetMapping
    public Response<CartResultVO> getCart() {
        return Response.buildSuccess(cartsService.getCart());
    }
}
