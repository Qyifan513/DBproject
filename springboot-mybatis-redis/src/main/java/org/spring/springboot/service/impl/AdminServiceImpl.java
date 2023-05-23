package org.spring.springboot.service.impl;


import org.spring.springboot.dao.AdminMapper;
import org.spring.springboot.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
//忘记加注释报错！！！
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public boolean veritypasswd(String name, String password) {
        return adminMapper.verifyPassword(name, password)>0?true:false;
    }
}
