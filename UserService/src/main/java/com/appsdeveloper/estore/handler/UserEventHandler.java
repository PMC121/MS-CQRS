package com.appsdeveloper.estore.handler;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import com.appsdeveloper.estore.model.PaymentDetails;
import com.appsdeveloper.estore.model.User;
import com.appsdeveloper.estore.query.FetchUserPaymentDetailsQuery;

@Component
public class UserEventHandler {

	@QueryHandler
	public User findUserPaymentDetail(FetchUserPaymentDetailsQuery fetchUserPaymentDetailsQuery)
	{
		 PaymentDetails paymentDetails = PaymentDetails.builder()
	                .cardNumber("123Card")
	                .cvv("123")
	                .name("SERGEY KARGOPOLOV")
	                .validUntilMonth(12)
	                .validUntilYear(2030)
	                .build();

	        User user = User.builder()
	                .firstName("Sergey")
	                .lastName("Kargopolov")
	                .userId(fetchUserPaymentDetailsQuery.getUserId())
	                .paymentDetails(paymentDetails)
	                .build();

	        return user;
	}
}
