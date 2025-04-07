package com.example.tomatomall.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddCartResultVO {
    private Integer cartItemId;
    private Integer productId;
    private String title;
    private Double price;
    private String description;
    private String cover;
    private String detail;
    private Integer quantity;
}
