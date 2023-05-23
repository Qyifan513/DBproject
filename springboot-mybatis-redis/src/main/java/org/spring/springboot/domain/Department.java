package org.spring.springboot.domain;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;


//@Entity
@Data
@Table(name="department")
@ApiModel("department")
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

//    @Id
//    @GeneratedValue
//    /**
//     * 主键
//     */
//    @ApiModelProperty("主键")
//    @Column(name="id")
    private Integer id;

    /**
     * 昵称，默认是用户id
     */
//    @ApiModelProperty("昵称，默认是用户id")
//    @Column(name="name")
    private String name;

    /**
     * 密码，加密存储
     */
//    @ApiModelProperty("密码，加密存储")
//    @Column(name="address")
    private String address;

    /**
     * 手机号码
     */
//    @ApiModelProperty("手机号码")
//    @Column(name="phone")
    private String phone;

    /**
     * 人物头像
     */
//    @ApiModelProperty("人物头像")
//    @Column(name="principal")
    private String principal;

    /**
     * printele
     */
//    @ApiModelProperty("ptele")
//    @Column(name="ptele")
    private String printele;

    public Department() {
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", principal='" + principal + '\'' +
                ", ptele='" + printele + '\'' +
                '}';
    }
}
