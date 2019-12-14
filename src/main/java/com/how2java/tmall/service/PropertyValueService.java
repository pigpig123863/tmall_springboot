package com.how2java.tmall.service;



import java.util.List;

import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.PropertyValue;

public interface PropertyValueService {

	void init(Product p);

	List<PropertyValue> list(Integer pid);
	PropertyValue get(Integer ptid, Integer pid);
	
}
