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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.entity.Doctor;
import com.demo.spring.entity.Specialty;
import com.demo.spring.repository.DoctorServiceDoctorRepository;
import com.demo.spring.repository.DoctorServiceSpecialtyRepository;
import com.demo.spring.util.Message;

@RestController
@RequestMapping("/doctor")
public class DoctorServiceRestController {
	@Autowired
	DoctorServiceDoctorRepository doctorRepo;
	@Autowired
	DoctorServiceSpecialtyRepository specialtyRepo;

	Message message = new Message();
	Logger logger = LoggerFactory.getLogger(DoctorServiceRestController.class);

//	 Create specialty
	@PostMapping(path = "/specialty/save")
	public ResponseEntity<Message> addSpecialty(@Valid @RequestBody Specialty s) {
		specialtyRepo.save(s);
		logger.info("Specialty is saved");
		message.setStatus("Specialty is saved");
		return new ResponseEntity<Message>(message, HttpStatus.OK);
	}

//	 Update specialty
	@PutMapping(path = "/specialty/update/{id}")
	public ResponseEntity<Message> updateSpecialty(@PathVariable("id") int id, @Valid @RequestBody Specialty s) {
		if (specialtyRepo.findById(id).isEmpty()) {
			logger.error("No data found with id: {}", id);
			message.setStatus("Not found");
			return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
		} else {
			specialtyRepo.save(s);
			logger.info("Specialty is updated");
			message.setStatus("Specialty is updated");
			return new ResponseEntity<Message>(message, HttpStatus.OK);
		}
	}

//	 Add a doctor to a specialty
	@PostMapping(path = "/specialty/update/{id}/add/doctor")
	public ResponseEntity<Message> addDoctorToSpecialty(@PathVariable("id") int id, @Valid @RequestBody Doctor d) {
		Optional<Specialty> specialtyOp = specialtyRepo.findById(id);
		if (specialtyOp.isEmpty()) {
			logger.error("No data found with id: {}", id);
			message.setStatus("Not found");
			return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
		} else {
			specialtyOp.get().getDoctors().add(d);
			specialtyRepo.save(specialtyOp.get());
			logger.info("Doctor is added to specialty {}", specialtyOp.get().getSpecialtyName());
			message.setStatus("Doctor is added to specialty " + specialtyOp.get().getSpecialtyName());
			return new ResponseEntity<Message>(message, HttpStatus.OK);
		}
	}

//	 Remove a doctor from a specialty
	@DeleteMapping(path = "/specialty/update/{specialtyId}/delete/doctor/{doctorId}")
	public ResponseEntity<Message> deleteDoctorFromSpecialty(@PathVariable("specialtyId") int specialtyId,
			@PathVariable("doctorId") int doctorId) {
		if (specialtyRepo.findById(specialtyId).isEmpty() || doctorRepo.findById(doctorId).isEmpty()) {
			logger.error("No data found");
			message.setStatus("Not found");
			return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
		} else {
			doctorRepo.deleteById(doctorId);
			logger.info("Doctor is deleted from specialty");
			message.setStatus("Doctor is deleted from specialty");
			return new ResponseEntity<Message>(message, HttpStatus.OK);
		}
	}

//	 Update doctor details
	@PutMapping(path = "/specialty/update/{specialtyId}/update/doctor/{doctorId}")
	public ResponseEntity<Message> updateDoctorOfSpecialty(@PathVariable("specialtyId") int specialtyId,
			@PathVariable("doctorId") int doctorId, @Valid @RequestBody Doctor d) {
		if (specialtyRepo.findById(specialtyId).isEmpty() || doctorRepo.findById(doctorId).isEmpty()) {
			logger.error("No data found");
			message.setStatus("Not found");
			return new ResponseEntity<Message>(message, HttpStatus.BAD_REQUEST);
		} else {
			doctorRepo.save(d);
			logger.info("Doctor is updated");
			message.setStatus("Doctor is updated");
			return new ResponseEntity<Message>(message, HttpStatus.OK);
		}
	}

//	 List all the doctors in a particular specialty
	@GetMapping(path = "/getBySpecialty/{specialtyName}")
	public ResponseEntity<List<Doctor>> getAllDoctorsBySpecialty(@PathVariable("specialtyName") String specialtyName) {
		Optional<Specialty> specialtyOp = specialtyRepo.findBySpecialtyName(specialtyName);
		if (specialtyOp.isEmpty()) {
			logger.error("No data found");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<Doctor>>(specialtyOp.get().getDoctors(), HttpStatus.OK);
		}
	}

//	 List all doctors data
	@GetMapping(path = "/get")
	public ResponseEntity<List<Doctor>> getAllDoctors() {
		return new ResponseEntity<List<Doctor>>(doctorRepo.findAll(), HttpStatus.OK);
	}
	
//	List All specialty
	@GetMapping(path = "/specialty/get")
	public ResponseEntity<List<Specialty>> getAllSpecialty() {
		return new ResponseEntity<List<Specialty>>(specialtyRepo.findAll(), HttpStatus.OK);
	}
//	 List all the appointments for a doctor in a particular date
	
	
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
