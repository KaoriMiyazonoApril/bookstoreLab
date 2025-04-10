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
    @JoinColumn(name = "carts_item_id", referencedColumnName = "cart_item_id", insertable = false, updatable = false)
    private Carts carts;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id", insertable = false, updatable = false)
    private Orders order;

}
