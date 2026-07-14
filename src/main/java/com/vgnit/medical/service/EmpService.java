package com.vgnit.medical.service;

import java.util.List;

import com.vgnit.medical.entity.Emp;

public interface EmpService 
{
	Emp saveEmp(Emp emp);

	List<Emp>getAllEmp(String keyword);

	void deleteEmp(Long id);

	Emp getParticularEmp(Long id);
}

