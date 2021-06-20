package com.appsdeveloper.estore.eventhandling;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.appsdeveloper.estore.entity.OrdersEntity;
import com.appsdeveloper.estore.event.ApprovedOrderEvent;
import com.appsdeveloper.estore.event.OrderCreateEvent;
import com.appsdeveloper.estore.event.OrderRejectedEvent;
import com.appsdeveloper.estore.repository.OrderRepository;

@Component
@ProcessingGroup("order-group")
public class OrderEventsHandler {

	private final OrderRepository orderRepository;

	public OrderEventsHandler(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@EventHandler
	public void on(OrderCreateEvent createOrderEvent) throws Exception {
		System.out.println("Handler Class ::" + createOrderEvent.getAddressId());
		OrdersEntity entity = new OrdersEntity();
		BeanUtils.copyProperties(createOrderEvent, entity);
		this.orderRepository.save(entity);
	}

	@EventHandler
	public void on(ApprovedOrderEvent approvedOrderEvent) throws Exception {
		OrdersEntity entity = this.orderRepository.findByOrderId(approvedOrderEvent.getOrderId());
		if (entity == null) {
			return;
		}

		entity.setOrderStatus(approvedOrderEvent.getOrderStatus().toString());
		this.orderRepository.save(entity);

	}
	
	public void on(OrderRejectedEvent event)
	{
	  OrdersEntity ordersEntity=this.orderRepository.findByOrderId(event.getOrderId());
	  ordersEntity.setOrderStatus(event.getOrderStatus().toString());
	  this.orderRepository.save(ordersEntity);
	}

}
