package com.appsdeveloper.event;

import lombok.Data;

@Data
public class PaymentEvent {

	private String paymentId;
	private String orderId;
}
