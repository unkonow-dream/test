package com.sakura.demo.domain.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.sakura.demo.domain.User;

import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
public class LoginUser implements UserDetails {
    private String username;
    private String password;
    private List<GrantedAuthority> authorities;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    // 构造函数、getter和setter方法需要添加
    private User user;
    private List<String> roles;
    public LoginUser(User user, List<String> roles) {
        this.user = user;
        this.roles = roles != null ? new ArrayList<>(roles) : new ArrayList<>();
    }

    @Override
    @JSONField(serialize = false)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(authorities != null){
            return authorities;
        }
        authorities = new ArrayList<>();
        if (roles != null) {
            for (String role : roles) {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
                authorities.add(authority);
            }
        }
        System.out.println("roles:"+roles);
        return authorities;
    }


    @Override
    public String getPassword() {
        return user.getPassword(); // 返回实际密码
    }

    @Override
    public String getUsername() {
        return user.getUserName(); // 返回实际用户名
    }

    @Override
    public boolean isAccountNonExpired() {
        //return accountNonExpired; // 返回实际账户状态
        return true;
    }

    //判断账户是否未过期
    @Override
    public boolean isAccountNonLocked() {
        //return accountNonLocked; // 返回实际账户锁定状态
        return true;
    }
    //判断凭证是否未过期
    @Override
    public boolean isCredentialsNonExpired() {
        //return credentialsNonExpired; // 返回实际凭证状态
        return true;
    }
    //判断账户是否启用
    @Override
    public boolean isEnabled() {
        //return enabled; // 返回实际启用状态
        return true;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String Password() {
        return password;
    }

    public String Username() {
        return username;
    }

    @JSONField(name = "roles")
    public List<String> getRoles() {
        return roles;
    }

    @JSONField(name = "roles")
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }


    //
}


