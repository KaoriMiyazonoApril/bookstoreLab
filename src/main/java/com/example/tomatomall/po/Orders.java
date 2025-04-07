package com.example.tomatomall.po;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name="orders")
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderId")
    private Integer Id;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id", insertable = false, updatable = false)
    private Account account;

    @Column(name = "userId")
    private Integer userId;

    @Column(name = "total_amount")
    private Integer totalAmount;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "status")
    private String status;

    @Column(name = "create_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private String createTime;
}
