package com.demo.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.demo.spring.repository.PatientServiceDiagnosticRepository;
import com.demo.spring.repository.PatientServicePatientRepository;
import com.demo.spring.repository.PatientServiceVisitRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PatientServiceRestControllerTest {
	@Autowired
	MockMvc mvc;
	@MockBean
	PatientServiceDiagnosticRepository diagnosticRepo;
	@MockBean
	PatientServiceVisitRepository visitRepo;
	@MockBean
	PatientServicePatientRepository patientRepo;
//	 Register a new patient
	
//	********* Data validation for patient *********
//	 Find a patient by Id
//	 List of all patient
//	 Find a list of patients based on name/part name
//	 Update patient record data  (name, telephone, address) (Positive)
//	 Update patient record data  (name, telephone, address) (Negative)
	
//	 Add a visit for a patient with a doctor. (Positive)
//	 Add a visit for a patient with a doctor. (negative)
	
//	********* Data validation for visit *********
//	 Update visit (Positive)
//	 Update visit (Negative)
//	 Delete visit (positive)
//	 Delete visit (Negative)
//	 List all the appointments of a patient with Doctors
	
//	 Add a diagnostic test to a patient
//	********* Data validation for diagnostic *********
	
//	 Update particular test (positive)
//	 Update particular test (negative)
//	 Delete particular test (Positive)
//	 Delete particular test (Negative)
}
