package com.demo.spring.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "doctors")
public class Doctor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int doctorId;
	@NotEmpty(message = "Doctor name is required")
	@Column(name = "name")
	private String doctorName;
	@Pattern(regexp = "^\\d{10}$", message = "Enter 10 digit number")
	private String telephone;
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id")
	private Address address;

	public int getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}

	public String getName() {
		return doctorName;
	}

	public void setName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Doctor() {
	}

	public Doctor(String doctorName, String telephone) {
		this.doctorName = doctorName;
		this.telephone = telephone;
	}
	public Doctor(int doctorId,String doctorName, String telephone) {
		this.doctorId = doctorId;
		this.doctorName = doctorName;
		this.telephone = telephone;
		
	}

	public Doctor(int doctorId, String doctorName, String telephone, Address address) {
		this.doctorId = doctorId;
		this.doctorName = doctorName;
		this.telephone = telephone;
		this.address = address;
	}

}
