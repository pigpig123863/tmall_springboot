package com.how2java.tmall.service;

import java.util.List;


import com.how2java.tmall.pojo.Order;
import com.how2java.tmall.pojo.OrderItem;

public interface OrderItemService {

	void fill(List<Order> os);
	
	void fill(Order order);

	int getSaleCount(Integer id);

	List<OrderItem> listByUser(int uid);

	void update(OrderItem oi);

	void add(OrderItem oi);

	OrderItem get(int id);

	void delete(int oiid);

	
	
}
