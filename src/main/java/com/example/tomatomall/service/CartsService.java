package com.example.tomatomall.service;

import com.example.tomatomall.vo.AddCartResultVO;
import java.util.List;

public interface CartsService {
    AddCartResultVO addCart(Integer productId, Integer quantity);
    Boolean deleteCart(Integer productId);
    Boolean updateCart(Integer productId, Integer quantity);
    List<AddCartResultVO> getCart();
}
