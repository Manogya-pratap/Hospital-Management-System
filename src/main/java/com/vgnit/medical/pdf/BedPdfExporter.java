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
import com.vgnit.medical.entity.Bed;

import jakarta.servlet.http.HttpServletResponse;

public class BedPdfExporter {

    private List<Bed> listBeds;

    public BedPdfExporter(List<Bed> listBeds) {
        this.listBeds = listBeds;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.RED);
        cell.setPadding(5);
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("ID", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("BED TYPE", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("CAPACITY", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("DESCRIPTION", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("SEX", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {
        for (Bed row : listBeds) {
            table.addCell(String.valueOf(row.getId()));
            table.addCell(row.getBedType());
            table.addCell(row.getBedCap());
            table.addCell(row.getBedDesc());
            table.addCell(row.getSex());
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        document.add(new Paragraph("LIST OF BEDS"));
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setSpacingBefore(15);

        writeTableHeader(table);
        writeTableData(table);
        document.add(table);
        document.close();
    }
}