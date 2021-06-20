package com.appsdeveloper.estore.eventhandler;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.appsdeveloper.estore.entity.PaymentEntity;
import com.appsdeveloper.estore.entity.PaymentRepository;
import com.appsdeveloper.estore.event.PaymentProcessEvent;

@Component
public class PaymentEventHandler {

	@Autowired
	private PaymentRepository paymentRepository;
	
	public void on(PaymentProcessEvent event)
	{
		PaymentEntity paymentEntity=new PaymentEntity();
		
		BeanUtils.copyProperties(event, paymentEntity);
		
		paymentRepository.save(paymentEntity);
	}
}
