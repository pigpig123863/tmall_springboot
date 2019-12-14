package com.how2java.tmall.mapper;

import java.util.List;

import com.how2java.tmall.pojo.OrderItem;
import com.how2java.tmall.pojo.OrderItemExample;

public interface OrderItemMapper {

	List<OrderItem> selectByExample(OrderItemExample example);

	void updateByPrimaryKeySelective(OrderItem oi);

	void insert(OrderItem oi);

	void deleteByPrimaryKey(int oiid);

}
