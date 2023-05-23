package org.spring.springboot.controller;


import org.spring.springboot.domain.AdminLogin;
import org.spring.springboot.common.Result;
import org.spring.springboot.service.impl.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController {
    @Autowired
    private AdminServiceImpl adminService;

    // 判断是否登录成功
    @ResponseBody
    @CrossOrigin
    @RequestMapping(value = "/admin/login/status", method = RequestMethod.POST)
//    public Result loginStatus(@RequestBody AdminLogin req, HttpSession session) {
    public Result loginStatus(@RequestBody AdminLogin req) {
          String name = req.getName();
          String password = req.getPassword();

          boolean res = adminService.veritypasswd(name, password);
        if (res) {
            //session.setAttribute("name", name);
            System.out.println("成功登陆啦！！恭喜呀##############");
            return new Result();
        } else {
            Result r = new Result();
            r.setSuccess(false);
            r.setErrorMsg("用户名或密码错误");
            return r;
        }
    }
}
