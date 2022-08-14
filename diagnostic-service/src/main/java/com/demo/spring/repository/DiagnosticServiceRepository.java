package com.demo.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.spring.entity.Diagnostic;
@Repository
public interface DiagnosticServiceRepository extends JpaRepository<Diagnostic, Integer> {

}
