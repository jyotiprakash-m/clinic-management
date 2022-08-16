package com.demo.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.spring.entity.Patient;
@Repository
public interface PatientServicePatientRepository extends JpaRepository<Patient, Integer> {
	@Query("SELECT p FROM Patient p WHERE p.patientName LIKE %:name%")
	public List<Patient> searchByPatientNameLike(@Param("name") String name);
}
