package org.spring.springboot.service;

import org.spring.springboot.common.Result;
import org.spring.springboot.dao.PersonMapper;
import org.spring.springboot.domain.Person;
import org.spring.springboot.utils.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PersonService {

    List<Person> findDepartmentByCondition(String str);
    Result updateByPage(List<Person> inputs);
    //根据idList查找信息
    List<Person> findImformationByIds(List<String> id);
    Result importUpdatePeople(MultipartFile file);
    public Pager<Person> findByPersonPager(int page, int size);
    Result queryById(String id);
    Person getById(String id);
}
