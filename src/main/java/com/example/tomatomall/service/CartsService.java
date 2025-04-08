package com.example.tomatomall.service;

import com.example.tomatomall.RRVO.AddCartResultVO;
import com.example.tomatomall.RRVO.CartResultVO;

public interface CartsService {
    AddCartResultVO addCart(String productId, Integer quantity);
    Boolean deleteCart(String productId);
    Boolean updateCart(String cartItemId, Integer quantity);
    CartResultVO getCart();
}
