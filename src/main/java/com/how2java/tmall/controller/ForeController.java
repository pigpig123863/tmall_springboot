package com.how2java.tmall.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import com.github.pagehelper.PageHelper;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Order;
import com.how2java.tmall.pojo.OrderItem;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.ProductImage;
import com.how2java.tmall.pojo.PropertyValue;
import com.how2java.tmall.pojo.Review;
import com.how2java.tmall.pojo.User;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.service.OrderItemService;
import com.how2java.tmall.service.OrderService;
import com.how2java.tmall.service.ProductImageService;
import com.how2java.tmall.service.ProductService;
import com.how2java.tmall.service.PropertyValueService;
import com.how2java.tmall.service.ReviewService;
import com.how2java.tmall.service.UserService;

import comparator.ProductAllComparator;
import comparator.ProductDateComparator;
import comparator.ProductPriceComparator;
import comparator.ProductReviewComparator;
import comparator.ProductSaleCountComparator;

@Controller
public class ForeController {
	@Autowired ProductService productService;
	@Autowired ProductImageService productImageService;
	@Autowired PropertyValueService propertyValueService;
	@Autowired ReviewService reviewService;
	@Autowired CategoryService categoryService;
	@Autowired UserService userService;
	@Autowired OrderItemService orderItemService;
	@Autowired OrderService orderService;
	
	@RequestMapping("foredoreview")
	public String doview(@RequestParam("oid")int oid,@RequestParam("pid")int pid,String content,HttpSession session){
		Order order = orderService.get(oid);
		order.setStatus(orderService.finish);
		orderService.update(order);
		Product product = productService.get(pid);
		User user = (User) session.getAttribute("user");
		Review review = new Review();
		review.setContent(content);
		review.setCreateDate(new Date());
		review.setPid(pid);
		review.setUid(user.getId());
		reviewService.add(review);
		
		return "redirect:forereview?oid="+oid+"&showonly=true";
	}
	
	@RequestMapping("forereview")
	public String view(int oid,Model model){
		Order order = orderService.get(oid);
		orderItemService.fill(order);
		Product product = order.getOrderItems().get(0).getProduct();
		List rs = reviewService.list(product.getId());
		productService.setSaleAndReviewNumber(product);
		
		model.addAttribute("p",product);
		model.addAttribute("o",order);
		model.addAttribute("rs",rs);
		
		return "fore/review";
	}
	
	@RequestMapping("foreorderConfirmed")
	public String orderConfirmed(int oid){
		Order order = orderService.get(oid);
		order.setConfirmDate(new Date());
		order.setStatus(orderService.waitReview);
		orderService.update(order);
		return "fore/orderConfirmed";
	}
	
	@RequestMapping("/foreconfirmPay")
	public String confirmPay(Model model,int oid){
		Order order = orderService.get(oid);
		orderItemService.fill(order);
		model.addAttribute("o",order);
		return "fore/confirmPay";
	}
	
	@RequestMapping("forebought")
	public String bought(HttpSession session,Model model){
		User user = (User) session.getAttribute("user");
		List<Order> os= orderService.list(user.getId(),OrderService.delete);
		
		orderItemService.fill(os);

        model.addAttribute("os", os);

        return "fore/bought";
	}

	@RequestMapping("forepayed")
	public String payed(@RequestParam("oid")int oid,@RequestParam("total") float total,Model model){
		Order order = orderService.get(oid);
		order.setStatus(OrderService.waitDelivery);
		order.setPayDate(new Date());
		orderService.update(order);
		model.addAttribute("o",order);
		return "fore/payed";
	}
	
	
	@RequestMapping("forecreateOrder")
	public String createOrder(Order order,HttpSession session){
		User user = (User) session.getAttribute("user");
		
		String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+RandomUtils.nextInt(10000);
		order.setOrderCode(orderCode);
		order.setCreateDate(new Date());
		order.setUid(user.getId());
		order.setStatus(OrderService.waitPay);
		List<OrderItem> ois = (List<OrderItem>) session.getAttribute("ois");
		
		float total = orderService.getTotal(order,ois);
		return "redirect:forealipay?oid="+order.getId()+"&total="+total;
	}
	
	@RequestMapping("foredeleteOrderItem")
	public String deleteOrderItem(int oiid,Model model,HttpSession session){
		User user = (User) session.getAttribute("user");
		if (user!=null){
			orderItemService.delete(oiid);
			return "success";
		}else{
			return "fail";
		}
		
	}
	
	@RequestMapping("forechangeOrderItem")
	public String changeOrderItem(@RequestParam ("pid") int pid,@RequestParam("num")int num,Model model,HttpSession session){
		User user = (User) session.getAttribute("user");
		if(user==null){
			return "fail";
		}else{
		List<OrderItem> ois = orderItemService.listByUser(user.getId());
		for(OrderItem oi:ois){
			if(oi.getPid()==pid){
				oi.setNumber(num);
				orderItemService.update(oi);
				break;
			}
		}
		return "success";
	}
	}
	@RequestMapping("forecheckLogin")
    @ResponseBody
    public String checkLogin( HttpSession session) {
        User user =(User)  session.getAttribute("user");
        if(null!=user)
            return "success";
        return "fail";
    }
	
	@RequestMapping("foreloginAjax")
    @ResponseBody
    public String loginAjax(@RequestParam("name") String name, @RequestParam("password") String password,HttpSession session) {
        name = HtmlUtils.htmlEscape(name);
        User user = userService.get(name,password);

        if(null==user){
            return "fail";
        }
        session.setAttribute("user", user);
        return "success";
    }
	
	@RequestMapping("forecart")
	public String cart(Model model,HttpSession session){
		User user = (User) session.getAttribute("user");
		List<OrderItem> ois = orderItemService.listByUser(user.getId());
		model.addAttribute("ois",ois);
		return "fore/cart";
	}
	
	@RequestMapping("/foreaddCart")
	@ResponseBody
	public String addCart(@RequestParam("pid") int pid,@RequestParam("num") int num,HttpSession session){
		Product p = productService.get(pid);
		User user = (User) session.getAttribute("user");
		
		boolean found = false;
		List<OrderItem> ois = orderItemService.listByUser(user.getId());
		for(OrderItem oi:ois){
			if(oi.getPid().intValue()==pid){
				oi.setNumber(oi.getNumber()+num);
				orderItemService.update(oi);
				found = true;
				break;
			}
		}
		if(!found){
			OrderItem oi = new OrderItem();
			oi.setNumber(num);
			oi.setPid(pid);
			oi.setUid(user.getId());
			orderItemService.add(oi);
		}
		return "success";
	}
	
	@RequestMapping("/forebuyone")
	public String buyone( Integer pid, Integer num,HttpSession session){
		Product product = productService.get(pid);
		User user = (User) session.getAttribute("user");
		
		boolean found = false;
		int orderItemId =0;
		
		List<OrderItem> ois = orderItemService.listByUser(user.getId());
		for(OrderItem oi:ois){
			if(oi.getProduct().getId().intValue()==product.getId().intValue()){
				oi.setNumber(oi.getNumber()+num);
				orderItemService.update(oi);
				
				found = true;
				orderItemId = oi.getId();
				break;
			}
		}
		if(!found){
			OrderItem oi =new OrderItem();
			oi.setNumber(num);
			oi.setPid(pid);
			oi.setUid(user.getId());
			orderItemService.add(oi);
			orderItemId=oi.getId();
		}
		
		return "redirect:/forebuy?oiid="+orderItemId;
	}
	
	@RequestMapping("/forebuy")
	public String buy(Model model,String[] oiid,HttpSession session){
		List<OrderItem> ois = new ArrayList<>();
		float total = 0;
		for(String str_oiid :oiid){
			int id = Integer.parseInt(str_oiid);
			OrderItem oi = orderItemService.get(id);
			Product p = productService.get(oi.getPid());
			oi.setProduct(p);
			orderItemService.update(oi);
			System.out.println(oi.getProduct().getId());
//			System.out.println(oi.getProduct().getId()+"123456");
			total+=oi.getProduct().getPromotePrice()*oi.getNumber();
//			ois.add(orderItemService.get(id));
			ois.add(oi);
		}
		session.setAttribute("ois", ois);
		model.addAttribute("total",total);
		return "fore/buy";
	}
	
	@RequestMapping("/foresearch")
	public String search(String keyword,String sort,Model model){
		PageHelper.offsetPage(0, 20);
		List<Product> ps = productService.search(keyword);
		
		if(sort!=null){
			switch (sort) {
			case "review":
				Collections.sort(ps,new ProductReviewComparator());
				break;
			case "saleCount":
				Collections.sort(ps,new ProductSaleCountComparator());
				break;
			case "price":
				Collections.sort(ps,new ProductPriceComparator());
				break;	
			case "date":
				Collections.sort(ps,new ProductDateComparator());
				break;
			case "all":
				Collections.sort(ps,new ProductAllComparator());
				break;
			default:
				break;
			}
		}
		
		model.addAttribute("ps",ps);
		model.addAttribute("keyword",keyword);
		return "fore/searchResult";
	}
	
	@RequestMapping("/forecategory")
	public String category(@RequestParam("cid") int cid,Model model,String sort){
		Category c =categoryService.get(cid);
		productService.fill(c);
		productService.setSaleAndReviewNumber(c.getProducts());
		
		if(sort!=null){
			switch (sort) {
			case "review":
				Collections.sort(c.getProducts(),new ProductReviewComparator());
				break;
				
			case "saleCount":
				Collections.sort(c.getProducts(),new ProductSaleCountComparator());
				break;

			case "price":
				Collections.sort(c.getProducts(),new ProductPriceComparator());
				break;
			
			case "all":
				Collections.sort(c.getProducts(),new ProductAllComparator());
				break;	
			
			case "date":
				Collections.sort(c.getProducts(),new ProductDateComparator());
				break;
			
			default:
			}
	
		}
		model.addAttribute("c",c);
		return "fore/category";
	}
	@RequestMapping("/foreproduct")
	public String product(Model model,@RequestParam("pid") int pid){
		Product p =productService.get(pid);
		List<ProductImage> productSingleImages = productImageService.list(pid, ProductImageService.type_single);
		List<ProductImage> productDetailImages = productImageService.list(pid, ProductImageService.type_detail);

		 p.setProductSingleImages(productSingleImages);
	     p.setProductDetailImages(productDetailImages);
	     
	     List<PropertyValue> pvs = propertyValueService.list(p.getId());
         List<Review> reviews = reviewService.list(p.getId());
         productService.setSaleAndReviewNumber(p);

         model.addAttribute("reviews", reviews);
         model.addAttribute("p", p);
         model.addAttribute("pvs", pvs);
         return "fore/product";
	}
	
	@RequestMapping("/forehome")
	public String home(Model model){
		List<Category> cs = categoryService.list();
		productService.fill(cs);
		productService.fillByRow(cs);
		System.out.println(cs.get(8).getProductsByRow());
		model.addAttribute("cs",cs);
		return "fore/home";
	}
	
	@RequestMapping("/foreregister")
	public String register(User user,Model model){
		String name = user.getName();
		//把账号里的特殊符号进行转义
		 name = HtmlUtils.htmlEscape(name);
		 user.setName(name);
		 boolean exist = userService.isExist(name);
		 if(exist){
			 String message = "用户名已经被使用,不能使用!";
			 model.addAttribute("user",null);
			 model.addAttribute("msg",message);
			 return "fore/register";
		 }else{
			 userService.add(user);
			 return "redirect:/registerSuccessPage";
			 
		 }
	}
	
	@RequestMapping("/forelogin")
	public String login(@RequestParam("name") String name,@RequestParam("password")String password,Model model, HttpSession session){
		name = HtmlUtils.htmlEscape(name);
		User user = userService.get(name, password);
		if(user==null){
			model.addAttribute("msg","账号密码错误！");
			return "fore/login";
		}
		session.setAttribute("user",user);
		return "redirect:/forehome";
	}
	
	@RequestMapping("/forelogout")
	public String logout(HttpSession session){
		session.removeAttribute("user");
		return "redirect:/forehome";
	}
}
