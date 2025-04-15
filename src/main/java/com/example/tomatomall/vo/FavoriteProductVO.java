package com.example.tomatomall.vo;

import com.example.tomatomall.po.FavoriteProduct;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FavoriteProductVO {
    private Integer id,productId,userId;

    public FavoriteProduct toPO(){
        FavoriteProduct f=new FavoriteProduct();
        f.setId(id);
        f.setProductId(productId);
        f.setUserId(userId);
        return f;
    }
}
