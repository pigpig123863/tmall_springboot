package com.how2java.tmall.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.tmall.pojo.Order;
import com.how2java.tmall.service.OrderItemService;
import com.how2java.tmall.service.OrderService;
import com.how2java.tmall.util.Page;

@Controller
public class OrderController {
	
		@Autowired
	    OrderService orderService;
		@Autowired
		OrderItemService orderItemService;

		@RequestMapping("admin_order_delivery")
		public String delivery(int oid){
			Order order = orderService.get(oid);
			order.setDeliveryDate(new Date());
			order.setStatus(orderService.waitConfirm);
			orderService.update(order);
			
			return "redirect:/admin_order_list";
		}
	    
		@RequestMapping("admin_order_list")
	    public String list(Model model, Page page){
	        PageHelper.offsetPage(page.getStart(),page.getCount());

	        List<Order> os= orderService.list();

	        int total = (int) new PageInfo<>(os).getTotal();
	        page.setTotal(total);

	        orderItemService.fill(os);

	        model.addAttribute("os", os);
	        model.addAttribute("page", page);

	        return "admin/listOrder";
	    }
}
