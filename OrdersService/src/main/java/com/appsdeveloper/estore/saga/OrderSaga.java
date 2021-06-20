package com.appsdeveloper.estore.saga;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.CommandResultMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.deadline.DeadlineManager;
import org.axonframework.deadline.annotation.DeadlineHandler;
import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.axonframework.spring.stereotype.Saga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.appsdeveloper.estore.command.ApprovedOrderCommand;
import com.appsdeveloper.estore.command.CancelProductReservationCommand;
import com.appsdeveloper.estore.command.OrderRejectCommand;
import com.appsdeveloper.estore.command.ProcessPaymentCommand;
import com.appsdeveloper.estore.command.ReservedProductCommand;
import com.appsdeveloper.estore.event.ApprovedOrderEvent;
import com.appsdeveloper.estore.event.CancelProductReservationEvent;
import com.appsdeveloper.estore.event.OrderCreateEvent;
import com.appsdeveloper.estore.event.OrderRejectedEvent;
import com.appsdeveloper.estore.event.PaymentProcessEvent;
import com.appsdeveloper.estore.event.ReservedProductEvent;
import com.appsdeveloper.estore.model.User;
import com.appsdeveloper.estore.query.FetchUserPaymentDetailsQuery;
import com.appsdeveloper.estore.query.FindOrderQuery;
import com.appsdeveloper.estore.query.OrderSummary;

import javassist.tools.Callback;

@Saga
public class OrderSaga {

	@Autowired
	private transient CommandGateway commandGateway;

	@Autowired
	private QueryGateway queryGateway;
	
	@Autowired
	private transient DeadlineManager deadlineManager;
	
	@Autowired
	private transient QueryUpdateEmitter queryUpdateEmitter;

	private static final Logger log = LoggerFactory.getLogger(OrderSaga.class);
	
	String deadlineId=null;

	@StartSaga
	@SagaEventHandler(associationProperty = "orderId")
	public void handle(OrderCreateEvent orderCreateEvent) {
		ReservedProductCommand reservedProductCommand = ReservedProductCommand.builder()
				.orderId(orderCreateEvent.getOrderId()).productId(orderCreateEvent.getProductId())
				.userId(orderCreateEvent.getUserId()).quantity(orderCreateEvent.getQuantity()).build();

		commandGateway.send(reservedProductCommand, new CommandCallback<ReservedProductCommand, Object>() {

			@Override
			public void onResult(CommandMessage<? extends ReservedProductCommand> commandMessage,
					CommandResultMessage<? extends Object> commandResultMessage) {
				// TODO Auto-generated method stub

			}
		});
	}

	@SagaEventHandler(associationProperty = "orderId")
	public void handle(ReservedProductEvent reservedProductEvent) {
		log.debug("Enter in ReservedProductEvent");
		String result = null;
		FetchUserPaymentDetailsQuery fetchUserPaymentDetailsQuery = new FetchUserPaymentDetailsQuery();
		fetchUserPaymentDetailsQuery.setUserId(reservedProductEvent.getOrderId());
		User userDetailsUser = null;
		try {
			userDetailsUser = queryGateway.query(fetchUserPaymentDetailsQuery, ResponseTypes.instanceOf(User.class))
					.join();

		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			CancelProductReservationCommand(reservedProductEvent, e.getMessage());
		}

		deadlineId=deadlineManager.schedule(Duration.ofSeconds(10), "payement-processing-dealine", reservedProductEvent);
	
		ProcessPaymentCommand processPaymentCommand = ProcessPaymentCommand.builder()
				.paymentId(UUID.randomUUID().toString()).orderId(reservedProductEvent.getOrderId())
				.paymentDetails(userDetailsUser.getPaymentDetails()).build();

		try {
			result = commandGateway.sendAndWait(processPaymentCommand, 10, TimeUnit.SECONDS);
		} catch (Exception e) {
			log.error(e.getMessage());
			CancelProductReservationCommand(reservedProductEvent, "Could not fetch user payment details...");
		}

		if (result == null) {
			log.info("Payment Result is::" + result);
			CancelProductReservationCommand(reservedProductEvent, "Could not process user payment with provided details..");
		}
	}
	
	public void cancelDealine(String scheduledId)
	{
		if(scheduledId!=null)
		{
		deadlineManager.cancelSchedule("payement-processing-dealine",scheduledId);
		}
	}
	public void CancelProductReservationCommand(ReservedProductEvent reservedProductEvent,String reason)
	{
		CancelProductReservationCommand cancelProductReservationCommand=CancelProductReservationCommand
				.builder()
				.productId(reservedProductEvent.getProductId())
				.orderId(reservedProductEvent.getOrderId())
				.quantity(reservedProductEvent.getQuantity())
				.userId(reservedProductEvent.getUserId())
				.reason(reason)
				.build();
		
		commandGateway.send(cancelProductReservationCommand);
	}
	
	@SagaEventHandler(associationProperty = "orderId")
	public void handle(PaymentProcessEvent paymentProcessEvent)
	{
		cancelDealine(deadlineId);
		ApprovedOrderCommand approvedOrderCommand=new ApprovedOrderCommand(paymentProcessEvent.getOrderId());
		commandGateway.send(approvedOrderCommand);
	}
	
	@EndSaga
	@SagaEventHandler(associationProperty = "orderId")
	public void handle(ApprovedOrderEvent approvedOrderEvent)
	{
		queryUpdateEmitter.emit(FindOrderQuery.class, q->true,
				new OrderSummary(approvedOrderEvent.getOrderId(), approvedOrderEvent.getOrderStatus().toString()));
		log.debug("Order Approved sucessfully");
	}
	
	@SagaEventHandler(associationProperty = "orderId")
	public void handle(CancelProductReservationEvent cancelProductReservationEvent)
	{
		OrderRejectCommand orderRejectCommand=new OrderRejectCommand(cancelProductReservationEvent.getOrderId(), cancelProductReservationEvent.getReason());
		commandGateway.send(orderRejectCommand);
	}
	
	@SagaEventHandler(associationProperty = "orderId")
	public void handle(OrderRejectedEvent orderRejectedEvent)
	{
		queryUpdateEmitter.emit(FindOrderQuery.class, q->true,
				new OrderSummary(orderRejectedEvent.getOrderId(), orderRejectedEvent.getOrderStatus().toString()));
		log.debug("Order Rejected sucessfully");
	}
	
	@DeadlineHandler(deadlineName = "payement-processing-dealine")
	public void handlePaymentDeadline(ReservedProductEvent reservedProductEvent)
	{
	CancelProductReservationCommand(reservedProductEvent, "Payment Timeout happen");	
	}
}
