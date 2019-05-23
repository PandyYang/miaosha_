package com.pandy.miaosha.redis;

/**
 * @Author: Pandy
 * @Date: 2019/5/23 21:19
 * @Version 1.0
 */
public interface Prefix {

    public int expireSeconds();

    public String getPrefix();
}
