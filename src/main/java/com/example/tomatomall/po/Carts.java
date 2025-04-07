package com.example.tomatomall.po;

import com.example.tomatomall.vo.CartsVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name="carts")
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Carts {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "cartItemId")
    private Integer cartItemId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @Column(name = "quantity")
    private Integer quantity;

    public CartsVO toVO() {
        CartsVO cartsVO = new CartsVO();
        cartsVO.setCartItemId(cartItemId);
        cartsVO.setQuantity(this.quantity);
        if (this.product != null) {
            cartsVO.setProduct(this.product.toVO());
        }
        if (this.account != null) {
            cartsVO.setAccount(this.account.toVO());
        }
        return cartsVO;
    }
}
