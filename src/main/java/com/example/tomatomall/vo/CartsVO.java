package com.example.tomatomall.vo;


import com.example.tomatomall.po.Carts;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartsVO {
    private Integer cartItemId;
    private Integer userId;
    private Integer productId;
    private Integer quantity;

    public Carts toPO() {
        Carts carts = new Carts();
        carts.setCartItemId(cartItemId);
        carts.setUserId(userId);
        carts.setProductId(productId);
        carts.setQuantity(quantity);
        return carts;
    }

}
