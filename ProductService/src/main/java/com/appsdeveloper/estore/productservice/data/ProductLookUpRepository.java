package com.appsdeveloper.estore.productservice.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductLookUpRepository extends JpaRepository<ProductLookUpEntirty, String> {

	ProductLookUpEntirty findByProductIdOrTitle(String productId,String title);
}
