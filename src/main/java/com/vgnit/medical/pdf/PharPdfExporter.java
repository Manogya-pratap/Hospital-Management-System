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
import com.vgnit.medical.entity.Phar;

import jakarta.servlet.http.HttpServletResponse;

public class PharPdfExporter {

    private List<Phar> listPharmacists;

    public PharPdfExporter(List<Phar> listPharmacists) {
        this.listPharmacists = listPharmacists;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.RED);
        cell.setPadding(5);
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("ID", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("FIRST NAME", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("LAST NAME", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("EMAIL", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("MOBILE", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("GENDER", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("ADDRESS", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("STATUS", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {
        for (Phar row : listPharmacists) {
            table.addCell(String.valueOf(row.getId()));
            table.addCell(row.getFirstName());
            table.addCell(row.getLastName());
            table.addCell(row.getEmail());
            table.addCell(row.getMobile());
            table.addCell(row.getGender());
            table.addCell(row.getAddress());
            table.addCell(row.getStatus());
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        document.add(new Paragraph("LIST OF PHARMACISTS"));
        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100);
        table.setSpacingBefore(15);

        writeTableHeader(table);
        writeTableData(table);
        document.add(table);
        document.close();
    }
}