package com.example.tomatomall.vo;
import com.example.tomatomall.po.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductVO {
    private String id;
    private String title,description,cover,detail;
    private Double price;
    private Double rate;
    private List<SpecificationVO> specifications;

    public Product toPO(){
        Product p=new Product();
        p.setId(Integer.parseInt(this.id));
        p.setTitle(this.title);
        p.setDescription(this.description);
        p.setCover(this.cover);
        p.setDetail(this.detail);
        p.setPrice(this.price);
        p.setRate(this.rate);
        return p;
    }
}
