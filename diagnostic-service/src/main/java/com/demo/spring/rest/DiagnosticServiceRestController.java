package com.demo.spring.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.entity.Diagnostic;
import com.demo.spring.repository.DiagnosticServiceRepository;
import com.demo.spring.util.Message;

@RestController
@RequestMapping("/diagnostic")
public class DiagnosticServiceRestController {
	@Autowired
	DiagnosticServiceRepository repo;

	Message message = new Message();
	Logger logger = LoggerFactory.getLogger(DiagnosticServiceRestController.class);

//	Add new tests in the list of tests
	@PostMapping(path = "/save")
	public ResponseEntity<Message> addDiagnostic(@Valid @RequestBody Diagnostic d) {
		repo.save(d);
		logger.info("Diagnostic is saved");
		message.setStatus("Diagnostic is saved");
		return new ResponseEntity<Message>(message, HttpStatus.OK);
	}

//	Update test
	@PutMapping(path = "/update/{id}")
	public ResponseEntity<Message> updateDiagnostic(@PathVariable("id") int id, @Valid @RequestBody Diagnostic d) {
		if (repo.findById(id).isEmpty()) {
			logger.error("No data found with id: {}", id);
			message.setStatus("Not found");
			return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
		} else {
			repo.save(d);
			logger.error("Diagnostic is updated");
			message.setStatus("Diagnostic is updated");
			return new ResponseEntity<Message>(message, HttpStatus.OK);
		}
	}

//	Remove tests from the list of tests
	@DeleteMapping(path = "/delete/{id}")
	public ResponseEntity<Message> deleteDiagnostic(@PathVariable("id") int id) {
		if (repo.findById(id).isEmpty()) {
			logger.error("No data found with id: {}", id);
			message.setStatus("Not found");
			return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
		} else {
			repo.deleteById(id);
			logger.info("Diagnostic is deleted");
			message.setStatus("Diagnostic is deleted");
			return new ResponseEntity<Message>(message, HttpStatus.OK);
		}
	}

//	List all the diagnostic tests available in the clinic
	@GetMapping("/get")
	public ResponseEntity<List<Diagnostic>> getAllDiagnostic() {
		return new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}
}
