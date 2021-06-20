package com.appsdeveloper.estore.productservice.data;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="productlookup")
public class ProductLookUpEntirty implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3429287729128611068L;

	@Id
	@Column(unique = true)
	private String productId;
	@Column(unique = true)
	@Size(max = 20)
	private String title;
}
