package com.appsdeveloper.estore.query;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.appsdeveloper.estore.entity.OrdersEntity;
import com.appsdeveloper.estore.repository.OrderRepository;

@Component
public class FindOrderQueryHandler {

	@Autowired
	private OrderRepository orderRepository;
	
	@QueryHandler
	public OrderSummary findOrder(FindOrderQuery findOrderQuery)
	{
		OrdersEntity entity= orderRepository.findByOrderId(findOrderQuery.getOrderId());
		return new OrderSummary(entity.getOrderId(), entity.getOrderStatus());
	}
}
