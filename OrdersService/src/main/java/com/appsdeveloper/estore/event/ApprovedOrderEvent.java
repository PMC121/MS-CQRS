package com.appsdeveloper.estore.event;

import com.appsdeveloper.estore.command.OrderStatus;

import lombok.Value;

@Value
public class ApprovedOrderEvent {

	private final String orderId;
	private OrderStatus orderStatus=OrderStatus.APPROVED;
}
