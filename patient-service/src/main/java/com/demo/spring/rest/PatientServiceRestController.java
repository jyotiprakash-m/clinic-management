package com.demo.spring.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.entity.Diagnostic;
import com.demo.spring.entity.Patient;
import com.demo.spring.entity.Visit;
import com.demo.spring.repository.PatientServiceDiagnosticRepository;
import com.demo.spring.repository.PatientServicePatientRepository;
import com.demo.spring.repository.PatientServiceVisitRepository;
import com.demo.spring.util.Message;

@RestController
@RequestMapping("/patient")
public class PatientServiceRestController {
	@Autowired
	PatientServicePatientRepository patientRepo;

	@Autowired
	PatientServiceDiagnosticRepository diagnosticRepo;
	
	@Autowired
	PatientServiceVisitRepository visitRepo;

	Message message = new Message();
	Logger logger = LoggerFactory.getLogger(PatientServiceRestController.class);

//	 Register a new patient
	@PostMapping(path = "/save")
	public ResponseEntity<Message> addPatient(@Valid @RequestBody Patient p) {
		patientRepo.save(p);
		logger.info("Patient is saved");
		message.setStatus("Patient is saved");
		return new ResponseEntity<Message>(message, HttpStatus.OK);
	}

//	 Update patient record data  (name, telephone, address)
	@PutMapping(path = "/update/{id}")
	public ResponseEntity<Message> updatePatient(@PathVariable("id") int id, @Valid @RequestBody Patient p) {
		if (patientRepo.findById(id).isEmpty()) {
			logger.error("No data found with id: {}", id);
			message.setStatus("Not found");
			return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
		} else {
			patientRepo.save(p);
			logger.info("Patient is updated");
			message.setStatus("Patient is updated");
			return new ResponseEntity<Message>(message, HttpStatus.OK);
		}
	}

//	 Find a patient by Id
	@GetMapping(path = "/get/{id}")
	public ResponseEntity<Patient> getPatientById(@PathVariable("id") int id) {
		Optional<Patient> patientOp = patientRepo.findById(id);
		if (patientOp.isEmpty()) {
			logger.error("No data found with id: {}", id);
			return new ResponseEntity<Patient>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<Patient>(patientOp.get(), HttpStatus.OK);
		}
	}

//	 List of all patient
	@GetMapping(path = "/get")
	public ResponseEntity<List<Patient>> getAllPatient() {
		return new ResponseEntity<List<Patient>>(patientRepo.findAll(), HttpStatus.OK);
	}

//	 Find a list of patients based on name/part name
	@GetMapping(path = "/getByName")
	public ResponseEntity<List<Patient>> getAllPatientName(@RequestParam String name) {
		return new ResponseEntity<List<Patient>>(patientRepo.searchByPatientNameLike(name), HttpStatus.OK);
	}
	
//	 Add a visit for a patient with a doctor.
	@PostMapping(path = "/update/{id}/add/visit")
	public ResponseEntity<Message> addDoctorToPatient(@PathVariable("id") int id, @Valid @RequestBody Visit v) {
		Optional<Patient> patientOp = patientRepo.findById(id);
		if (patientOp.isEmpty()) {
			logger.error("No data found with id: {}", id);
			message.setStatus("Not found");
			return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
		} else {
			patientOp.get().getVisits().add(v);
			patientRepo.save(patientOp.get());
			logger.info("Visit is added to patient {}", patientOp.get().getPatientName());
			message.setStatus("Visit is added to patient " + patientOp.get().getPatientName());
			return new ResponseEntity<Message>(message, HttpStatus.OK);
		}
	}
//	 Update visit
	@PutMapping(path = "/update/{patientId}/update/visit/{visitId}")
	public ResponseEntity<Message> updateDoctorOfPatient(@PathVariable("patientId") int patientId,
			@PathVariable("visitId") int visitId, @Valid @RequestBody Visit v) {
		if (visitRepo.findById(visitId).isEmpty()) {
			logger.error("No data found");
			message.setStatus("Not found");
			return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
		} else {
			visitRepo.save(v);
			logger.info("Visit is updated");
			message.setStatus("Visit is updated");
			return new ResponseEntity<Message>(message, HttpStatus.OK);
		}
	}
//	 Delete visit
	@DeleteMapping(path = "/update/{patientId}/delete/visit/{visitId}")
	public ResponseEntity<Message> deleteDoctorOfPatient(@PathVariable("patientId") int patientId,
			@PathVariable("visitId") int visitId) {
		if (visitRepo.findById(visitId).isEmpty()) {
			logger.error("No data found");
			message.setStatus("Not found");
			return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
		} else {
			visitRepo.deleteById(visitId);
			logger.info("Visit is deleted");
			message.setStatus("Visit is deleted");
			return new ResponseEntity<Message>(message, HttpStatus.OK);
		}
	}
	
//	 List all the appointments of a patient with Doctors
	@GetMapping(path = "/get/{patientId}/visits")
	public ResponseEntity<List<Visit>> getAllDoctorsBySpecialty(@PathVariable("patientId") int patientId) {
		Optional<Patient> patientOp = patientRepo.findById(patientId);
		if (patientOp.isEmpty()) {
			logger.error("No data found");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<Visit>>(patientOp.get().getVisits(), HttpStatus.OK);
		}
	}
	
//	 Add a diagnostic test to a patient
	@PostMapping(path = "/update/{id}/add/diagnostic")
	public ResponseEntity<Message> addDiagnosticToPatient(@PathVariable("id") int id, @Valid @RequestBody Diagnostic d) {
		Optional<Patient> patientOp = patientRepo.findById(id);
		if (patientOp.isEmpty()) {
			logger.error("No data found with id: {}", id);
			message.setStatus("Not found");
			return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
		} else {
			patientOp.get().getDiagnostics().add(d);
			patientRepo.save(patientOp.get());
			logger.info("Diagnostics is added to patient {}", patientOp.get().getPatientName());
			message.setStatus("Diagnostics is added to patient " + patientOp.get().getPatientName());
			return new ResponseEntity<Message>(message, HttpStatus.OK);
		}
	}
//	 Update particular test
	@PutMapping(path = "/update/{patientId}/update/diagnostic/{diagnosticId}")
	public ResponseEntity<Message> updateDiagnosticOfPatient(@PathVariable("patientId") int patientId,
			@PathVariable("diagnosticId") int diagnosticId, @Valid @RequestBody Diagnostic d) {
		if (diagnosticRepo.findById(diagnosticId).isEmpty()) {
			logger.error("No data found");
			message.setStatus("Not found");
			return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
		} else {
			diagnosticRepo.save(d);
			logger.info("Diagnostic is updated");
			message.setStatus("Diagnostic is updated");
			return new ResponseEntity<Message>(message, HttpStatus.OK);
		}
	}
//	 Delete particular test
	@DeleteMapping(path = "/update/{patientId}/delete/diagnostic/{diagnosticId}")
	public ResponseEntity<Message> deleteDiagnosticFromPatient(@PathVariable("patientId") int patientId,
			@PathVariable("diagnosticId") int diagnosticId) {
		if (diagnosticRepo.findById(diagnosticId).isEmpty()) {
			logger.error("No data found");
			message.setStatus("Not found");
			return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
		} else {
			diagnosticRepo.deleteById(diagnosticId);
			logger.info("Diagnostic is deleted");
			message.setStatus("Diagnostic is deleted");
			return new ResponseEntity<Message>(message, HttpStatus.OK);
		}
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
