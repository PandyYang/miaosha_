package com.pandy.miaosha.controller;

import com.pandy.miaosha.domain.User;
import com.pandy.miaosha.redis.RedisService;
import com.pandy.miaosha.redis.UserKey;
import com.pandy.miaosha.result.Result;
import com.pandy.miaosha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



/**
 * @Author: Pandy
 * @Date: 2019/5/16 9:31
 * @Version 1.0
 */
@Controller
@RequestMapping("/demo")
public class SampleController {

    @Autowired
    RedisService redisService;
    @Autowired
    UserService userService;

    @RequestMapping("/")
    public String thymeleaf(Model model){
        model.addAttribute("name","pandy");
        return "hello";
    }

    @RequestMapping("/db/get")
    @ResponseBody
    public Result<User> dbGet(){
        User user = userService.getById(1);
        return Result.success(user);
    }

    @RequestMapping("/db/tx")
    @ResponseBody
    public Result<Boolean> dbTx(){
        userService.getTx();
        return Result.success(true);
    }

    @RequestMapping("/redis/get")
    @ResponseBody
    public Result<Long> redisGet(){
        Long v1 = redisService.get("key1", Long.class);
        System.out.println(v1);
        return Result.success(v1);
    }

    @RequestMapping("/redis/set")
    @ResponseBody
    public Result<String> redisSet(){
        Boolean set = redisService.set("key2", "pandy");
        String str = redisService.get("key2", String.class);
        System.out.println(str);
        return Result.success(str);
    }

    @RequestMapping("/redis/get2")
    @ResponseBody
    public Result<User> redisGet2(){
        User redisService2 = redisService.get2(UserKey.getById, "" + 1, User.class);
        return Result.success(redisService2);
    }

    @RequestMapping("/redis/set2")
    @ResponseBody
    public Result<Boolean> redisSet2(){
        User user = new User();
        user.setId(123);
        user.setName("ceshi");
        Boolean aBoolean = redisService.set2(UserKey.getById, "" + 1, user);
        return Result.success(true);
    }
}

