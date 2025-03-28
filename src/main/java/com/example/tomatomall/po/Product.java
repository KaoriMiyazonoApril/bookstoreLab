package com.example.tomatomall.po;

import com.example.tomatomall.util.ProductSet;
import com.example.tomatomall.vo.ProductVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Product {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name="title")
    private String title;

    @Column(name="price")
    private Double price;

    @Column(name="rate")
    private Float rate;

    @Column(name="description")
    private String description;

    @Column(name="cover")
    private String cover;

    @Column(name = "detail")
    private String detail;

    @Embedded
    @Column(name="specifications")
    public ProductSet specifications;

    @Column(name="amount")
    private Integer amount;

    @Column(name="frozen")
    private Integer frozen;

    public ProductVO toVO(){
        ProductVO p=new ProductVO();
        p.setId(this.id);
        p.setPrice(this.price);
        p.setRate(this.rate);
        p.setTitle(this.title);
        p.setDescription(this.description);
        p.setCover(this.cover);
        p.setDetail(this.detail);
        p.setSpecifications(this.specifications);
        p.setFrozen(this.frozen);
        p.setAmount(this.amount);
        return p;
    }
}
