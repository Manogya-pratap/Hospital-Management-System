package com.vgnit.medical.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name =  "Add_Notice")
public class Notice 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String title;
	private String bedDesc;
	private String startDate;
	private String endDate;
	
	public Notice() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Notice(Long id, String title, String bedDesc, String startDate, String endDate) {
		super();
		this.id = id;
		this.title = title;
		this.bedDesc = bedDesc;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBedDesc() {
		return bedDesc;
	}
	public void setBedDesc(String bedDesc) {
		this.bedDesc = bedDesc;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	
}
