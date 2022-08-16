package com.demo.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.spring.entity.Visit;
@Repository
public interface PatientServiceVisitRepository extends JpaRepository<Visit, Integer> {

}
