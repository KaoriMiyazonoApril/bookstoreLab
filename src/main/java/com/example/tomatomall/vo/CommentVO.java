package com.example.tomatomall.vo;

import com.example.tomatomall.po.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentVO {
    Integer id,userId,productId;
    String detail;

    public Comment toPO(){
        Comment c=new Comment();
        c.setId(this.id);
        c.setDetail(this.detail);
        c.setUserId(this.userId);
        c.setProductId(this.productId);
        return c;
    }
}
