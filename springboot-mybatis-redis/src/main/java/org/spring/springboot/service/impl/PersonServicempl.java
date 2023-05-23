package org.spring.springboot.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.spring.springboot.common.Result;
import org.spring.springboot.dao.PersonMapper;
import org.spring.springboot.domain.Person;
import org.spring.springboot.service.PersonService;
import org.spring.springboot.utils.CacheClient;
import org.spring.springboot.utils.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;


import javax.annotation.Resource;
import java.io.IOException;
import java.security.Key;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.spring.springboot.utils.RedisConstants.CACHE_PERSON_KEY;
import static org.spring.springboot.utils.RedisConstants.CACHE_PERSON_TTL;

@Service
public class PersonServicempl implements PersonService {
    @Autowired
    private PersonMapper personMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private CacheClient cacheClient;

    private static final ObjectMapper mapper = new ObjectMapper();
    public List<Person> findDepartmentByCondition(String str){
        List<Person>  res = personMapper.searchmal();
        return res;
    }
    //业务层实现批量更新，当出现要更新的Person的id不在数据库中的情况，停止更新并返回报错,
    public Result updateByPage(List<Person> inputs){
        Result res = new Result();
        //ids存放从数据库中查询到的id信息
        List<String> ids= personMapper.searchID();
        HashSet<String> idsSet = new HashSet<>(ids);
        for(Person peo : inputs){
            if(!idsSet.add(peo.getPersonId())){
                personMapper.update(peo);
            }else{
                res.setSuccess(false);
                res.setErrorMsg("要更新的数据不在数据库中");
                return res;
            }
        }
        return res;
    }
    //业务层实现批量更新，使用redis
    public Result updateByPage2(List<Person> inputs){
        Result res = new Result();
        //ids存放从数据库中查询到的id信息
        List<String> ids= personMapper.searchID();
        HashSet<String> idsSet = new HashSet<>(ids);
        for(Person peo : inputs){
            if(!idsSet.add(peo.getPersonId())){
                personMapper.update(peo);
            }else{
                res.setSuccess(false);
                res.setErrorMsg("要更新的数据不在数据库中");
                return res;
            }
        }
        return res;
    }
    //根据idList查找信息
    public List<Person> findImformationByIds(List<String> id){
        System.out.println("开始");
        //获取开始时间
        long startTime=System.currentTimeMillis();
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();

        List<Person> people = new ArrayList<>();
        Result tmp = new Result();
        for(String sid : id){
//                //从缓存中获取信息
//                String key = new String("person " + sid);
//               //缓存中有目标信息时从redis获取信息
//                boolean hasKey = stringRedisTemplate.hasKey(key);
//                if(hasKey) {
//                    String jsonPerson = operations.get(key);
//                    Person person1 = null;
//                    try {
//                        person1 = mapper.readValue(jsonPerson, Person.class);
//                        people.add(person1);
//                    } catch (JsonMappingException e) {
//                        throw new RuntimeException(e);
//                    } catch (JsonParseException e) {
//                        throw new RuntimeException(e);
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                }else {
//                    //缓存中没有科室信息时从数据库中获取科室信息
//                        Person tmp = personMapper.load(sid);
//                        people.add(tmp);
//                    try {
//                        //将科室信息插入缓存
//                        String json = mapper.writeValueAsString(tmp);
//                        stringRedisTemplate.opsForValue().set(key,json);
//                    } catch (JsonProcessingException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
            tmp.setData(this.queryById(sid).getData());
            if(tmp.getData()!=null){
                people.add(Person.class.cast(tmp.getData()));
                //people.add(p);
            }else {
                System.out.println("缓存和数据库中没有用户" + sid);
            }
        }
        long endTime=System.currentTimeMillis(); //获取结束时间
        System.out.println("================time===================");
        System.out.println(endTime - startTime);
        return  people;
    }
    public Result importUpdatePeople(MultipartFile file){
        Result res = new Result();
        List<Person> peopleList = new ArrayList<>();
        // 读取模板文件
        XSSFWorkbook wb = null;
        try {
            // 读取导入文件内容
            wb = new XSSFWorkbook(file.getInputStream());

            for (int sheetNo = 1; sheetNo < wb.getNumberOfSheets(); sheetNo++) {

                switch (sheetNo) {
                    case 1:
                        // 读取excel信息
                        peopleList = checkInformation(wb.getSheetAt(sheetNo));

                        break;
                    default:
                        break;
                }
            }
            res = updateByPage(peopleList);
        }catch (Exception e){
            System.out.println("发生异常！");
            res.setErrorMsg("importUpdatePeople内发生异常");
            res.setSuccess(false);
        }

        return res;
    }
    private List<Person> checkInformation(XSSFSheet sheetAt){
        List<Person> peopleList = new ArrayList<>();
        // 获取行
        int rowNo = 1;

        while (true){
            // 获取行
            XSSFRow row = sheetAt.getRow(rowNo);
            Person per = new Person();
            //if (PoiUtils.isBlankRow(row)) break;
            Iterator<Cell> cellIterator = row.cellIterator();
            int voidFlag = 1;
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                if (cell.getCellType() != CellType.BLANK && !cell.getStringCellValue().trim().isEmpty()) {
                    voidFlag = 0;
                    break;
                }
            }
            if(voidFlag == 1)
                return peopleList;
            XSSFCell cell_1 = row.getCell(1);//person_id
            XSSFCell cell_2 = row.getCell(2);//name
            XSSFCell cell_3 = row.getCell(3);//sex
            XSSFCell cell_4 = row.getCell(4);//tele
            if(cell_1 != null){
                cell_1.setCellType(CellType.STRING);
                String cellValue = cell_1.getStringCellValue().trim();
                if(cellValue != null && cellValue != ""){
                    //获取到person_id
                    per.setPersonId(cellValue);
                }
            }
            if(cell_2 != null){
                cell_2.setCellType(CellType.STRING);
                String cellValue = cell_2.getStringCellValue().trim();
                if(cellValue != null && cellValue != ""){
                    //获取到person_id
                    per.setName(cellValue);
                }
            }
            if(cell_3 != null){
                cell_3.setCellType(CellType.STRING);
                String cellValue = cell_3.getStringCellValue().trim();
                if(cellValue != null && cellValue != ""){
                    //获取到person_id
                    per.setSex(cellValue);
                }
            }
            if(cell_4 != null){
                cell_4.setCellType(CellType.STRING);
                String cellValue = cell_4.getStringCellValue().trim();
                if(cellValue != null && cellValue != ""){
                    //获取到person_id
                    per.setTel(cellValue);
                }
            }
            peopleList.add(per);
            rowNo++;
        }
    }
    @Override
    public Pager<Person> findByPersonPager(int page, int size){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("offset", (page-1)*size);
        params.put("pageSize", size);
        List<Person> list = personMapper.pageList(params);
        Pager<Person> pager = new Pager<Person>();
        pager.setData(list);
        pager.setTotal(personMapper.findPersonCount());
        pager.setPage(page);
        pager.setSize(size);
        System.out.println("!!!!!!!!!!!");
        return pager;
    }
    @Override
    public Result queryById(String id){
        Result result = new Result();
        Person personbean = cacheClient.queryWithPassThrough(CACHE_PERSON_KEY,id,Person.class,this::getById,CACHE_PERSON_TTL, TimeUnit.MINUTES);
        if(personbean == null){
            result.setSuccess(false);
            result.setErrorMsg("用户不存在！");
        }
        result.setData(personbean);
        return result;
    }
    @Override
    public Person getById(String id){
       return personMapper.load(id);
    }
}
