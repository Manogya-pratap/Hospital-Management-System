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
import com.vgnit.medical.entity.PatientReport;

import jakarta.servlet.http.HttpServletResponse;

public class PatientWisePdfExporter 
{
List<PatientReport> listPatientWise;
	
	public PatientWisePdfExporter(List<PatientReport> listPatientWise)
	{
		this.listPatientWise = listPatientWise;
	}
	
	private void writeTableHeader(PdfPTable table)
	{
		PdfPCell cell= new PdfPCell();
		
		cell.setBackgroundColor(Color.cyan);
		cell.setPadding(5);
		Font font= FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.BLACK);
		
		
		cell.setPhrase(new Phrase("PATIENT NAME", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("ASSIGN DATE", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("MOBILE", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("DESCRIPTIONS", font));
		table.addCell(cell);
		
	}
	
	private void writeTableData(PdfPTable table)
	{
		for (PatientReport PATIENTS : listPatientWise)
		{
			table.addCell(PATIENTS.getPatientName());
			table.addCell(PATIENTS.getAssignDate());
			table.addCell(String.valueOf(PATIENTS.getMobile()));	// for long, integer, float type data!!
			table.addCell(PATIENTS.getDescription());
		}
	}
	
	public void export(HttpServletResponse response) throws DocumentException, IOException
	{
		Document document= new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());
		document.open();
		document.add(new Paragraph("LIST OF PATIENTS WISE REPORT"));
		PdfPTable table= new PdfPTable(4);
		table.setWidthPercentage(100);
		table.setSpacingBefore(15);
		
		writeTableHeader(table);
		writeTableData(table);
		document.add(table);
		document.close();
	}
}
