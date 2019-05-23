package com.pandy.miaosha.dao;

import com.pandy.miaosha.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Author: Pandy
 * @Date: 2019/5/16 10:36
 * @Version 1.0
 */
@Mapper
@Component
public interface UserDao {

    @Select("select * from user where id = #{id}")
    public User getById(@Param("id") int id);

    @Insert("insert into user(id,name)values(#{id},#{name})")
    int insert(User user);
}
