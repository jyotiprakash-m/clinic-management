package com.demo.spring;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.demo.spring.entity.Address;
import com.demo.spring.entity.Doctor;
import com.demo.spring.entity.Specialty;
import com.demo.spring.repository.DoctorServiceDoctorRepository;
import com.demo.spring.repository.DoctorServiceSpecialtyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class DoctorServiceRestControllerTest {
	@Autowired
	MockMvc mvc;

	@MockBean
	DoctorServiceDoctorRepository doctorRepo;
	@MockBean
	DoctorServiceSpecialtyRepository specialtyRepo;

//	 Create specialty
	@Test
	public void testAddSpecialty() throws Exception {
		Specialty specialty = new Specialty("Dentist");
		when(specialtyRepo.save(specialty)).thenReturn(specialty);
		ObjectMapper om = new ObjectMapper();
		String value = om.writeValueAsString(specialty);
		mvc.perform(post("/doctor/specialty/save").contentType(MediaType.APPLICATION_JSON).content(value))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("Specialty is saved"));
	}

//	*** Data Validation for specialty ****************
	@Test
	public void testEmptySpecialtyNameAddSpecialty() throws Exception {
		Specialty specialty = new Specialty("");
		when(specialtyRepo.save(specialty)).thenReturn(specialty);
		ObjectMapper om = new ObjectMapper();
		String value = om.writeValueAsString(specialty);
		mvc.perform(post("/doctor/specialty/save").contentType(MediaType.APPLICATION_JSON).content(value))
				.andExpect(status().isBadRequest()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.specialtyName").value("Specialty is required"));
	}

//	 Update specialty (Positive)
	@Test
	public void testUpdateSpecialty() throws Exception {
		int id = 1;
		Specialty specialty = new Specialty(1, "Dentist");
		Optional<Specialty> specialtyOp = Optional.of(specialty);
		when(specialtyRepo.findById(1)).thenReturn(specialtyOp);
		ObjectMapper om = new ObjectMapper();
		String value = om.writeValueAsString(specialty);
		mvc.perform(put("/doctor/specialty/update/" + id).contentType(MediaType.APPLICATION_JSON).content(value))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("Specialty is updated"));

	}

//	 Update specialty (Negative)
	@Test
	public void negativeTestUpdateSpecialty() throws Exception {
		int id = 2;
		Specialty specialty = new Specialty(1, "Dentist");
		Optional<Specialty> specialtyOp = Optional.of(specialty);
		when(specialtyRepo.findById(1)).thenReturn(specialtyOp);
		ObjectMapper om = new ObjectMapper();
		String value = om.writeValueAsString(specialty);
		mvc.perform(put("/doctor/specialty/update/" + id).contentType(MediaType.APPLICATION_JSON).content(value))
				.andExpect(status().isBadRequest()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("Not found"));

	}

//	 Add a doctor to a specialty (Positive)
	@Test
	public void testAddDoctorToSpecialty() throws Exception {
		int id = 1;
		List<Doctor> doctors = new ArrayList<Doctor>();
		doctors.add(new Doctor(1, "Jyoti", "7978208016"));
		Specialty specialty = new Specialty(1, "Dentist", doctors);
		Optional<Specialty> specialtyOp = Optional.of(specialty);
		when(specialtyRepo.findById(1)).thenReturn(specialtyOp);

		Doctor doctor = new Doctor("Ram", "7978208015");
		specialtyOp.get().getDoctors().add(doctor);
		ObjectMapper om = new ObjectMapper();
		String value = om.writeValueAsString(doctor);
		mvc.perform(post("/doctor/specialty/update/" + id + "/add/doctor").contentType(MediaType.APPLICATION_JSON)
				.content(value)).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("Doctor is added to specialty " + specialty.getSpecialtyName()));
	}

//	 Add a doctor to a specialty (Negative)
	@Test
	public void negativeTestAddDoctorToSpecialty() throws Exception {
		int id = 2;
		List<Doctor> doctors = new ArrayList<Doctor>();
		doctors.add(new Doctor(1, "Jyoti", "7978208016"));
		Specialty specialty = new Specialty(1, "Dentist", doctors);
		Optional<Specialty> specialtyOp = Optional.of(specialty);
		when(specialtyRepo.findById(1)).thenReturn(specialtyOp);

		Doctor doctor = new Doctor("Ram", "7978208015");
		specialtyOp.get().getDoctors().add(doctor);
		ObjectMapper om = new ObjectMapper();
		String value = om.writeValueAsString(doctor);
		mvc.perform(post("/doctor/specialty/update/" + id + "/add/doctor").contentType(MediaType.APPLICATION_JSON)
				.content(value)).andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("Not found"));
	}

//	*** Data Validation for Doctor ****************
	@Test
	public void testEmptyDoctorNameAddDoctorToSpecialty() throws Exception {
		int id = 1;
		List<Doctor> doctors = new ArrayList<Doctor>();
		doctors.add(new Doctor(1, "Jyoti", "7978208016"));
		Specialty specialty = new Specialty(1, "Dentist", doctors);
		Optional<Specialty> specialtyOp = Optional.of(specialty);
		when(specialtyRepo.findById(1)).thenReturn(specialtyOp);

		Doctor doctor = new Doctor("", "7978208015");
		specialtyOp.get().getDoctors().add(doctor);
		ObjectMapper om = new ObjectMapper();
		String value = om.writeValueAsString(doctor);
		mvc.perform(post("/doctor/specialty/update/" + id + "/add/doctor").contentType(MediaType.APPLICATION_JSON)
				.content(value)).andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.doctorName").value("Doctor name is required"));
	}

	@Test
	public void testWrongTelephoneAddDoctorToSpecialty() throws Exception {
		int id = 2;
		List<Doctor> doctors = new ArrayList<Doctor>();
		doctors.add(new Doctor(1, "Jyoti", "797820801a"));
		Specialty specialty = new Specialty(1, "Dentist", doctors);
		Optional<Specialty> specialtyOp = Optional.of(specialty);
		when(specialtyRepo.findById(1)).thenReturn(specialtyOp);

		Doctor doctor = new Doctor("Ram", "797820801a");
		specialtyOp.get().getDoctors().add(doctor);
		ObjectMapper om = new ObjectMapper();
		String value = om.writeValueAsString(doctor);
		mvc.perform(post("/doctor/specialty/update/" + id + "/add/doctor").contentType(MediaType.APPLICATION_JSON)
				.content(value)).andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.telephone").value("Enter 10 digit number"));
	}
//	 Remove a doctor from a specialty (Positive)
	@Test
	public void testDeleteDoctorFromSpecialty() throws Exception {
		int specialtyId = 1;
		int doctorId = 1;
		Doctor doctor = new Doctor(1, "Jyoti", "797820801a",new Address("Baripada","757081"));
		List<Doctor> doctors = new ArrayList<Doctor>();
		doctors.add(doctor);
		Specialty specialty = new Specialty(1, "Dentist", doctors);
		Optional<Specialty> specialtyOp = Optional.of(specialty);
		Optional<Doctor> doctorOp = Optional.of(doctor);
		when(specialtyRepo.findById(1)).thenReturn(specialtyOp);
		when(doctorRepo.findById(1)).thenReturn(doctorOp);
		mvc.perform(delete("/doctor/specialty/update/"+specialtyId+"/delete/doctor/"+doctorId)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("Doctor is deleted from specialty"));
	}
//	 Remove a doctor from a specialty (Negative)
	@Test
	public void negativeTestDeleteDoctorFromSpecialty() throws Exception {
		int specialtyId = 2;
		int doctorId = 1;
		Doctor doctor = new Doctor(1, "Jyoti", "797820801a",new Address("Baripada","757081"));
		List<Doctor> doctors = new ArrayList<Doctor>();
		doctors.add(doctor);
		Specialty specialty = new Specialty(1, "Dentist", doctors);
		Optional<Specialty> specialtyOp = Optional.of(specialty);
		Optional<Doctor> doctorOp = Optional.of(doctor);
		when(specialtyRepo.findById(1)).thenReturn(specialtyOp);
		when(doctorRepo.findById(1)).thenReturn(doctorOp);
		mvc.perform(delete("/doctor/specialty/update/"+specialtyId+"/delete/doctor/"+doctorId)).andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("Not found"));
	}
//	 Update doctor details (Positive)
	@Test
	@Disabled
	public void testUpdateDoctorOfSpecialty() throws Exception {
		int specialtyId = 1;
		int doctorId = 1;
		Doctor doctor = new Doctor(1, "Jyoti", "797820801a",new Address("Baripada","757081"));
		List<Doctor> doctors = new ArrayList<Doctor>();
		doctors.add(doctor);
		Specialty specialty = new Specialty(1, "Dentist", doctors);
		Optional<Specialty> specialtyOp = Optional.of(specialty);
		Optional<Doctor> doctorOp = Optional.of(doctor);
		when(specialtyRepo.findById(1)).thenReturn(specialtyOp);
		when(doctorRepo.findById(1)).thenReturn(doctorOp);
		mvc.perform(put("/doctor/specialty/update/"+specialtyId+"/update/doctor/"+doctorId)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("Doctor is updated"));
	}
//	 Update doctor details (Negative)
	@Test
	@Disabled
	public void negativeTestUpdateDoctorOfSpecialty() throws Exception {
		int specialtyId = 1;
		int doctorId = 2;
		Doctor doctor = new Doctor(1, "Jyoti", "797820801a",new Address("Baripada","757081"));
		List<Doctor> doctors = new ArrayList<Doctor>();
		doctors.add(doctor);
		Specialty specialty = new Specialty(1, "Dentist", doctors);
		Optional<Specialty> specialtyOp = Optional.of(specialty);
		Optional<Doctor> doctorOp = Optional.of(doctor);
		when(specialtyRepo.findById(1)).thenReturn(specialtyOp);
		when(doctorRepo.findById(1)).thenReturn(doctorOp);
		mvc.perform(put("/doctor/specialty/update/"+specialtyId+"/update/doctor/"+doctorId)).andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("Not found"));
	}
//	 List all the doctors in a particular specialty
	@Test
	public void testGetAllDoctorsBySpecialty() throws Exception {
		String specialtyName = "Dentist";
		Doctor doctor = new Doctor(1, "Jyoti", "797820801a",new Address("Baripada","757081"));
		List<Doctor> doctors = new ArrayList<Doctor>();
		doctors.add(doctor);
		Specialty specialty = new Specialty(1, "Dentist", doctors);
		Optional<Specialty> specialtyOp = Optional.of(specialty);
		when(specialtyRepo.findBySpecialtyName(specialtyName)).thenReturn(specialtyOp);
		mvc.perform(get("/doctor/getBySpecialty/" + specialtyName).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andDo(print())
		.andExpect(jsonPath("$[0].name").value("Jyoti"));
	}
//	 List all doctors data
	@Test
	public void testGetAllDoctors() throws Exception {
		Doctor doctor = new Doctor(1, "Jyoti", "797820801a",new Address("Baripada","757081"));
		List<Doctor> doctors = new ArrayList<Doctor>();
		doctors.add(doctor);
		when(doctorRepo.findAll()).thenReturn(doctors);
		mvc.perform(get("/doctor/get").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andDo(print())
		.andExpect(jsonPath("$[0].name").value("Jyoti"));
		
	}
	
//	 List All specialty
	@Test
	public void testGetAllSpecialty() throws Exception {
		Doctor doctor = new Doctor(1, "Jyoti", "797820801a",new Address("Baripada","757081"));
		List<Doctor> doctors = new ArrayList<Doctor>();
		doctors.add(doctor);
		Specialty specialty = new Specialty(1, "Dentist", doctors);
		List<Specialty> specialties = new ArrayList<Specialty>();
		specialties.add(specialty);
		when(specialtyRepo.findAll()).thenReturn(specialties);
		mvc.perform(get("/doctor/specialty/get").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andDo(print())
		.andExpect(jsonPath("$[0].specialtyName").value("Dentist"));
	}
	
//	 List all the appointments for a doctor in a particular date

}
