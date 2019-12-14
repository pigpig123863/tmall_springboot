package com.how2java.tmall.service;


import java.util.List;

import com.how2java.tmall.pojo.User;

public interface UserService {

	List<User> list();

	User get(int id);
	
	boolean isExist(String name);

	void add(User user);
	
	User get(String name,String password);

}
