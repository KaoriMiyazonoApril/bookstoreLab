package com.example.tomatomall.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CartResultVO {
    private List<AddCartResultVO> items;
    private Integer total;
    private Double totalAmount;
}
