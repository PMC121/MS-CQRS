package com.appsdeveloper.estore.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CancelProductReservationCommand {

	@TargetAggregateIdentifier
	private String productId;
	private String orderId;
	private int quantity;
	private String userId;
	private String reason;
	
}
