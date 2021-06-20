package com.appsdeveloper.estore.productservice.querycontroller;

import java.util.List;

import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appsdeveloper.estore.productservice.query.FindProductsQuery;

@RestController("/api/v1/products/")
public class ProductQueryController {

	@Autowired
	private QueryGateway queryGateway;
	
	@GetMapping
	public List<ProductQueryModel> getProducts()
	{
		FindProductsQuery findProductsQuery=new FindProductsQuery();
		List<ProductQueryModel> productsList=queryGateway
				.query(findProductsQuery, ResponseTypes.multipleInstancesOf(ProductQueryModel.class)).join();
	
	  return productsList;
	}
}
