package com.how2java.tmall.service;

import java.util.List;

import com.how2java.tmall.pojo.Property;

public interface PropertyService {

	List<Property> list(Integer cid);

	Property get(int id);

	void update(Property property);

	void delete(int id);

	void add(Property property);
	
}
