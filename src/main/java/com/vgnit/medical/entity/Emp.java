package com.vgnit.medical.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name =  "Add_Employee")
public class Emp
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String typeEmp;
	private String firstName;
	private String lastName;
	private String email;
	private String mobile;
	private String gender;
	private String address;
	private String status;
	
	public Emp() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Emp(Long id, String typeEmp, String firstName, String lastName, String email, String mobile, String gender,
			String address, String status) {
		super();
		this.id = id;
		this.typeEmp = typeEmp;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.mobile = mobile;
		this.gender = gender;
		this.address = address;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTypeEmp() {
		return typeEmp;
	}

	public void setTypeEmp(String typeEmp) {
		this.typeEmp = typeEmp;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
