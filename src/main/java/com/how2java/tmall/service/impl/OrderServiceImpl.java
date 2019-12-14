package com.how2java.tmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.how2java.tmall.mapper.OrderMapper;
import com.how2java.tmall.pojo.Order;
import com.how2java.tmall.pojo.OrderExample;
import com.how2java.tmall.pojo.OrderItem;
import com.how2java.tmall.service.OrderItemService;
import com.how2java.tmall.service.OrderService;
import com.how2java.tmall.service.UserService;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
    OrderMapper orderMapper;

    @Autowired
    UserService userService;
    @Autowired OrderItemService orderItemService;
    
	public List<Order> list(){
        OrderExample example =new OrderExample();
        example.setOrderByClause("id desc");
        return orderMapper.selectByExample(example);

    }

	@Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackForClassName="Exception")
    public float getTotal(Order o, List<OrderItem> ois) {
        float total = 0;
        add(o);

        if(false)
            throw new RuntimeException();

        for (OrderItem oi: ois) {
            oi.setOid(o.getId());
            orderItemService.update(oi);
            total+=oi.getProduct().getPromotePrice()*oi.getNumber();
        }
        return total;
    }

	private void add(Order o) {
		orderMapper.insert(o);
		
	}

	@Override
	public Order get(int id) {
		return orderMapper.selectByPrimaryKey(id);
	}

	@Override
	public void update(Order order) {
		orderMapper.updateByPrimaryKeySelective(order);
		
	}

	@Override
	public List<Order> list(int uid, String excludedStatus) {
		OrderExample example = new OrderExample();
		example.createCriteria().andUidEqualTo(uid).andStatusNotEqualTo(excludedStatus);
		example.setOrderByClause("id desc");
		return orderMapper.selectByExample(example);
	}

}
