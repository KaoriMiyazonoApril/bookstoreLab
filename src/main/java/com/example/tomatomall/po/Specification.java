package com.example.tomatomall.po;

import com.example.tomatomall.vo.SpecificationVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name="specification")
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Specification {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name="product_id")
    private Integer productId;

    @Column(name="value")
    private String value;

    @Column(name="item")
    private String item;

    public SpecificationVO toVO(){
        SpecificationVO a=new SpecificationVO();
        a.setId(this.id.toString());
        a.setProductId(this.productId.toString());
        a.setItem(this.item);
        a.setValue(this.value);
        return a;
    }
}
