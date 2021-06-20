package com.appsdeveloper.estore.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="Payment")
@Data
public class PaymentEntity {

	@Id 
	private String paymentId;
	@Column
	public String orderId;
}
