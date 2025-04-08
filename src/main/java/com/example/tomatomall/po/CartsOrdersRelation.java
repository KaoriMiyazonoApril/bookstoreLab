package com.example.tomatomall.po;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name="carts_orders_relation")
@Getter
@Setter
@NoArgsConstructor
@Entity
public class CartsOrdersRelation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer Id;

    @ManyToOne
    @JoinColumn(name = "cartitem_id", referencedColumnName = "cartItemId", insertable = false, updatable = false)
    private Carts cartItem;

    @Column(name = "cartitem_id")
    private Integer cartItemId;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "orderId", insertable = false, updatable = false)
    private Orders order;

    @Column(name = "order_id")
    private Integer orderId;
}
