package com.pandy;

import redis.clients.jedis.Jedis;

/**
 * @Author: Pandy
 * @Date: 2019/5/16 15:33
 * @Version 1.0
 */
public class JedisTest {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.43.7",6379);
        //jedis.set("name","pandy");
        //edis.set("age","22");
        jedis.del("name");
        jedis.del("age");
        jedis.del("value");
        System.out.println(jedis.ping());
    }
}
