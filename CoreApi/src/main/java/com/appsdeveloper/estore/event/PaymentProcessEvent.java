package com.appsdeveloper.estore.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentProcessEvent {

	private String orderId;
	private String paymentId;
	
}
