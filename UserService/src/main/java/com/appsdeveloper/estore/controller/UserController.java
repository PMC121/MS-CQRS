package com.appsdeveloper.estore.controller;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appsdeveloper.estore.model.User;
import com.appsdeveloper.estore.query.FetchUserPaymentDetailsQuery;

@RestController
@RequestMapping("/user")
public class UserController {

	private QueryGateway queryGateway;

	@GetMapping("/{userid}/payment-details")
	public User getUserPaymentDetails(@PathVariable("userid") String uid) {
		FetchUserPaymentDetailsQuery fetchUserPaymentDetailsQuery = new FetchUserPaymentDetailsQuery();
		fetchUserPaymentDetailsQuery.setUserId(uid);
		return queryGateway.query(fetchUserPaymentDetailsQuery, ResponseTypes.instanceOf(User.class)).join();

	}
}
