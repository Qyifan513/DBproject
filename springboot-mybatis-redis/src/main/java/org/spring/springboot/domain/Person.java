package org.spring.springboot.domain;

import org.apache.ibatis.annotations.Result;

import java.io.Serializable;

/**
 * @description person
 * @author zhengkai.blog.csdn.net
 * @date 2023-05-17
 */
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */

    private String personId;

    /**
     * 用户名
     */
    private String name;

    /**
     * 性别
     */
    private String sex;

    /**
     * 手机号
     */
    private String tel;


    public Person() {
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

}