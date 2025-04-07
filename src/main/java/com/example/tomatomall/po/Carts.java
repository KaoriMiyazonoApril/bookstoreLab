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
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Account account;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Product product;

    @Setter
    @Column(name = "user_id")
    private Integer userId;

    @Setter
    @Column(name = "product_id")
    private Integer productId;

    @Setter
    @Column(name = "quantity")
    private Integer quantity;

    public CartsVO toVO() {
        CartsVO cartsVO = new CartsVO();
        cartsVO.setCartItemId(cartItemId);
        cartsVO.setQuantity(this.quantity);
        cartsVO.setUserId(this.userId);
        cartsVO.setProductId(this.productId);
        return cartsVO;
    }
}
