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
@Table(name = "visits")
public class Visit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int visitId;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm")
	@NotNull(message = "Date is required")
    private LocalDateTime date;
	@NotNull(message = "Doctor Id is required")
	private int doctorId;

	public int getVisitId() {
		return visitId;
	}

	public void setVisitId(int visitId) {
		this.visitId = visitId;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public int getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}
	public Visit() {
	}

	public Visit(int visitId, LocalDateTime date, int doctorId) {
		this.visitId = visitId;
		this.date = date;
		this.doctorId = doctorId;
	}
	
}
