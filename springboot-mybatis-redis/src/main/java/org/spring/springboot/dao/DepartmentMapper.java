package org.spring.springboot.dao;

import org.spring.springboot.domain.Department;

import java.util.List;
import java.util.Map;

public interface DepartmentMapper {
    Department findById(int id);
    int save(Department department);
    int updateDepartment(Department department);
    List<Department> pageList(Map<String,Object> map);
    long pageListCount();
}
