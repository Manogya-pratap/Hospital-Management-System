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

import com.vgnit.medical.entity.PatientReport;
import com.vgnit.medical.repository.PatientReportRepository;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Service
public class PatientRportService 
{
	@Autowired
	private PatientReportRepository patientReportRepository;

	public PatientReport addReportPatient(PatientReport patientReport )
	{
		return patientReportRepository.save(patientReport);
		
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
	
	public List<PatientReport> getAllRep(String keyword)
	{
		if(keyword!=null)
		{
			return patientReportRepository.getThisss(keyword);
		}
		else
		{
			return patientReportRepository.findAll();
		}
	}

	public PatientReport getParticularRep(Long id) 
	{
		Optional<PatientReport> findById = patientReportRepository.findById(id);
		return findById.get();
	}

	public void deleteRep(Long id)
	{
		patientReportRepository.deleteById(id);
	}

	public PatientReport addRep(PatientReport patientReport)
	{
		return patientReportRepository.save(patientReport);
	}

	public List<PatientReport> listAllPdf()
	{
		List<PatientReport> findAllPdf = patientReportRepository.findAll();
		return findAllPdf;
	}

	public void generateExcel(HttpServletResponse response) throws IOException
	{
		List<PatientReport> findAllList = patientReportRepository.findAll();
		
		
		/* Apache POI Components: 
		 * WorkBook
		 * Sheet
		 * Row
		 * Cell
	   */
		
		//WorkBook Creation:
		HSSFWorkbook hssfWorkbook= new HSSFWorkbook();
		
		//Sheet
		HSSFSheet sheet = hssfWorkbook.createSheet("Patient Wise Report");
		
		//Row Creation:
		// 1. Header Row
		HSSFRow row = sheet.createRow(0);
		
		//Cell Creation:
		// 1. Cell Row
		//row.createCell(0).setCellValue("ID");
		row.createCell(0).setCellValue("PATIENT NAME");
		row.createCell(1).setCellValue("ASSIGN DATE");
		row.createCell(2).setCellValue("MOBILE");
		row.createCell(3).setCellValue("DESCRIPTION");
		
		int dataRowIndex= 1; 
		
		for(PatientReport PATIENTS : findAllList)
		{
			HSSFRow dataRow = sheet.createRow(dataRowIndex);
			
			//dataRow.createCell(0).setCellValue(PATIENTS.getId());
			dataRow.createCell(0).setCellValue(PATIENTS.getPatientName());
			dataRow.createCell(1).setCellValue(PATIENTS.getAssignDate());
			dataRow.createCell(2).setCellValue(PATIENTS.getMobile());
			dataRow.createCell(3).setCellValue(PATIENTS.getDescription());
			dataRowIndex ++;
		}
		
		ServletOutputStream outputStream = response.getOutputStream();
		hssfWorkbook.write(outputStream);
		hssfWorkbook.close();
		outputStream.close();
		
	}

}
