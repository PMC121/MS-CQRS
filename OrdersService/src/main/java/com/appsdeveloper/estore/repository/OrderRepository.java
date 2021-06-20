package com.appsdeveloper.estore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.appsdeveloper.estore.entity.OrdersEntity;

public interface OrderRepository extends JpaRepository<OrdersEntity, String> {

	OrdersEntity findByOrderId(String orderId);

}
