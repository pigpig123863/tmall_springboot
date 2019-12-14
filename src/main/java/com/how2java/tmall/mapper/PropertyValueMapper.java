package com.how2java.tmall.mapper;

import java.util.List;

import com.how2java.tmall.pojo.PropertyValue;
import com.how2java.tmall.pojo.PropertyValueExample;

public interface PropertyValueMapper {

	List<PropertyValue> selectByExample(PropertyValueExample example);

	void insert(PropertyValue pv);

	

}
