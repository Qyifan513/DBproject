package org.spring.springboot.service.impl;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.spring.springboot.dao.DepartmentMapper;
import org.spring.springboot.domain.City;
import org.spring.springboot.domain.Department;
import org.spring.springboot.domain.Item;
import org.spring.springboot.service.DepartmentService;
import org.spring.springboot.utils.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DepartmentServicelmpl implements DepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final ObjectMapper mapper = new ObjectMapper();
    @Override
    public Department findDepartmentById(int id) {
        //从缓存中获取科室信息
        String key = "department:" + id;
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        //缓存中有科室信息时从缓存获取
        boolean hasKey = stringRedisTemplate.hasKey(key);
        if(hasKey){
            String jsonDepartment = operations.get(key);
            Department department1 = null;
            try{
                department1 = mapper.readValue(jsonDepartment, Department.class);
                return department1;
            } catch (JsonMappingException e) {
                throw new RuntimeException(e);
            } catch (JsonParseException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        //缓存中没有科室信息时从数据库中获取科室信息
        Department res = departmentMapper.findById(id);
        //将科室信息插入缓存
        try {
            String json = mapper.writeValueAsString(res);
            stringRedisTemplate.opsForValue().set(key,json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return res;
    }
    @Override
    public int saveDepartment(Department department){
        //未
        return departmentMapper.save(department);
    }
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public int updateDepartment(Department department){
        //更新数据库
        int res =  departmentMapper.updateDepartment(department);
        // 缓存存在，删除缓存
        String key = "department:" + department.getId();
        System.out.println("@#!!!" + key);
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        //缓存中有科室信息时从缓存获取
        boolean hasKey = stringRedisTemplate.hasKey(key);
        if(hasKey){
            //redisTemplate.delete(key);思考为什么这样就错了？？
            stringRedisTemplate.delete(key);
        }
        return  res;
    }
    @Override
    public Pager<Department> findByDepartmentPager(int page, int size){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("page", (page-1)*size);
        params.put("size", size);
        List<Department> list = departmentMapper.pageList(params);
        Pager<Department> pager = new Pager<Department>();
        pager.setData(list);
        pager.setTotal(departmentMapper.pageListCount());
        pager.setPage(page);
        pager.setSize(size);
        System.out.println("!!!!!!!!!!!");
        return pager;
    }
}
