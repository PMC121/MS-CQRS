package com.appsdeveloper.estore.command;

import javax.persistence.Column;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import com.appsdeveloper.estore.event.PaymentProcessEvent;
import com.appsdeveloper.event.PaymentEvent;

import javassist.expr.NewArray;

@Aggregate
public class PaymentAggregate {

	@AggregateIdentifier
	private  String orderId;
	private  String paymentId;
	
	
	public PaymentAggregate() {
		// TODO Auto-generated constructor stub
	}
	
	@CommandHandler
	public PaymentAggregate(ProcessPaymentCommand command) {
		
		PaymentProcessEvent paymentProcessEvent=PaymentProcessEvent
				.builder()
				.orderId(command.getOrderId())
				.paymentId(command.getPaymentId())
				.build();
		AggregateLifecycle.apply(paymentProcessEvent);
		
	}
	
	@EventSourcingHandler
	public void on(PaymentProcessEvent paymentProcessEvent)
	{
		this.orderId=paymentProcessEvent.getOrderId();
		this.paymentId=paymentProcessEvent.getPaymentId();
	}
}
