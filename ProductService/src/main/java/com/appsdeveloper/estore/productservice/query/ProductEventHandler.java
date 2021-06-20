package com.appsdeveloper.estore.productservice.query;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.appsdeveloper.estore.event.CancelProductReservationEvent;
import com.appsdeveloper.estore.event.ReservedProductEvent;
import com.appsdeveloper.estore.productservice.core.event.ProductCreateEvent;
import com.appsdeveloper.estore.productservice.data.ProductEntity;
import com.appsdeveloper.estore.productservice.data.ProductRepository;

@Component
@ProcessingGroup("product-group")
public class ProductEventHandler {

	@Autowired
	ProductRepository productRepository;
	
	@EventHandler
	public void on(ProductCreateEvent productCreateEvent)
	{
		ProductEntity productEntity=new ProductEntity();
		BeanUtils.copyProperties(productCreateEvent, productEntity);
		try {
			productRepository.save(productEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@EventHandler
	public void on(ReservedProductEvent reservedProductEvent)
	{
		try {
			
			ProductEntity productEntity=productRepository.findByProductId(reservedProductEvent.getProductId());
			productEntity.setQuantity(reservedProductEvent.getQuantity());
			productRepository.save(productEntity);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@EventHandler
	public void on(CancelProductReservationEvent cancelProductReservationEvent)
	{
		try {
			
			ProductEntity productEntity=productRepository.findByProductId(cancelProductReservationEvent.getProductId());
			productEntity.setQuantity(cancelProductReservationEvent.getQuantity()+1);
			productRepository.save(productEntity);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
