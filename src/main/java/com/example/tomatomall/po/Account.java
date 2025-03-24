package com.example.tomatomall.po;

import com.example.tomatomall.vo.AccountVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

@Table(name = "account")
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Account {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "name")
    private String name;

    @Column(name = "imgUrl")
    private String imgUrl;

    @Column(name = "create_time")
    private Date createTime;

    public AccountVO toVO(){
        AccountVO accountVO = new AccountVO();
        accountVO.setId(this.id);
        accountVO.setUsername(this.username);
        accountVO.setPassword(this.password);
        accountVO.setEmail(this.email);
        accountVO.setPhone(this.phone);
        accountVO.setAddress(this.address);
        accountVO.setName(this.name);
        accountVO.setImgUrl(this.imgUrl);
        accountVO.setCreateTime(this.createTime);
        return accountVO;
    }
}
