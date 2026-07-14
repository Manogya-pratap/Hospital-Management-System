package com.vgnit.medical.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Add_Shedule")
public class Schedule 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String dname;
	private String days;
	private String time;
	private String perTime;
	private String visibility;
	private String status;
	
	public Schedule() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Schedule(Long id, String dname, String days, String time, String perTime, String visibility, String status) {
		super();
		this.id = id;
		this.dname = dname;
		this.days = days;
		this.time = time;
		this.perTime = perTime;
		this.visibility = visibility;
		this.status = status;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDname() {
		return dname;
	}
	public void setDname(String dname) {
		this.dname = dname;
	}
	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getPerTime() {
		return perTime;
	}
	public void setPerTime(String perTime) {
		this.perTime = perTime;
	}
	public String getVisibility() {
		return visibility;
	}
	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
