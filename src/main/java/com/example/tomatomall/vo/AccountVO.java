package com.example.tomatomall.vo;

import com.example.tomatomall.po.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class AccountVO {
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String imgUrl;
    private String password;
    private String username;
    private Date createTime;

    public Account toPO(){
        Account account = new Account();
        account.setId(this.id);
        account.setName(this.name);
        account.setEmail(this.email);
        account.setPhone(this.phone);
        account.setAddress(this.address);
        account.setImgUrl(this.imgUrl);
        account.setPassword(this.password);
        account.setUsername(this.username);
        account.setCreateTime(this.createTime);
        return account;
    }

}
