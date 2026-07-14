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

import com.vgnit.medical.entity.DoctorReport;
import com.vgnit.medical.entity.PatientReport;
import com.vgnit.medical.repository.DoctorReportRepository;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Service
public class DoctorRportService
{
	@Autowired
	private DoctorReportRepository doctorReportRepository;
	
	public DoctorReport addReportDocter(DoctorReport doctorReport)
	{
		return doctorReportRepository.save(doctorReport);
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
	
	public List<DoctorReport> getAllRep(String keyword)
	{
		if(keyword!=null)
		{
			return doctorReportRepository.allfull(keyword);
		}
		else
		{
			return doctorReportRepository.findAll();
		}
	}

	public void deleteRep(Long id) 
	{
		doctorReportRepository.deleteById(id);
		
	}

	public DoctorReport getParticularRep(Long id)
	{
		Optional<DoctorReport> findById = doctorReportRepository.findById(id);
		return findById.get();
	}

	public void addRep(DoctorReport doctorReport)
	{
		doctorReportRepository.save(doctorReport);
		
	}


	public List<DoctorReport> findByDateBetween(String startDate, String endDate) 
	{
		if(startDate!=null && endDate!=null)
		{
			return doctorReportRepository.findStart(startDate, endDate);
		}
		else
		{
			return doctorReportRepository.findAll();
		}
	}

	public List<DoctorReport> listAllPdf()
	{
		List<DoctorReport> findAllPdf = doctorReportRepository.findAll();
		return findAllPdf;
	}

	public void generateExcel(HttpServletResponse response) throws IOException
	{
		List<DoctorReport> findAllList = doctorReportRepository.findAll();
		
		
		/* Apache POI Components: 
		 * WorkBook
		 * Sheet
		 * Row
		 * Cell
	   */
		
		//WorkBook Creation:
		HSSFWorkbook hssfWorkbook= new HSSFWorkbook();
		
		//Sheet
		HSSFSheet sheet = hssfWorkbook.createSheet("Doctor Wise Report");
		
		//Row Creation:
		// 1. Header Row
		HSSFRow row = sheet.createRow(0);
		
		//Cell Creation:
		// 1. Cell Row
		//row.createCell(0).setCellValue("ID");
		row.createCell(0).setCellValue("DOCTOR NAME");
		row.createCell(1).setCellValue("ASSIGN DATE");
		row.createCell(2).setCellValue("MOBILE");
		row.createCell(3).setCellValue("DESCRIPTION");
		
		int dataRowIndex= 1; 
		
		for(DoctorReport doctors : findAllList)
		{
			HSSFRow dataRow = sheet.createRow(dataRowIndex);
			
			//dataRow.createCell(0).setCellValue(PATIENTS.getId());
			dataRow.createCell(0).setCellValue(doctors.getdName());
			dataRow.createCell(1).setCellValue(doctors.getDate());
			dataRow.createCell(2).setCellValue(doctors.getMobile());
			dataRow.createCell(3).setCellValue(doctors.getDescription());
			dataRowIndex ++;
		}
		
		ServletOutputStream outputStream = response.getOutputStream();
		hssfWorkbook.write(outputStream);
		hssfWorkbook.close();
		outputStream.close();
		
	}

}
