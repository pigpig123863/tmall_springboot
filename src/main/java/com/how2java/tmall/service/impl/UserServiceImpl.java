package com.how2java.tmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.how2java.tmall.mapper.UserMapper;
import com.how2java.tmall.pojo.User;
import com.how2java.tmall.pojo.UserExample;
import com.how2java.tmall.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired UserMapper userMapper;

	@Override
	public List<User> list() {
		UserExample example =new UserExample();
        example.setOrderByClause("id desc");
        return userMapper.selectByExample(example);

	
}

	@Override
	public User get(int id) {
        return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public boolean isExist(String name) {
		UserExample example = new  UserExample();
		example.createCriteria().andNameEqualTo(name);
		List<User> us = userMapper.selectByExample(example);
		if(!us.isEmpty()){
			return true;
		}
		return false;
		
	}

	@Override
	public void add(User user) {
		userMapper.insert(user);
		
	}

	@Override
	public User get(String name, String password) {
		UserExample example = new UserExample();
		example.createCriteria().andNameEqualTo(name).andPasswordEqualTo(password);
		List<User> us = userMapper.selectByExample(example);
		if(!us.isEmpty()){
			return us.get(0);
		}
		return null;
	}
}
