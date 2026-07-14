package com.vgnit.medical.service;

import java.util.List;

import com.vgnit.medical.entity.AddDoctor;

public interface DoctorService
{
	boolean checkEmail(String email);

	AddDoctor addDoctor(AddDoctor addDoctor);

	List<AddDoctor> listAll(String keyword);

	void deletDoctor(Long id);

	AddDoctor getDoctorById(Long id);

	List<AddDoctor> findst();
}
