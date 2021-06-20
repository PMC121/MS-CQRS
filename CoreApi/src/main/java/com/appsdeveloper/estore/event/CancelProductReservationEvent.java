package com.appsdeveloper.estore.event;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Data;

@Data
public class CancelProductReservationEvent {

	private String productId;
	private String orderId;
	private int quantity;
	private String userId;
	private String reason;
}
