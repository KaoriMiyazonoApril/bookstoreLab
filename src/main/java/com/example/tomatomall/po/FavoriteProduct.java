package com.example.tomatomall.po;

import com.example.tomatomall.vo.FavoriteProductVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

@Table(name="favorite_product")
@Getter
@Setter
@NoArgsConstructor
@Entity
public class FavoriteProduct {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name="user_id")
    private Integer userId;

    @Column(name="product_id")
    private Integer productId;

    public FavoriteProductVO toVO(){
        FavoriteProductVO f=new FavoriteProductVO();
        f.setId(id);
        f.setProductId(productId);
        f.setUserId(userId);
        return f;
    }
}
