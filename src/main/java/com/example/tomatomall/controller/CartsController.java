package com.example.tomatomall.controller;

import com.example.tomatomall.service.CartsService;
import com.example.tomatomall.vo.AddCartRequest;
import com.example.tomatomall.vo.AddCartResultVO;
import com.example.tomatomall.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/cart")
public class CartsController {

    @Autowired
    CartsService cartsService;

    @PostMapping
    public Response<AddCartResultVO> addCart(@RequestBody AddCartRequest request) {
        return Response.buildSuccess(cartsService.
                addCart(request.getProductId(), request.getQuantity()));
    }
}
