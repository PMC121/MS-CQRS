package com.appsdeveloper.estore.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Value;

@Value
public class OrderRejectCommand {

	@TargetAggregateIdentifier
	private String orderId;
	private String reason;
}
