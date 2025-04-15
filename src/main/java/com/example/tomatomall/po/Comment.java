package com.example.tomatomall.po;

import com.example.tomatomall.vo.CommentVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name="comment")
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Comment {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "userId")
    private Integer userId;

    @Column(name = "productId")
    private Integer productId;

    @Column(name = "detail")
    private String detail;

    public CommentVO toVO(){
        CommentVO c=new CommentVO();
        c.setId(this.id);
        c.setDetail(this.detail);
        c.setUserId(this.userId);
        c.setProductId(this.productId);
        return c;
    }
}
