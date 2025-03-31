package com.example.tomatomall.vo;

import com.example.tomatomall.po.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountVO {
    private Integer id;
    private String username;
    private String password;
    private String phone;
    private String name;
    private String email;
    private String location;
    private String role ;
    private String avatar;

    public Account toPO() {
        Account account = new Account();
        account.setId(this.id);
        account.setName(this.name);
        account.setEmail(this.email);
        account.setPhone(this.phone);
        account.setLocation(this.location);
        account.setAvatar(this.avatar);
        account.setPassword(this.password);
        account.setUsername(this.username);
        account.setLocation(this.location);
        return account;
    }

}
