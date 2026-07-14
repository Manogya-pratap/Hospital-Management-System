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

import com.vgnit.medical.entity.Payment;
import com.vgnit.medical.repository.PaymentRepository;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public long count() {
        return paymentRepository.count();
    }

    public Payment addPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public List<Payment> getAllPayment(String keyword) {
        if (keyword != null) {
            return paymentRepository.findthis(keyword);
        }
        return paymentRepository.findAll();
    }

    public List<Payment> getAllPayment() {
        return paymentRepository.findAll();
    }

    public Payment getPaymentById(long id) {
        Optional<Payment> optional = paymentRepository.findById(id);
        return optional.orElse(null);
    }

    public void deletePayment(long id) {
        paymentRepository.deleteById(id);
    }

    // Used by the PDF export endpoint
    public List<Payment> listAllPdf() {
        return paymentRepository.findAll();
    }

    // Excel export via Apache POI, mirroring PatientServiceImpl.generateExcel
    public void generateExcel(HttpServletResponse response) throws IOException {
        List<Payment> findAllList = paymentRepository.findAll();

        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        HSSFSheet sheet = hssfWorkbook.createSheet("Payment Report");

        HSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("ID");
        row.createCell(1).setCellValue("ACCOUNT NAME");
        row.createCell(2).setCellValue("PAYMENT METHOD");
        row.createCell(3).setCellValue("DESCRIPTION");
        row.createCell(4).setCellValue("STATUS");

        int dataRowIndex = 1;
        for (Payment payment : findAllList) {
            HSSFRow dataRow = sheet.createRow(dataRowIndex);
            dataRow.createCell(0).setCellValue(payment.getId());
            dataRow.createCell(1).setCellValue(payment.getAccountName());
            dataRow.createCell(2).setCellValue(payment.getPaymentMethod());
            dataRow.createCell(3).setCellValue(payment.getDescription());
            dataRow.createCell(4).setCellValue(payment.getStatus());
            dataRowIndex++;
        }

        hssfWorkbook.write(response.getOutputStream());
        hssfWorkbook.close();
    }

    public void removeSessionMessage() {
        HttpSession session = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes()))
                .getRequest().getSession();
        session.removeAttribute("msg");
    }
}