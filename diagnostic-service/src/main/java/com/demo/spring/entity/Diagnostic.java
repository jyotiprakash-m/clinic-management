package com.demo.spring.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "tests")
public class Diagnostic {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int testId;

	@NotEmpty(message = "Diagnostic name is required")
	private String name;

	public int getTestId() {
		return testId;
	}

	public void setTestId(int testId) {
		this.testId = testId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Diagnostic() {
	}

	public Diagnostic(String name) {
		super();
		this.name = name;
	}

	public Diagnostic(int testId, String name) {
		super();
		this.testId = testId;
		this.name = name;
	}

}
