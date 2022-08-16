package com.demo.spring.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "diagnostics")
public class Diagnostic {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int diagnosticId;

	@NotEmpty(message = "Diagnostic name is required")
	@Column(name = "name")
	private String diagnosticName;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm")
	@NotNull(message = "Date is required")
    private LocalDateTime date;

	

	public int getDiagnosticId() {
		return diagnosticId;
	}

	public void setDiagnosticId(int diagnosticId) {
		this.diagnosticId = diagnosticId;
	}

	public String getDiagnosticName() {
		return diagnosticName;
	}

	public void setDiagnosticName(String diagnosticName) {
		this.diagnosticName = diagnosticName;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public Diagnostic() {
	}

	public Diagnostic(int diagnosticId, String diagnosticName, LocalDateTime date) {
		this.diagnosticId = diagnosticId;
		this.diagnosticName = diagnosticName;
		this.date = date;
	}
	
}
