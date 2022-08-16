package com.demo.spring.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "specialties")
public class Specialty {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int specialtyId;
	@NotEmpty(message = "Specialty is required")
	@Column(name = "name")
	private String specialtyName;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "specialty_id", referencedColumnName = "id")
	private List<Doctor> doctors;

	public int getSpecialtyId() {
		return specialtyId;
	}

	public void setSpecialtyId(int specialtyId) {
		this.specialtyId = specialtyId;
	}

	public String getSpecialtyName() {
		return specialtyName;
	}

	public void setSpecialtyName(String specialtyName) {
		this.specialtyName = specialtyName;
	}

	public List<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(List<Doctor> doctors) {
		this.doctors = doctors;
	}

	public Specialty() {
	}

	public Specialty(String specialtyName) {
		this.specialtyName = specialtyName;
	}
	public Specialty(int specialtyId,String specialtyName) {
		this.specialtyId = specialtyId;
		this.specialtyName = specialtyName;
	}

	public Specialty(int specialtyId, String specialtyName, List<Doctor> doctors) {
		this.specialtyId = specialtyId;
		this.specialtyName = specialtyName;
		this.doctors = doctors;
	}

}
