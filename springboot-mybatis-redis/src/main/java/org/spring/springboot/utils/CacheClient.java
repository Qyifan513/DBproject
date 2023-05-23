package org.spring.springboot.utils;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import cn.hutool.json.JSONUtil;

import static org.spring.springboot.utils.RedisConstants.CACHE_NULL_TTL;
import static org.spring.springboot.utils.RedisConstants.LOCK_SHOP_KEY;

@Slf4j
@Component
public class CacheClient {
    private final StringRedisTemplate stringRedisTemplate;//private final StringRedisTemplate stringRedisTemplate;?
    private static final ExecutorService CACHE_REBUILD_EXECUTOR = Executors.newFixedThreadPool(10);

    public CacheClient(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }//为什么这样写？
    public void set(String key, Object value, Long time, TimeUnit unit){
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(value), time, unit);
    }
    //设置逻辑过期
    public void setWithLogicalExpire(String key, Object value, Long time,TimeUnit unit){
        //设置逻辑过期
        RedisData redisData = new RedisData();
        redisData.setData(value);
        redisData.setExpireTime(LocalDateTime.now().plusSeconds(unit.toSeconds(time)));
        //写入redis
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(redisData));
    }
    /*
    缓存穿透：客户端请求的数据在缓存中和数据库中都不存在，这样的缓存永远不会生效，但这些请求都会打到数据库
     */
    public <R,ID> R queryWithPassThrough(String keyPrefix, ID id, Class<R> type, Function<ID,R> dbFallback, Long time,TimeUnit unit){
        String key = keyPrefix + id;
        //从redis查询缓存
        String json = stringRedisTemplate.opsForValue().get(key);
        //判断是否存在
        if(StrUtil.isNotBlank(json)){
            System.out.println("在缓存中查询到" + id + "信息");
            //存在
            return JSONUtil.toBean(json,type);
        }
        //判断命中是否空值
        if(json != null){
            System.out.println("在缓存中命中是空值");
            //返回错误信息
            return null;
        }
        //不存在，根据id查询数据库
        R r = dbFallback.apply(id);
        //不存在，返回错误
        if(r == null){
            System.out.println("数据库中不存在"+id+"信息");
            //空值写入redis
            stringRedisTemplate.opsForValue().set(key,"",CACHE_NULL_TTL,TimeUnit.MINUTES);
            //返回错误信息
            return null;
        }
        System.out.println("在数据库中查询到" + id + "信息");
        //存在，写入redis
        this.set(key,r,time,unit);
        return r;
    }
    /*
    缓存击穿问题/热点Key问题：一个被高并发访问并且缓存重建业务比较复杂的Key突然失效了，无数的访问请求会在瞬间给数据库带来巨大的冲击
     缓存击穿问题解决方案一：逻辑过期
     */
    public <R, ID> R queryWithLogicalExpire(
            String keyPrefix, ID id, Class<R> type, Function<ID, R> dbFallback, Long time, TimeUnit unit){
        String key = keyPrefix + id;
        //从redis中查询记录缓存
        String json = stringRedisTemplate.opsForValue().get(key);
        if(StrUtil.isBlank(json)){
            //查询结果为空
            return null;
        }
        //命中
        //反序列化
        RedisData redisData = JSONUtil.toBean(json,RedisData.class);
        R r = JSONUtil.toBean((JSONObject) redisData.getData(),type);
        LocalDateTime expireTime = redisData.getExpireTime();
        //判断是否过期
        if(expireTime.isAfter(LocalDateTime.now())){
            //未过期，直接返回信息
            return r;
        }
        //已经过期，需要缓存重建
        //1.缓存重建
        //1.1获取互斥锁
        String localKey = LOCK_SHOP_KEY + id;
        boolean isLock = tryLock(localKey);
        if(isLock){
            //获取锁成功,开启独立线程，实现缓存重建
            CACHE_REBUILD_EXECUTOR.submit(() ->{
                try{
                    //查询数据库
                    R newR = dbFallback.apply(id);
                    //重建缓存
                    this.setWithLogicalExpire(key,newR,time,unit);
                }catch (Exception e){
                    throw new RuntimeException(e);
                }finally {
                    //释放锁
                    unlock(localKey);
                }
            });
        }
        //返回过期信息
        return r;
    }

    /*
缓存击穿问题/热点Key问题：一个被高并发访问并且缓存重建业务比较复杂的Key突然失效了，无数的访问请求会在瞬间给数据库带来巨大的冲击
 缓存击穿问题解决方案二：利用互斥锁
 */
    public <R, ID> R queryWithMutex(
            String keyPrefix, ID id, Class<R> type, Function<ID, R> dbFallback, Long time, TimeUnit unit) {
        String key = keyPrefix + id;
        // 1.从redis查询缓存
        String json = stringRedisTemplate.opsForValue().get(key);
        // 2.判断是否存在
        if (StrUtil.isNotBlank(json)) {
            // 3.存在，直接返回
            return JSONUtil.toBean(json, type);
        }
        //判断命中是否是空值
        if(json != null){
            JSONUtil.toBean(json,type);
        }
        //1.缓存重建
        //1.1获取互斥锁
        String lockKey = LOCK_SHOP_KEY + id;
        R r = null;
        try{
            boolean isLock = tryLock(lockKey);
            if(!isLock){
                //获取锁失败，休眠并重试
                Thread.sleep(50);
                return queryWithMutex(keyPrefix, id, type, dbFallback, time, unit);
            }
            //获取锁成功,根据id查询数据库
            r = dbFallback.apply(id);
            if(r == null){
                //将空值写入redis
                stringRedisTemplate.opsForValue().set(key,"",CACHE_NULL_TTL,TimeUnit.MINUTES);
                return null;
            }
            //存在，写入redis
            this.set(key, r, time, unit);
        }catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            // 7.释放锁
            unlock(lockKey);
        }
        // 8.返回
        return r;
    }
    private boolean tryLock(String key){
        Boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(key,"1",10,TimeUnit.SECONDS);
        return BooleanUtil.isTrue(flag);
    }
    private void unlock(String key){
        stringRedisTemplate.delete(key);
    }
}
