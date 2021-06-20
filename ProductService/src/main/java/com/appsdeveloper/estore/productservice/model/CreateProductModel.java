package com.appsdeveloper.estore.productservice.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductModel {

	private String title;
	private BigDecimal price;
	private Integer quantity;
}
