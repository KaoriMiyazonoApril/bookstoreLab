package com.example.tomatomall.vo;

import com.example.tomatomall.po.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
public class AccountVO {
    private Integer id;
    private String username;
    private String password;
    @Getter
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;
    private String name;
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "邮箱格式不正确")
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
