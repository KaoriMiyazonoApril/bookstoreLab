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
    private String telephone;
    private String location;
    private String avatar;
    private String password;
    private String username;


    public Account toPO(){
        Account account = new Account();
        account.setId(this.id);
        account.setName(this.name);
        account.setEmail(this.email);
        account.setTelephone(this.telephone);
        account.setLocation(this.location);
        account.setAvatar(this.avatar);
        account.setPassword(this.password);
        account.setUsername(this.username);
        account.setLocation(this.location);
        return account;
    }

}
