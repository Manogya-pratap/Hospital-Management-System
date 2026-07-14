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
import com.vgnit.medical.entity.AddDoctor;

import jakarta.servlet.http.HttpServletResponse;

public class DoctorPdfExporter {

    private List<AddDoctor> listDoctors;

    public DoctorPdfExporter(List<AddDoctor> listDoctors) {
        this.listDoctors = listDoctors;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.RED);
        cell.setPadding(5);
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("FIRST NAME", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("LAST NAME", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("GENDER", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("MOBILE", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("EMAIL", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("DEPARTMENT", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("DESIGNATION", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("SPECIALIST", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("BLOOD GROUP", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("STATUS", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {
        for (AddDoctor row : listDoctors) {
            table.addCell(row.getFirstName());
            table.addCell(row.getLastName());
            table.addCell(row.getGender());
            table.addCell(row.getMobile());
            table.addCell(row.getEmail());
            table.addCell(row.getDepartment());
            table.addCell(row.getDesignation());
            table.addCell(row.getSpecialist());
            table.addCell(row.getBloodGroup());
            table.addCell(row.getStatus());
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        document.add(new Paragraph("LIST OF DOCTORS"));
        PdfPTable table = new PdfPTable(10);
        table.setWidthPercentage(100);
        table.setSpacingBefore(15);

        writeTableHeader(table);
        writeTableData(table);
        document.add(table);
        document.close();
    }
}