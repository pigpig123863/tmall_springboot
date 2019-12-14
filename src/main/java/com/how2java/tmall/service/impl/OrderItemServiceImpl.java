package com.how2java.tmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.how2java.tmall.mapper.OrderItemMapper;
import com.how2java.tmall.mapper.OrderMapper;
import com.how2java.tmall.pojo.Order;
import com.how2java.tmall.pojo.OrderItem;
import com.how2java.tmall.pojo.OrderItemExample;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.User;
import com.how2java.tmall.service.OrderItemService;
import com.how2java.tmall.service.ProductService;

@Service
public class OrderItemServiceImpl implements OrderItemService {
	
	 @Autowired OrderItemMapper orderItemMapper;
	 @Autowired ProductService productService;

	 @Override
	  public void fill(List<Order> os) {
        for (Order o : os) {
            fill(o);
        }
    }

	 public void fill(Order o) {
        OrderItemExample example =new OrderItemExample();
        example.createCriteria().andOidEqualTo(o.getId());
        example.setOrderByClause("id desc");
        List<OrderItem> ois =orderItemMapper.selectByExample(example);
        setProduct(ois);

        float total = 0;
        int totalNumber = 0;
        for (OrderItem oi : ois) {
            total+=oi.getNumber()*oi.getProduct().getPromotePrice();
            totalNumber+=oi.getNumber();
        }
        o.setTotal(total);
        o.setTotalNumber(totalNumber);
        o.setOrderItems(ois);


    }

	private void setProduct(List<OrderItem> ois) {
		for(OrderItem oi :ois){
			
			setProduct(oi);
		}
		
	}

	private void setProduct(OrderItem oi) {
		Product p =productService.get(oi.getPid());
		oi.setProduct(p);
		
	}

	@Override
	public int getSaleCount(Integer pid) {
		OrderItemExample orderItemExample = new OrderItemExample();
		orderItemExample.createCriteria().andIdEqualTo(pid);
		List<OrderItem> ois = orderItemMapper.selectByExample(orderItemExample);
		
		int result=0;
		for(OrderItem oi : ois){
			result+=oi.getNumber();
		}
		return result;
	}

	@Override
	public List<OrderItem> listByUser(int uid) {
		OrderItemExample example = new OrderItemExample();
		example.createCriteria().andUidEqualTo(uid);
		List<OrderItem> ois = orderItemMapper.selectByExample(example);
		setProduct(ois);
		return ois;
	}

	@Override
	public void update(OrderItem oi) {
		orderItemMapper.updateByPrimaryKeySelective(oi);
		
	}

	@Override
	public void add(OrderItem oi) {
		orderItemMapper.insert(oi);
		
	}

	@Override
	public OrderItem get(int id) {
		OrderItemExample example = new OrderItemExample();
		example.createCriteria().andIdEqualTo(id);
		return  orderItemMapper.selectByExample(example).get(0);
	}

	@Override
	public void delete(int oiid) {
		orderItemMapper.deleteByPrimaryKey(oiid);
		
	}

	
			
}
