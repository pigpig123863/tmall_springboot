package com.how2java.tmall.mapper;

import java.util.List;

import com.how2java.tmall.pojo.Property;
import com.how2java.tmall.pojo.PropertyExample;

public interface PropertyMapper {


	List selectByExample(PropertyExample example);

	Property selectByPrimaryKey(int id);

	void updateByPrimaryKeySelective(int id);

	void deleteByPrimaryKey(int id);

	void insertSelective(Property property);


	

	
}
