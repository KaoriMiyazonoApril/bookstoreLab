package com.example.tomatomall.util;

import com.example.tomatomall.po.Product;

public class ProductSet {
    public String item,value;
    public Integer productId;

    public ProductSet(String item, String value, Integer id){
        this.item=item;
        this.value=value;
        this.productId=id;
    }

}
