package org.spring.springboot.dao;

import org.spring.springboot.domain.Person;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

public interface PersonMapper {

    /**
     * 新增
     * @author zhengkai.blog.csdn.net
     * @date 2023/05/17
     **/
    int insert(Person person);

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
    int update(Person person);

    /**
     * 查询 根据主键 id 查询
     * @author zhengkai.blog.csdn.net
     * @date 2023/05/17
     **/
    Person load(String id);

    /**
     * 查询 分页查询
     * @author zhengkai.blog.csdn.net
     * @date 2023/05/17
     **/
    List<Person> pageList(Map<String, Object> map);
    long findPersonCount();
    List<Person> searchmal();
    //查找数据库中所有的id
    List<String> searchID();
}
