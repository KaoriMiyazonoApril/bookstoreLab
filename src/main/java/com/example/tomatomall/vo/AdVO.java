package com.example.tomatomall.vo;

import com.example.tomatomall.po.Ad;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdVO {
    private String id,title,content,imgUrl,productId;

    public Ad toPO(){
        Ad a=new Ad();
        if(id!=null){
            a.setId(Integer.parseInt(id));
        }
        if(productId!=null){
            a.setProductId(Integer.parseInt(productId));
        }
        a.setTitle(title);
        a.setContent(content);
        a.setImage(imgUrl);
        return a;
    }
}
