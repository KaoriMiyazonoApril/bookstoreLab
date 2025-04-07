package com.example.tomatomall.vo;
import com.example.tomatomall.po.Account;
import com.example.tomatomall.po.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class ProductVO {
    private Integer id,amount,frozen;
    private String title,description,cover,detail;
    private Double price;
    private Float rate;
    private String item,value;

    public Product toPO(){
        Product p=new Product();
        p.setId(this.id);
        p.setTitle(this.title);
        p.setDescription(this.description);
        p.setCover(this.cover);
        p.setDetail(this.detail);
        p.setPrice(this.price);
        p.setRate(this.rate);
        p.setFrozen(this.frozen);
        p.setAmount(this.amount);
        p.setItem(this.item);
        p.setValue(this.value);
        return p;
    }
}
