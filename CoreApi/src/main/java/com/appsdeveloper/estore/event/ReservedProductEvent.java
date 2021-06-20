package com.appsdeveloper.estore.event;

import lombok.Data;

@Data
public class ReservedProductEvent {

	private String orderId;
	private String userId;
	private String productId;
	private int quantity;
}
