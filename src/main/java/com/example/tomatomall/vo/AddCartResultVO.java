package com.example.tomatomall.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddCartResultVO {
    private String cartItemId;
    private String productId;
    private String title;
    private Double price;
    private String description;
    private String cover;
    private String detail;
    private Integer quantity;
}
