package com.appsdeveloper.estore.productservice.query;

import java.util.List;

import org.axonframework.eventhandling.ResetHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.appsdeveloper.estore.productservice.data.ProductEntity;
import com.appsdeveloper.estore.productservice.data.ProductRepository;
import com.appsdeveloper.estore.productservice.querycontroller.ProductQueryModel;
import com.github.andrewoma.dexx.collection.ArrayList;

@Component
public class ProductQueryHandler {
	
	@Autowired
	private ProductRepository productRepository;
	
	@QueryHandler
	public List<ProductQueryModel> getProduct()
	{
		List<ProductQueryModel> productsList=new java.util.ArrayList<>();
		
		List<ProductEntity> productEntityList=productRepository.findAll();
		
		productEntityList.stream().forEach(p->
		{
			ProductQueryModel productQueryModel= new ProductQueryModel();
			BeanUtils.copyProperties(p,productQueryModel);
			productsList.add(productQueryModel);
			
		});
		
		return productsList;
	}
 
	@ResetHandler
	public void reset()
	{
		productRepository.deleteAll();
	}
}
