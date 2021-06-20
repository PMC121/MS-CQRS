package com.appsdeveloper.estore.productservice.controller;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appsdeveloper.estore.productservice.command.CreateProductCommand;
import com.appsdeveloper.estore.productservice.model.CreateProductModel;

@RestController
@RequestMapping("/api/v1/product/")
public class ProductController {

	private Environment environment;
	private CommandGateway commandGateway;
	
	@Autowired
	public ProductController(Environment environment,CommandGateway commandGateway) {
		this.environment=environment;
		this.commandGateway=commandGateway;
	}
	
	@GetMapping
	public String getProducts()
	{
		return "Product List Return";
	}
	
	@PostMapping
	public String createProduct(@RequestBody CreateProductModel createProductModel)
	{
		String returnResult;
		CreateProductCommand createProductCommand=CreateProductCommand.builder()
				.price(createProductModel.getPrice())
				.quantity(createProductModel.getQuantity())
				.title(createProductModel.getTitle())
				.productId(UUID.randomUUID().toString())
				.build();
		
				try {
					 returnResult=commandGateway.sendAndWait(createProductCommand);
				} catch (Exception e) {
					 returnResult=e.getLocalizedMessage();
				}
		
		 
	
		return returnResult;
	}
	
	public String updateProduct()
	{
		return "Product Created";
	}
	
	public String deleteProduct()
	{
		return "Product Created";	
	}
}
