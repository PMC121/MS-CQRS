package com.appsdeveloper.estore.command;

import lombok.Data;

import com.appsdeveloper.estore.model.PaymentDetails;

import lombok.Builder;

@Builder
@Data
public class ProcessPaymentCommand {

	
	private String paymentId;
	private String orderId;
	private PaymentDetails paymentDetails;
}
