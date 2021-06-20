package com.appsdeveloper.estore.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import com.appsdeveloper.estore.command.OrderStatus;

import lombok.Data;

@Entity
@Table(name = "Orders")
@Data
public class OrdersEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6045500661824993788L;
	
	@Id
	@Column(unique = true)
	private String orderId;
	private String userId;
	private String productId;
	private int quantity;
	private String addressId;
	private String orderStatus;
}
