package com.how2java.tmall.mapper;

import java.util.List;

import com.how2java.tmall.pojo.Order;
import com.how2java.tmall.pojo.OrderExample;

public interface OrderMapper {

	List<Order> selectByExample(OrderExample example);

	void insert(Order o);

	Order selectByPrimaryKey(int id);

	void updateByPrimaryKeySelective(Order order);

}
