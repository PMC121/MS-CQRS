package com.appsdeveloper.estore.command;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.appsdeveloper.estore.event.ApprovedOrderEvent;
import com.appsdeveloper.estore.event.OrderCreateEvent;
import com.appsdeveloper.estore.event.OrderRejectedEvent;


@Aggregate
public class OrderAggregate {

	@AggregateIdentifier
	private String orderId;
	private String userId;
	private String productId;
	private int quantity;
	private String addressId;
	private OrderStatus orderStatus;

	public OrderAggregate() {
		// TODO Auto-generated constructor stub
	}

	@CommandHandler
	public OrderAggregate(CreateOrderCommand createOrderCommand) {
		
		OrderCreateEvent createOrderEvent=new OrderCreateEvent();
		BeanUtils.copyProperties(createOrderCommand, createOrderEvent);
		System.out.println("OrderAggregate Class ::"+createOrderEvent.getOrderId());
		AggregateLifecycle.apply(createOrderEvent);
	}
	
	
	@EventSourcingHandler
	public void on(OrderCreateEvent createOrderEvent)
	{
		this.addressId=createOrderEvent.getAddressId();
		this.userId=createOrderEvent.getUserId();
		this.productId=createOrderEvent.getProductId();
		this.orderId=createOrderEvent.getOrderId();
		this.quantity=createOrderEvent.getQuantity();
		this.orderStatus=createOrderEvent.getOrderStatus();
	}

	@CommandHandler
	public void handle(ApprovedOrderCommand approvedOrderCommand)
	{
		ApprovedOrderEvent approvedOrderEvent=new ApprovedOrderEvent(approvedOrderCommand.getOrderId());
		AggregateLifecycle.apply(approvedOrderEvent);
	}
	
	@EventSourcingHandler
	public void on(ApprovedOrderEvent approvedOrderEvent)
	{
		this.orderStatus=approvedOrderEvent.getOrderStatus();
	}
	
	@CommandHandler
	public void handle(OrderRejectCommand orderRejectCommand)
	{
		OrderRejectedEvent orderRejectedEvent=OrderRejectedEvent.builder()
				.orderId(orderRejectCommand.getOrderId())
				.reason(orderRejectCommand.getReason())
				.orderStatus(OrderStatus.REJECTED)
				.build();
	}
	
	public void on(OrderRejectedEvent orderRejectedEvent)
	{
		this.orderStatus=orderRejectedEvent.getOrderStatus();
	}
}
