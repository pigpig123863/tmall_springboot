package com.how2java.tmall.mapper;

import java.util.List;

import com.how2java.tmall.pojo.Review;
import com.how2java.tmall.pojo.ReviewExample;

public interface ReviewMapper {

	List<Review> selectByExample(ReviewExample example);

	void insert(Review c);

	void deleteByPrimaryKey(int id);

	void updateByPrimaryKeySelective(Review c);

}
