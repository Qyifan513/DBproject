package org.spring.springboot.dao;

import org.spring.springboot.domain.Item;

import java.util.List;
import java.util.Map;


public interface ItemMapper {

    /**
     * 新增
     * @author zhengkai.blog.csdn.net
     * @date 2023/05/17
     **/
    int insert(Item item);

    /**
     * 刪除
     * @author zhengkai.blog.csdn.net
     * @date 2023/05/17
     **/
    int delete(int id);

    /**
     * 更新
     * @author zhengkai.blog.csdn.net
     * @date 2023/05/17
     **/
    int update(Item item);

    /**
     * 查询 根据主键 id 查询
     * @author zhengkai.blog.csdn.net
     * @date 2023/05/17
     **/
    Item load(int id);

    /**
     * 查询 分页查询
     * @author zhengkai.blog.csdn.net
     * @date 2023/05/17
     **/
    List<Item> pageList(Map<String, Object> map);
    /**
     * 查询 分页查询 count
     * @author zhengkai.blog.csdn.net
     * @date 2023/05/17
     **/
    long pageListCount();

}