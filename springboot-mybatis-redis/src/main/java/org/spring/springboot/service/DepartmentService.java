package org.spring.springboot.service;

//import org.spring.springboot.common.Result;
import org.spring.springboot.domain.Department;
import org.spring.springboot.domain.Item;
import org.spring.springboot.utils.Pager;


public interface DepartmentService {
    Department findDepartmentById(int id);
    int saveDepartment(Department department);
    int updateDepartment(Department department);
    /**
     * 分页查询
     */
    public Pager<Department> findByDepartmentPager(int page, int size);
}
