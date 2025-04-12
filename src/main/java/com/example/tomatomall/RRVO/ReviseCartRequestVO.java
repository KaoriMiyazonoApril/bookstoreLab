package com.example.tomatomall.RRVO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//用于修改购物车商品数量的请求体
@Setter
@Getter
@NoArgsConstructor
public class ReviseCartRequestVO {
    private Integer quantity;
    private String cartItemId;
}
