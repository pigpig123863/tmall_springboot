package com.how2java.tmall.service;

import java.util.List;

import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Product;

public interface ProductService {

	List<Product> list(Integer cid);

	Product get(int id);

	void update(Product p);

	void delete(int id);

	void add(Product p);

	void setSaleAndReviewNumber(Product p);

	void setSaleAndReviewNumber(List<Product> ps);
	
	public void fill(List<Category> categorys);

	public void fill(Category category);

	public void fillByRow(List<Category> categorys);

	List<Product> search(String keyword);
	
}
