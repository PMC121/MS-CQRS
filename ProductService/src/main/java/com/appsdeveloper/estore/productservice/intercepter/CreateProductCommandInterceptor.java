package com.appsdeveloper.estore.productservice.intercepter;

import java.util.List;
import java.util.function.BiFunction;

import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.appsdeveloper.estore.productservice.command.CreateProductCommand;
import com.appsdeveloper.estore.productservice.data.ProductLookUpEntirty;
import com.appsdeveloper.estore.productservice.data.ProductLookUpRepository;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CreateProductCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

	private ProductLookUpRepository productLookUpRepository;
	
	
	CreateProductCommandInterceptor(ProductLookUpRepository productLookUpRepository)
	{
		this.productLookUpRepository = productLookUpRepository;
	}
	
	@Override
	public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(
			List<? extends CommandMessage<?>> messages) {
		log.debug("Inside Intercepter::");
		return (index,command)->{
			if(CreateProductCommand.class.equals(command.getPayloadType()))
			{
				CreateProductCommand createProductCommand=(CreateProductCommand) command.getPayload();
				/*
				 * ProductLookUpEntirty productLookUpEntirty=
				 * productLookUpRepository.findByProductIdOrTitle(createProductCommand.
				 * getProductId(), createProductCommand.getTitle());
				 * 
				 * log.debug("Product Details::"+productLookUpEntirty.getProductId());
				 * if(productLookUpEntirty!=null) { throw new IllegalArgumentException(
				 * String.format("Product with product id : %s or Title : %s already Exists",
				 * createProductCommand.getProductId(),createProductCommand.getTitle())); }
				 */
			}
			return command;
		};
		
	}

}
