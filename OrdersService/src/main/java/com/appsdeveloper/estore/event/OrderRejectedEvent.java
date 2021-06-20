package com.appsdeveloper.estore.event;

import com.appsdeveloper.estore.command.OrderStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderRejectedEvent {

	private String orderId;
	private String reason;
	private OrderStatus orderStatus=OrderStatus.REJECTED;
	
	
}
