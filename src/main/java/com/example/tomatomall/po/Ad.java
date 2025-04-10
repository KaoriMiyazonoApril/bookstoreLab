package com.example.tomatomall.po;

import com.example.tomatomall.vo.AdVO;
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

    public AdVO toVO(){
        AdVO a=new AdVO();
        a.setId(id.toString());
        a.setTitle(this.title);
        a.setContent(this.content);
        a.setImgUrl(this.image);
        a.setProductId(productId.toString());
        return a;
    }
}
