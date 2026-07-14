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
import com.vgnit.medical.entity.Payment;

import jakarta.servlet.http.HttpServletResponse;

public class PaymentPdfExporter {

    private List<Payment> listPayments;

    public PaymentPdfExporter(List<Payment> listPayments) {
        this.listPayments = listPayments;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.RED);
        cell.setPadding(5);
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("ID", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("ACCOUNT NAME", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("PAYMENT METHOD", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("DESCRIPTION", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("STATUS", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {
        for (Payment payment : listPayments) {
            table.addCell(String.valueOf(payment.getId()));
            table.addCell(payment.getAccountName());
            table.addCell(payment.getPaymentMethod());
            table.addCell(payment.getDescription());
            table.addCell(payment.getStatus());
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        document.add(new Paragraph("LIST OF PAYMENTS"));
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setSpacingBefore(15);

        writeTableHeader(table);
        writeTableData(table);
        document.add(table);
        document.close();
    }
}