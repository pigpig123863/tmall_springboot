package com.how2java.tmall.mapper;

import java.util.List;

import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.ProductExample;

public interface ProductMapper {

	List selectByExample(ProductExample example);


	Product selectByPrimaryKey(int id);


	void updateByPrimaryKeySelective(Product p);


	void deleteByPrimaryKey(int id);



	void insert(Product p);
	
}
