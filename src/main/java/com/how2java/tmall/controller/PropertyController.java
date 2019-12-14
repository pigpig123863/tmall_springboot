package com.how2java.tmall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.how2java.tmall.pojo.Property;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.service.PropertyService;
import com.how2java.tmall.util.Page;

@Controller
public class PropertyController {
	@Autowired CategoryService categoryService;
	@Autowired PropertyService propertyService;
	
	@RequestMapping("/admin_property_list")
	public String list(Integer cid,Page page,Model model){
		Category category = categoryService.get(cid);
		PageHelper.offsetPage(page.getStart(), page.getCount());
		List<Property> ps = propertyService.list(cid);
		int total = (int) new PageInfo<> (ps).getTotal();
		
		page.setTotal(total);
		page.setParam("&cid="+category.getId());
		model.addAttribute("ps",ps);
		model.addAttribute("page",page);
		model.addAttribute("c",category);
		return "admin/listProperty";
	}
	
	@RequestMapping("/admin_property_edit")
	public String edit(int id,Model model){
		Property property = propertyService.get(id);
		model.addAttribute("p",property);
		return "admin/editProperty";
	}
	
	@RequestMapping("/admin_property_update")
	public String update(Property property){
		 propertyService.update(property);
		 return "redirect:/admin_property_list?cid="+property.getCid();
	}
	
	@RequestMapping("/admin_property_delete")
	public String delete(int id){
		Property property = propertyService.get(id);
		propertyService.delete(id);
		return "redirect:/admin_property_list?cid="+property.getCid();
	}
	
	@RequestMapping("/admin_property_add")
	public String add(Property property){
		propertyService.add(property);
		return "redirect:/admin_property_list?cid="+property.getCid();
	}
}
