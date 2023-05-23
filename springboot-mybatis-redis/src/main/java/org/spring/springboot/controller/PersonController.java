package org.spring.springboot.controller;

import org.spring.springboot.common.Result;
import org.spring.springboot.dao.PersonMapper;

import org.spring.springboot.domain.Person;
import org.spring.springboot.service.PersonService;

import org.spring.springboot.utils.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class PersonController {
    @Autowired
    private PersonService personService;
    @ResponseBody
    @RequestMapping(value = "/admin/person/mytest", method = RequestMethod.POST)
    public List<Person> Search(){
        String str = new String("男");
        return personService.findDepartmentByCondition(str);
    }
    @ResponseBody
    @RequestMapping(value = "/admin/person/updateByExcel", method = RequestMethod.POST)
    public Result UpdateThrowexcel(@RequestBody MultipartFile file){
        return personService.importUpdatePeople(file);
    }
    @ResponseBody
    @RequestMapping(value = "/admin/person/findByIds", method = RequestMethod.POST)
    public List<Person> SearchByIds(@RequestBody String[] input){
        List<String> people = Arrays.asList(input);
        return personService.findImformationByIds(people);
    }
    @Autowired
    PersonMapper personMapper;
    /**
     * 查询 分页查询
     * @date 2023/05/17
     **/
    @RequestMapping(value ="/admin/person/show", method = RequestMethod.GET)
    public Pager<Person> searchList(int page, int size){
        return personService.findByPersonPager(page,size);
    }
    /*
    根据id查询用户信息
     */
    @ResponseBody
    @RequestMapping(value = "/admin/person/{id}", method = RequestMethod.GET)
    public Result queryPersonById(@PathVariable("id")String id){
        return personService.queryById(id);
    }
}
