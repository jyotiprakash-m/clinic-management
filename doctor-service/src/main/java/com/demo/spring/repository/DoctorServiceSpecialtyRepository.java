package com.demo.spring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.spring.entity.Specialty;
@Repository
public interface DoctorServiceSpecialtyRepository extends JpaRepository<Specialty, Integer> {
	public Optional<Specialty> findBySpecialtyName(String specialtyName);
}
