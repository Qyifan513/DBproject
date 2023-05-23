package org.spring.springboot;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
//import org.spring.springboot.domain.User;
import org.spring.springboot.domain.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisStringTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

   // @Test
//    void testString() {
//        // 写入一条String数据
//        stringRedisTemplate.opsForValue().set("verify:phone:13600527634", "124143");
//        // 获取string数据
//        Object name = stringRedisTemplate.opsForValue().get("name");
//        System.out.println("name = " + name);
//    }

    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testSaveUser() throws JsonProcessingException {
        // 创建对象
        City user = new City("巴黎",
                "追我的人从这里排到巴黎",
                5L,
                6L);
        // 手动序列化
        String json = mapper.writeValueAsString(user);
        // 写入数据
        stringRedisTemplate.opsForValue().set("city:200", json);

        // 获取数据
        String jsonUser = stringRedisTemplate.opsForValue().get("city:200");
        // 手动反序列化
        City user1 = null;
        try {
            user1 = mapper.readValue(jsonUser, City.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("city1 = " + user1);
    }

//    @Test
//    public void testHash() {
//        stringRedisTemplate.opsForHash().put("user:420", "name", "虎哥");
//        stringRedisTemplate.opsForHash().put("user:420", "age", "21");
//
//        Map<Object, Object> entries = stringRedisTemplate.opsForHash().entries("user:400");
//        System.out.println("entries = " + entries);
//    }

    @Test
    public void name() {
    }
}
