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
import com.vgnit.medical.entity.Schedule;

import jakarta.servlet.http.HttpServletResponse;

public class SchedulePdfExporter {

    private List<Schedule> listSchedules;

    public SchedulePdfExporter(List<Schedule> listSchedules) {
        this.listSchedules = listSchedules;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.RED);
        cell.setPadding(5);
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("ID", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("DOCTOR NAME", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("DAYS", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("TIME", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("PER TIME", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("VISIBILITY", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("STATUS", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {
        for (Schedule row : listSchedules) {
            table.addCell(String.valueOf(row.getId()));
            table.addCell(row.getDname());
            table.addCell(row.getDays());
            table.addCell(row.getTime());
            table.addCell(row.getPerTime());
            table.addCell(row.getVisibility());
            table.addCell(row.getStatus());
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        document.add(new Paragraph("LIST OF SCHEDULES"));
        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100);
        table.setSpacingBefore(15);

        writeTableHeader(table);
        writeTableData(table);
        document.add(table);
        document.close();
    }
}