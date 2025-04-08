package com.example.tomatomall.RRVO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

// 用于请求当前用户购物车的所有商品的返回体
@Getter
@Setter
@NoArgsConstructor
public class CartResultVO {
    private List<AddCartResultVO> items;
    private Integer total;
    private Double totalAmount;
}
