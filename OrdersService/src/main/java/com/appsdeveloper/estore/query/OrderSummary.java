package com.appsdeveloper.estore.query;

import com.appsdeveloper.estore.command.OrderStatus;

import lombok.Value;

@Value
public class OrderSummary {

	private String orderId;
	private String orderStatus;
}
