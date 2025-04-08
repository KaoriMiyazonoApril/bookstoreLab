package com.example.tomatomall.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 用于添加购物车的请求体
@Getter
@Setter
@NoArgsConstructor
public class AddCartRequestVO {
    private String productId;
    private Integer quantity;
}
