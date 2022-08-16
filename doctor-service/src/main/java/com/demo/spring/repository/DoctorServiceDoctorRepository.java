package com.demo.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.spring.entity.Doctor;
@Repository
public interface DoctorServiceDoctorRepository extends JpaRepository<Doctor, Integer> {

}
