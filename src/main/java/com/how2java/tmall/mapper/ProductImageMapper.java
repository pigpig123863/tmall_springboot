package com.how2java.tmall.mapper;

import java.util.List;

import com.how2java.tmall.pojo.ProductImage;
import com.how2java.tmall.pojo.ProductImageExample;

public interface ProductImageMapper {

	void insert(ProductImage pi);

	void deleteByPrimaryKey(int id);

	void updateByPrimaryKeySelective(ProductImage pi);

	ProductImage selectByPrimaryKey(int id);

	List selectByExample(ProductImageExample example);
	



}
