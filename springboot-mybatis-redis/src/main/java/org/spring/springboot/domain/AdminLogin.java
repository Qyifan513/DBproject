package org.spring.springboot.domain;

import lombok.Data;

@Data
public class AdminLogin {
    //private String phone;
    private Integer id;
    private String name;
    private String password;

    public AdminLogin() {
    }
    public AdminLogin(String Name, String Password) {
        this.name = Name;
        this.password = Password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}

