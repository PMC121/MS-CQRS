package com.appsdeveloper.estore.productservice.query;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.ResetHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.appsdeveloper.estore.productservice.core.event.ProductCreateEvent;
import com.appsdeveloper.estore.productservice.data.ProductLookUpEntirty;
import com.appsdeveloper.estore.productservice.data.ProductLookUpRepository;

@Component
@ProcessingGroup("product-group")
public class ProductLookUPHandler {

	private ProductLookUpRepository productLookUpRepository;
	
	@Autowired
	public ProductLookUPHandler(ProductLookUpRepository productLookUpRepository) {
		this.productLookUpRepository = productLookUpRepository;
	}
	
	@EventHandler
	public void on(ProductCreateEvent createEvent)
	{
		ProductLookUpEntirty productLookUpEntirty=new ProductLookUpEntirty(createEvent.getProductId(), createEvent.getTitle());
		productLookUpRepository.save(productLookUpEntirty);
	}
	@ResetHandler
	public void reset()
	{
		productLookUpRepository.deleteAll();
	}

}
