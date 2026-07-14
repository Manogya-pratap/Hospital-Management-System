package com.vgnit.medical.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name =  "Add_Bed")
public class Bed
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String bedType;
	private String bedCap;
	private String bedDesc;
	private String sex;
	
	public Bed() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Bed(Long id, String bedType, String bedCap, String bedDesc, String sex) {
		super();
		this.id = id;
		this.bedType = bedType;
		this.bedCap = bedCap;
		this.bedDesc = bedDesc;
		this.sex = sex;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBedType() {
		return bedType;
	}
	public void setBedType(String bedType) {
		this.bedType = bedType;
	}
	public String getBedCap() {
		return bedCap;
	}
	public void setBedCap(String bedCap) {
		this.bedCap = bedCap;
	}
	public String getBedDesc() {
		return bedDesc;
	}
	public void setBedDesc(String bedDesc) {
		this.bedDesc = bedDesc;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}

	
}
