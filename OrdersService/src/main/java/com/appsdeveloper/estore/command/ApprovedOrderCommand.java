package com.appsdeveloper.estore.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;

import lombok.Data;

@Data
@AllArgsConstructor
public class ApprovedOrderCommand {

	@TargetAggregateIdentifier
	public String orderId;
}
