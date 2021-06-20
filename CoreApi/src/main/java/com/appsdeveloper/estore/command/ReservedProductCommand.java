
package com.appsdeveloper.estore.command;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReservedProductCommand {
 
	@TargetAggregateIdentifier
	private String orderId;
	private String userId;
	private String productId;
	private int quantity;
	
}
