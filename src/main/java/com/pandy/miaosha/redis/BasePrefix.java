package com.pandy.miaosha.redis;

/**
 * @Author: Pandy
 * @Date: 2019/5/23 21:20
 * @Version 1.0
 */
public abstract class BasePrefix implements Prefix {

    private int expireSeconds;

    private String prefix;

    public BasePrefix(int expireSeconds,String prefix){
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    public BasePrefix(String prefix) {
        this(0,prefix);
    }

    @Override
    public int expireSeconds() {//默认0是永不过期
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        String simpleName = getClass().getSimpleName();
        return simpleName + ":" + prefix;
    }
}
