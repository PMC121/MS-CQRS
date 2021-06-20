package com.appsdeveloper.estore.productservice.command;

import java.math.BigDecimal;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;

import com.appsdeveloper.estore.command.CancelProductReservationCommand;
import com.appsdeveloper.estore.command.ReservedProductCommand;
import com.appsdeveloper.estore.event.CancelProductReservationEvent;
import com.appsdeveloper.estore.event.ReservedProductEvent;
import com.appsdeveloper.estore.productservice.core.event.ProductCreateEvent;

@Aggregate
public class ProductAggregate {

	@AggregateIdentifier
	private String productId;
	private String title;
	private BigDecimal price;
	private Integer quantity;
	
	public ProductAggregate() {
		// TODO Auto-generated constructor stub
	}
	
	@CommandHandler
	public ProductAggregate(CreateProductCommand createProductCommand)
	{
		//sample validation
		
		if(createProductCommand.getTitle()==null || createProductCommand.getTitle().isEmpty()==true)
		{
			throw new IllegalArgumentException("Product Title Should Not Be Null");
		}
		
		ProductCreateEvent productCreateEvent=new ProductCreateEvent();
		BeanUtils.copyProperties(createProductCommand, productCreateEvent); //this is mapped class properties which is common in both class
		
		AggregateLifecycle.apply(productCreateEvent);
	}
	
	@CommandHandler
	public void hadle(ReservedProductCommand reservedProductCommand)
	{
		if(quantity<reservedProductCommand.getQuantity())
		{
			throw new IllegalArgumentException("Insufficient number of items in stock");
		}
		
		ReservedProductEvent reservedProductEvent=new ReservedProductEvent();
		BeanUtils.copyProperties(reservedProductCommand, reservedProductEvent);
		AggregateLifecycle.apply(reservedProductEvent);
	}
	
	@EventSourcingHandler
	public void on(ProductCreateEvent productCreateEvent )
	{
		this.title=productCreateEvent.getTitle();
		this.productId=productCreateEvent.getProductId();
		this.price=productCreateEvent.getPrice();
		this.quantity=productCreateEvent.getQuantity();
	}
	
	@EventSourcingHandler
	public void on(ReservedProductEvent reservedProductEvent)
	{
		this.quantity-=reservedProductEvent.getQuantity();
	}
	
	@CommandHandler
	public void handle(CancelProductReservationCommand cancelProductReservationCommand)
	{
		  CancelProductReservationEvent cancelProductReservationEvent=new CancelProductReservationEvent();
		  BeanUtils.copyProperties(cancelProductReservationCommand, cancelProductReservationEvent);
		  AggregateLifecycle.apply(cancelProductReservationEvent);
	}
	
	@EventSourcingHandler
	public void on(CancelProductReservationEvent cancelProductReservationEvent )
	{
		this.quantity+=cancelProductReservationEvent.getQuantity();
	}
}
