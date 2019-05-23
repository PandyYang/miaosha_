package com.pandy.miaosha.redis;

/**
 * @Author: Pandy
 * @Date: 2019/5/23 21:23
 * @Version 1.0
 */
public class UserKey extends BasePrefix {

    public UserKey(String prefix) {
        super(prefix);
    }

    public static UserKey getById = new UserKey("id");
    public static UserKey getByName = new UserKey("name");
}
