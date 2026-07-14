package com.vgnit.medical.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Add_Appointment")
public class Appointment 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String patient;
	private String depart;
	private String doctorn;
	private String adate;
	private String prob;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPatient() {
		return patient;
	}
	public void setPatient(String patient) {
		this.patient = patient;
	}
	public String getDepart() {
		return depart;
	}
	public void setDepart(String depart) {
		this.depart = depart;
	}
	public String getDoctorn() {
		return doctorn;
	}
	public void setDoctorn(String doctorn) {
		this.doctorn = doctorn;
	}
	public String getAdate() {
		return adate;
	}
	public void setAdate(String adate) {
		this.adate = adate;
	}
	public String getProb() {
		return prob;
	}
	public void setProb(String prob) {
		this.prob = prob;
	}
	public Appointment() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Appointment(Long id, String patient, String depart, String doctorn, String adate, String prob) {
		super();
		this.id = id;
		this.patient = patient;
		this.depart = depart;
		this.doctorn = doctorn;
		this.adate = adate;
		this.prob = prob;
	}
}
