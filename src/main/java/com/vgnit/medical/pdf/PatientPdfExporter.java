package com.vgnit.medical.pdf;

import java.awt.Color;
import java.io.IOException;
import java.util.List;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.vgnit.medical.entity.Patient;

import jakarta.servlet.http.HttpServletResponse;

public class PatientPdfExporter 
{
	private List<Patient> listPatients;

	public PatientPdfExporter(List<Patient> listPatients)
	{
		this.listPatients = listPatients;
	}
	
	private void writeTableHeader(PdfPTable table)
	{
		PdfPCell cell= new PdfPCell();
		
		cell.setBackgroundColor(Color.RED);
		cell.setPadding(5);
		Font font= FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.WHITE);
		
		
		cell.setPhrase(new Phrase("FIRST NAME", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("LAST NAME", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("EMAIL", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("MOBILE", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("DATE OF BIRTH", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("BLOOD GROUP", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("GENDER", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("ADDRESS", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("STATUS", font));
		table.addCell(cell);
	}
	
	private void writeTableData(PdfPTable table)
	{
		for (Patient PATIENTS : listPatients)
		{
			table.addCell(PATIENTS.getFirstName());
			table.addCell(PATIENTS.getLastName());
			table.addCell(PATIENTS.getEmail());
			table.addCell(String.valueOf(PATIENTS.getMobile()));	// for long, integer, float type data!!
			table.addCell(PATIENTS.getDateOfBirth());
			table.addCell(PATIENTS.getBloodGroup()); 
			table.addCell(PATIENTS.getGender());
			table.addCell(PATIENTS.getAddress());
			table.addCell(PATIENTS.getStatus());
		}
	}
	
	public void export(HttpServletResponse response) throws DocumentException, IOException
	{
		Document document= new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());
		document.open();
		document.add(new Paragraph("LIST OF PATIENTS"));
		PdfPTable table= new PdfPTable(9);
		table.setWidthPercentage(100);
		table.setSpacingBefore(15);
		
		writeTableHeader(table);
		writeTableData(table);
		document.add(table);
		document.close();
	}
	
}
