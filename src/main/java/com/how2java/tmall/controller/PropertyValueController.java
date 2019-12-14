package com.how2java.tmall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.Property;
import com.how2java.tmall.pojo.PropertyValue;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.service.ProductService;
import com.how2java.tmall.service.PropertyService;
import com.how2java.tmall.service.PropertyValueService;

@Controller
public class PropertyValueController {
	@Autowired PropertyService propertyService;
	@Autowired ProductService productService;
	@Autowired CategoryService categoryService;
	@Autowired PropertyValueService propertyValueService;
	
	@RequestMapping("/admin_propertyValue_edit")
	public String edit(int pid , Model model){
		Product p = productService.get(pid);
		Category c = categoryService.get(p.getCid());
		p.setCategory(c);
		propertyValueService.init(p);
	    List<PropertyValue> pvs = propertyValueService.list(p.getId());
//		for(PropertyValue pv:pvs){
//			System.out.println(pv.getProperty().getName());
//		}
		model.addAttribute("p",p);
		model.addAttribute("pvs",pvs);
		return "admin/editPropertyValue";
	}
	
}
