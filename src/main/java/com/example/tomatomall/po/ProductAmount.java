package com.example.tomatomall.po;
import com.example.tomatomall.vo.ProductAmountVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name="productamount")
@Getter
@Setter
@NoArgsConstructor
@Entity
public class ProductAmount {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name="product_id")
    private Integer productId;

    @Column(name="amount")
    private Integer amount;

    @Column(name="frozen")
    private Integer frozen;

    public ProductAmountVO toVO(){
        ProductAmountVO a=new ProductAmountVO();
        a.setId(this.id.toString());
        a.setProductId(this.productId.toString());
        a.setAmount(this.amount);
        a.setFrozen(this.frozen);
        return a;
    }

    public ProductAmount(Integer id,Integer am,Integer fr){
        this.productId=id;
        this.amount=am;
        this.frozen=fr;
    }
}
