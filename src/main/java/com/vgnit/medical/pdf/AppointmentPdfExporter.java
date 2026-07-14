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
import com.vgnit.medical.entity.Appointment;

import jakarta.servlet.http.HttpServletResponse;

public class AppointmentPdfExporter {

    private List<Appointment> listAppointments;

    public AppointmentPdfExporter(List<Appointment> listAppointments) {
        this.listAppointments = listAppointments;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.RED);
        cell.setPadding(5);
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("ID", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("PATIENT", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("DEPARTMENT", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("DOCTOR", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("DATE", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("PROBLEM", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {
        for (Appointment row : listAppointments) {
            table.addCell(String.valueOf(row.getId()));
            table.addCell(row.getPatient());
            table.addCell(row.getDepart());
            table.addCell(row.getDoctorn());
            table.addCell(row.getAdate());
            table.addCell(row.getProb());
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        document.add(new Paragraph("LIST OF APPOINTMENTS"));
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        table.setSpacingBefore(15);

        writeTableHeader(table);
        writeTableData(table);
        document.add(table);
        document.close();
    }
}