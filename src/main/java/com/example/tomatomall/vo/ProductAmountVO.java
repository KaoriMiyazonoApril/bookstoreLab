package com.example.tomatomall.vo;

import com.example.tomatomall.po.ProductAmount;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductAmountVO {
    private Integer amount,frozen;
    private String id,productId;

    public ProductAmount toPO(){
        ProductAmount a=new ProductAmount();
        a.setId(Integer.parseInt(this.id));
        a.setProductId(Integer.parseInt(this.productId));
        a.setAmount(this.amount);
        a.setFrozen(this.frozen);
        return a;
    }
}
