package com.demo.spring.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.util.Message;

@RestController
public class FallbackRestController {
	Message message = new Message();
	Logger logger = LoggerFactory.getLogger(FallbackRestController.class);
	
	@GetMapping("/diagnosticServiceFallback")
	public ResponseEntity<Message> diagnosticServiceFallback() {
		logger.error("Diagnostic service is down");
		message.setStatus("Diagnostic service is down");
		return new ResponseEntity<>(message, HttpStatus.SERVICE_UNAVAILABLE);
	}

	@GetMapping("/doctorServiceFallback")
	public ResponseEntity<Message> doctorServiceFallback() {
		logger.error("Doctor service is down");
		message.setStatus("Doctor service is down");
		return new ResponseEntity<>(message, HttpStatus.SERVICE_UNAVAILABLE);
	}

	@GetMapping("/patientServiceFallback")
	public ResponseEntity<Message> patientServiceFallback() {
		logger.error("Patient service is down");
		message.setStatus("Patient service is down");
		return new ResponseEntity<>(message, HttpStatus.SERVICE_UNAVAILABLE);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Message> handleError(Exception ex) {
		logger.error(ex.getMessage());
		message.setStatus("Something went wrong");
		return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
