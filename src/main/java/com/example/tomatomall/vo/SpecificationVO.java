package com.example.tomatomall.vo;

import com.example.tomatomall.po.Specification;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class SpecificationVO {
    private String id,productId;
    private String item,value;

    public Specification toPO(){
        Specification a=new Specification();
        a.setId(Integer.parseInt(this.id));
        a.setProductId(Integer.parseInt(this.productId));
        a.setItem(this.item);
        a.setValue(this.value);
        return a;
    }
}
