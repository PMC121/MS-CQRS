package com.appsdeveloper.estore.productservice.controller;

import java.util.Optional;

import org.axonframework.config.EventProcessingConfiguration;
import org.axonframework.eventhandling.TrackingEventProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/management")
public class EventReplayController {

	@Autowired
	public EventProcessingConfiguration eventProcessingConfiguration;
	
	@PostMapping("/event/{processname}")
	public ResponseEntity<String> replayEvent(@PathVariable("processname") String processName) {
	
	 Optional<TrackingEventProcessor> traOptional=	eventProcessingConfiguration
		.eventProcessor(processName,TrackingEventProcessor.class);
	 
	 if(traOptional.isPresent()) {
	 TrackingEventProcessor eventProcessor=traOptional.get();
	 eventProcessor.shutDown();
	 eventProcessor.resetTokens();
	 eventProcessor.start();
	 
	 return ResponseEntity
			 .ok()
			 .body(String.format("The Event Processor Name reset", processName));
	 }
	 else {
		 return ResponseEntity
				 .badRequest()
				 .body(String.format("The Event Processor Name not tracking reset", processName));
	 }
	}
}
