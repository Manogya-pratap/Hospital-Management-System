package com.vgnit.medical.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.vgnit.medical.entity.Patient;
import com.vgnit.medical.repository.PatientRepository;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Service
public class PatientServiceImpl implements PatientService 
{
	@Override
	public List<Patient> pp() 
	{
		return patientRepository.fp();
	}

	@Autowired
	private PatientRepository patientRepository;

	@Override
	public Patient addPatient(Patient patient)
	{
		Patient savePatient = patientRepository.save(patient);
		return savePatient;
	}

	// (*)
	public void removeSessionMessage()
	{
		HttpSession session = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest().getSession();
		session.removeAttribute("msg"); 
	}
	
	// (*)
	public void removeSessionMessage2()
	{
		HttpSession session = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest().getSession();
		
		session.removeAttribute("msgd"); 
	}
	
	// (*)
	public void removeSessionMessage3()
	{
		HttpSession session = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest().getSession();
		
		session.removeAttribute("msgu"); 
	}
	
	@Override
	public List<Patient> getAllPatient(String keyword)
	{
		if(keyword!=null)
		{
			return patientRepository.search(keyword);
		}
		else
		{
			return patientRepository.findAll();
		}
	}

	@Override
	public void deletePatient(Long id)
	{
		patientRepository.deleteById(id);
		
	}

	@Override
	public Patient getPatientById(Long id) 
	{
		Optional<Patient> findById = patientRepository.findById(id);
		Patient patient = findById.get();
		return patient;
	}

	@Override
	public List<Patient> getAllPatient() 
	{
		
		return patientRepository.findAll();
	}

	@Override
	public void generateExcel(HttpServletResponse HttpServletResponse) throws IOException
	{
		List<Patient> findAllList = patientRepository.findAll();
		
		
		/* Apache POI Components: 
		 * WorkBook
		 * Sheet
		 * Row
		 * Cell
	   */
		
		//WorkBook Creation:
		HSSFWorkbook hssfWorkbook= new HSSFWorkbook();
		
		//Sheet
		HSSFSheet sheet = hssfWorkbook.createSheet("Patient Report");
		
		//Row Creation:
		// 1. Header Row
		HSSFRow row = sheet.createRow(0);
		
		//Cell Creation:
		// 1. Cell Row
		//row.createCell(0).setCellValue("ID");
		row.createCell(0).setCellValue("FIRST NAME");
		row.createCell(1).setCellValue("LAST NAME");
		row.createCell(2).setCellValue("EMAIL");
		row.createCell(3).setCellValue("MOBILE");
		row.createCell(4).setCellValue("DATE OF BIRTH");
		row.createCell(5).setCellValue("BLOOD GROUP");
		row.createCell(6).setCellValue("GENDER");
		row.createCell(7).setCellValue("ADDRESS");
		row.createCell(8).setCellValue("STATUS");
		
		int dataRowIndex= 1; 
		
		for(Patient PATIENTS : findAllList)
		{
			HSSFRow dataRow = sheet.createRow(dataRowIndex);
			
			//dataRow.createCell(0).setCellValue(PATIENTS.getId());
			dataRow.createCell(0).setCellValue(PATIENTS.getFirstName());
			dataRow.createCell(1).setCellValue(PATIENTS.getLastName());
			dataRow.createCell(2).setCellValue(PATIENTS.getEmail());
			dataRow.createCell(3).setCellValue(PATIENTS.getMobile());
			dataRow.createCell(4).setCellValue(PATIENTS.getDateOfBirth());
			dataRow.createCell(5).setCellValue(PATIENTS.getBloodGroup());
			dataRow.createCell(6).setCellValue(PATIENTS.getGender());
			dataRow.createCell(7).setCellValue(PATIENTS.getAddress());
			dataRow.createCell(8).setCellValue(PATIENTS.getStatus());
			
			dataRowIndex ++;
		}
		
		ServletOutputStream outputStream = HttpServletResponse.getOutputStream();
		hssfWorkbook.write(outputStream);
		hssfWorkbook.close();
		outputStream.close();
		
	}
	
	@Override
	public List<Patient> listAllPdf()
	{
		List<Patient> findAllPdf = patientRepository.findAll();
		return findAllPdf;
	}

}
