package com.example.tomatomall.vo;

import com.example.tomatomall.po.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class AccountVO {
    private Integer id;
    @NotNull(message = "用户名不能为空")
    private String username;
    @NotNull(message = "密码不能为空")
    private String password;
    private String telephone;
    @NotNull(message = "姓名不能为空")
    private String name;
    private String email;
    private String location;
    @NotNull(message = "角色不能为空")
    private String role ;
    private String avatar;

    public Account toPO() {
        Account account = new Account();
        account.setId(this.id);
        account.setName(this.name);
        account.setEmail(this.email);
        account.setTelephone(this.telephone);
        account.setLocation(this.location);
        account.setAvatar(this.avatar);
        account.setPassword(this.password);
        account.setUsername(this.username);
        account.setRole(this.role);
        return account;
    }

}
