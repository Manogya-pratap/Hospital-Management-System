package com.vgnit.medical.service;

import java.io.IOException;
import java.util.List;

import com.vgnit.medical.entity.Patient;

import jakarta.servlet.http.HttpServletResponse;

public interface PatientService 
{
	Patient addPatient(Patient patient);
	
	List<Patient> getAllPatient(String keyword); 
	
	void deletePatient(Long id);
	
	Patient getPatientById(Long id);

	List<Patient> pp();

	List<Patient> getAllPatient();

	List<Patient> listAllPdf();

	void generateExcel(HttpServletResponse HttpServletResponse) throws IOException;
}
