package com.example.tomatomall.service;

import com.example.tomatomall.RRVO.AddCartResultVO;
import com.example.tomatomall.RRVO.CartResultVO;
import com.example.tomatomall.RRVO.CheckoutRequestVO;
import com.example.tomatomall.RRVO.CheckoutResultVO;

public interface CartsService {
    AddCartResultVO addCart(String productId, Integer quantity);
    String deleteCart(String productId);
    Boolean updateCart(String cartItemId, Integer quantity);
    CartResultVO getCart();
    CheckoutResultVO checkout(CheckoutRequestVO request);
}
