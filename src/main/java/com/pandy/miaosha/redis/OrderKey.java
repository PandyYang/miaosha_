package com.pandy.miaosha.redis;

/**
 * @Author: Pandy
 * @Date: 2019/5/23 21:24
 * @Version 1.0
 */
public class OrderKey extends BasePrefix{
    public OrderKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
}
