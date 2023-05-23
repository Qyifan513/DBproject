package org.spring.springboot.controller;

import io.swagger.annotations.ApiOperation;
import org.spring.springboot.domain.Department;
import org.spring.springboot.domain.Item;
import org.spring.springboot.service.DepartmentService;
import org.spring.springboot.utils.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @RequestMapping(value = "/admin/department/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据id获取科室信息", notes = "根据id查询科室信息")
    public Department findOneDepartment(@PathVariable("id") int id) {
        return departmentService.findDepartmentById(id);
    }
    //注意:科室的地址要不要作为其他表的外键
    @RequestMapping(value = "/admin/department",method = RequestMethod.POST)
    @ApiOperation(value = "", notes = "根据Department创建新的科室信息记录")
    public void createDepartment(@RequestBody Department department){
        departmentService.saveDepartment(department);
    }

    @RequestMapping(value = "/admin/department", method = RequestMethod.PUT)
    @ApiOperation(value = "", notes = "根据Department更新科室记录")
    public void modifyDepartment(@RequestBody Department department) {
        departmentService.updateDepartment(department);
    }

    @RequestMapping(value = "/admin/department/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "", notes = "根据id删除记录")
    public void modifyCity(@PathVariable("id") int id) {
        //查询能否删除，在该科室检查的所有项目是否都已经完成
        //不能删除则返回信息
        //能删除则对科室进行删除//小记：查询项目记录时，查询完成属性为0的记录
        //删除科室时，数据库中所有该科室的项目也进行删除。1、删除整条记录2、添加删除标记
    }
    /**
     * 查询 分页查询
     * @date 2023/05/17
     **/
    @RequestMapping(value ="/admin/department/show", method = RequestMethod.GET)
    public Pager<Department> searchList(int page, int size){
        return departmentService.findByDepartmentPager(page,size);
    }
}
