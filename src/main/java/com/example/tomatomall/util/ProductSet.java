package com.example.tomatomall.util;

import com.example.tomatomall.po.Product;

import javax.persistence.Embeddable;

@Embeddable
public class ProductSet {
    public String item,value;
    public Integer productId;

    public ProductSet(String item, String value, Integer id){
        this.item=item;
        this.value=value;
        this.productId=id;
    }

    public ProductSet() {

    }
}
