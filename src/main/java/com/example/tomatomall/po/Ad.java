package com.example.tomatomall.po;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "advertisements")
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Ad {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name="title")
    private String title;

    @Column(name="content")
    private String content;

    @Column(name="image")
    private String image;

    @Column(name="product_id")
    private Integer productId;//作为外键,后续记得要修改
}
