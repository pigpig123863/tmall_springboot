package com.how2java.tmall.mapper;

import java.util.List;

import com.how2java.tmall.pojo.User;
import com.how2java.tmall.pojo.UserExample;

public interface UserMapper {

	List<User> selectByExample(UserExample example);

	User selectByPrimaryKey(int id);

	void insert(User user);

	
}
