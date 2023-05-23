package org.spring.springboot.domain;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;
@Data
@Table(name="item_and_d")
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * name
     */
    private String name;

    /**
     * content
     */
    private String content;

}