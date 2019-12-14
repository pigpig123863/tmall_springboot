package comparator;

import java.util.Comparator;

import com.how2java.tmall.pojo.Product;

public class ProductPriceComparator implements Comparator<Product> {
	public int compare(Product p1,Product p2){
		double result = p2.getPromotePrice()-p1.getPromotePrice();
		
		if (result>0){
			return (int)(result+1);
		}else if(result==0){
			return 0;
		}else{
			return (int)(result-1);
		}
		 
	}
}
