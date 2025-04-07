package com.example.tomatomall.vo;


import com.example.tomatomall.po.Carts;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
public class CartsVO {
    private Integer cartItemId;
    private ProductVO product;
    private AccountVO account;
    private Integer quantity;

    public Carts toPO() {
        Carts carts = new Carts();
        carts.setCartItemId(cartItemId);
        carts.setQuantity(quantity);
        if (product != null) {
            carts.setProduct(product.toPO());
        }
        if (account != null) {
            carts.setAccount(account.toPO());
        }
        return carts;
    }

}
