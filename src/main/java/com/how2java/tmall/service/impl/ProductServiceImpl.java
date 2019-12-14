package com.how2java.tmall.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.how2java.tmall.mapper.ProductMapper;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.ProductExample;
import com.how2java.tmall.pojo.ProductImage;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.service.OrderItemService;
import com.how2java.tmall.service.ProductImageService;
import com.how2java.tmall.service.ProductService;
import com.how2java.tmall.service.ReviewService;

@Service
public class ProductServiceImpl implements ProductService  {
	
	@Autowired ProductMapper productMapper;
	@Autowired CategoryService categoryService;
	@Autowired ProductImageService productImageService;
	@Autowired OrderItemService orderItemService;
	@Autowired ReviewService reviewService;

	public List<Product> list(Integer cid) {
		ProductExample example =new ProductExample();
        example.createCriteria().andCidEqualTo(cid);
        example.setOrderByClause("id desc");
        List<Product> result= productMapper.selectByExample(example);
        setFirstProductImage(result);
        setCategory(result);
        return result;
	}

	 public void setCategory(List<Product> ps){
	        for (Product p : ps)
	            setCategory(p);
	    }
	    public void setCategory(Product p){
	        int cid = p.getCid();
	        Category c = categoryService.get(cid);
	        p.setCategory(c);
	    }

	    public void setFirstProductImage(Product p) {
	        List<ProductImage> pis = productImageService.list(p.getId(), ProductImageService.type_single);
	        if (!pis.isEmpty()) {
	            ProductImage pi = pis.get(0);
	            p.setFirstProductImage(pi);
	        }
	    }
	    
	    public void setFirstProductImage(List<Product> ps) {
	        for (Product p : ps) {
	            setFirstProductImage(p);
	        }
	    }


		@Override
		public Product get(int id) {
			Product p = productMapper.selectByPrimaryKey(id);
	        setFirstProductImage(p);
	        setCategory(p);
	        return p;
		}

		@Override
		public void update(Product p) {
			productMapper.updateByPrimaryKeySelective(p);
			
		}

		@Override
		public void delete(int id) {
			productMapper.deleteByPrimaryKey(id);
			
		}

		@Override
		public void add(Product p) {
			productMapper.insert(p);
			
		}

		 @Override
		  public void setSaleAndReviewNumber(Product p) {
		        int saleCount = orderItemService.getSaleCount(p.getId());
		        p.setSaleCount(saleCount);

		        int reviewCount = reviewService.getCount(p.getId());
		        p.setReviewCount(reviewCount);
		    }

		    @Override
		    public void setSaleAndReviewNumber(List<Product> ps) {
		        for (Product p : ps) {
		            setSaleAndReviewNumber(p);
		        }
		    }

			@Override
			public void fill(List<Category> categorys) {
				for (Category category :categorys){
					fill(category);
				}
				
			}

			@Override
			public void fill(Category category) {
				List<Product> ps = list(category.getId());
				category.setProducts(ps);
			}

			@Override
			public void fillByRow(List<Category> categorys) {
				int productNumEachRow =8;
				for (Category category:categorys){
					List<Product> ps = category.getProducts();
					List<List<Product>> productRow = new ArrayList<>();
					
					for(int i=0; i<ps.size(); i+=productNumEachRow){
						int size = i+productNumEachRow;
						size = size>ps.size()?ps.size():size;
						List<Product> temp = ps.subList(i,size);
						productRow.add(temp);
					}
					category.setProductsByRow(productRow);
				}
				
			}

			@Override
			public List<Product> search(String keyword) {
				ProductExample example = new ProductExample();
				example.createCriteria().andNameLike("%"+keyword+"%");
				example.setOrderByClause("id desc");
				List<Product> ps = productMapper.selectByExample(example);
				
				setFirstProductImage(ps);
				setCategory(ps);
				return ps;
			}


}
