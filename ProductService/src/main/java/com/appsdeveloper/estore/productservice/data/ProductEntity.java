package com.appsdeveloper.estore.productservice.data;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Table(name="products")
@Data
public class ProductEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8440990975207405552L;

	@Id
	@Column(unique = true)
	private String productId;
	@Column(unique = true)
	@Size(max = 20)
	private String title;
	private BigDecimal price;
	private Integer quantity;
}
