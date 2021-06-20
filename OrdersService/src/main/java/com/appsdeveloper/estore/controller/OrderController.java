package com.appsdeveloper.estore.controller;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appsdeveloper.estore.command.CreateOrderCommand;
import com.appsdeveloper.estore.command.OrderStatus;
import com.appsdeveloper.estore.model.CreateOrderModel;
import com.appsdeveloper.estore.query.FindOrderQuery;
import com.appsdeveloper.estore.query.OrderSummary;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

	private final CommandGateway commandGateway;

	private QueryGateway queryGateway;

	@Autowired
	public OrderController(CommandGateway commandGateway, QueryGateway queryGateway) {
		this.commandGateway = commandGateway;
		this.queryGateway = queryGateway;
	}

	@PostMapping("/")
	public OrderSummary reservedOrder(@RequestBody CreateOrderModel createOrderModel) {
		System.out.println("Value is::" + createOrderModel.getProductId());
		String resultString = null;
		CreateOrderCommand createOrderCommand = CreateOrderCommand.builder().productId(UUID.randomUUID().toString())
				.orderId(UUID.randomUUID().toString()).userId(UUID.randomUUID().toString())
				.addressId(UUID.randomUUID().toString()).orderStatus(OrderStatus.CREATED).quantity(1).build();
		SubscriptionQueryResult<OrderSummary, OrderSummary> subscriptionQueryResult = queryGateway.subscriptionQuery(
				new FindOrderQuery(createOrderCommand.getOrderId()), ResponseTypes.instanceOf(OrderSummary.class),
				ResponseTypes.instanceOf(OrderSummary.class));

		try {
			commandGateway.sendAndWait(createOrderCommand);
			return subscriptionQueryResult.updates().blockFirst();
		} finally {
			subscriptionQueryResult.close();
		}

	}
}
