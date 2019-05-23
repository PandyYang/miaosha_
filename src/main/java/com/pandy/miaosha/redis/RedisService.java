package com.pandy.miaosha.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Author: Pandy
 * @Date: 2019/5/16 15:54
 * @Version 1.0
 */
@Component
public class RedisService {

    @Autowired
    JedisPool jedisPool;
    @Autowired
    RedisConfig redisConfig;

    /**
     * 获取单个对象
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T get(String key,Class<T> clazz){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String str = jedis.get(key);
            T t = stringToBean(str,clazz);
            return t;
        }finally {
            returnToPool(jedis);
        }
    }

    public <T> T get2(Prefix prefix,String key,Class<T> clazz){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            String str = jedis.get(realKey);
            T t = stringToBean(str,clazz);
            return t;
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 设置对象
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    public <T> Boolean set(String key,T value){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String str = beanToString(value);
            if (str == null){
                return false;
            }
            jedis.set(key,str);
            return true;
        }finally {
            returnToPool(jedis);
        }
    }

    public <T> Boolean set2(Prefix prefix,String key,T value){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String str = beanToString(value);
            if (str == null){
                return false;
            }
            String realKey = prefix.getPrefix() + key;
            int seconds = prefix.expireSeconds();
            if (seconds <= 0){
                jedis.set(realKey,str);
            }else {
                jedis.setex(realKey,seconds,str);
            }
            return true;
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 判断是否存在
     * @param prefix
     * @param key
     * @param <T>
     * @return
     */
    public <T> Boolean exists(Prefix prefix,String key){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
          return jedis.exists(realKey);
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 增加
     * @param prefix
     * @param key
     * @param <T>
     * @return
     */
    public <T> Long incr(Prefix prefix,String key){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            return jedis.incr(realKey);
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 减少
     * @param prefix
     * @param key
     * @param <T>
     * @return
     */
    public <T> Long decr(Prefix prefix,String key){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            return jedis.decr(realKey);
        }finally {
            returnToPool(jedis);
        }
    }

    private <T> String beanToString(T value) {
        if (value == null){
            return null;
        }
        Class<?> clazz = value.getClass();
        if (clazz == int.class || clazz == Integer.class){
        return ""+value;
        }else if (clazz == String.class){
            return (String)value;
        }else if (clazz == long.class || clazz == Long.class){
            return ""+value;
        }else {
            return JSON.toJSONString(value);
        }

    }

    private <T> T stringToBean(String str,Class<T> clazz){
        if (str == null || str.length() <= 0 || clazz == null){
            return null;
        }else if (clazz == String.class){
            return (T)str;
        }else if (clazz == int.class || clazz == Integer.class){
            return (T)Integer.valueOf(str);
        }else if (clazz == long.class || clazz == Long.class){
           return (T)Long.valueOf(str);
        }else {
            return JSON.toJavaObject(JSON.parseObject(str),clazz);
        }
    }

    private void returnToPool(Jedis jedis){
        if (jedis!=null){
            jedis.close();
        }
    }
}
