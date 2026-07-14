package com.vgnit.medical.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.lowagie.text.DocumentException;
import com.vgnit.medical.entity.Payment;
import com.vgnit.medical.pdf.PaymentPdfExporter;
import com.vgnit.medical.service.PaymentService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    // Show the "Add Payment" form
    @GetMapping("/add-payment")
    public String addPaymentForm(Model model) {
        model.addAttribute("payment", new Payment());
        return "add-payment";
    }

    // Save a new payment
    @PostMapping("/savePayment")
    public String savePayment(@ModelAttribute("payment") Payment payment, HttpSession session) {
        paymentService.addPayment(payment);
        session.setAttribute("msg", "Payment added successfully!!");
        return "redirect:/admin/paymentlist";
    }

    // List all payments (with optional search)
    @GetMapping("/paymentlist")
    public String paymentList(@Param("keyword") String keyword, Model model) {
        List<Payment> list = paymentService.getAllPayment(keyword);
        model.addAttribute("paymentlist", list);
        model.addAttribute("keyword", keyword);
        return "payment-list";
    }

    // Delete
    @GetMapping("/deletePayment/{id}")
    public String deletePayment(@PathVariable(value = "id") long id, HttpSession session) {
        paymentService.deletePayment(id);
        session.setAttribute("msgd", "Payment deleted successfully!!");
        return "redirect:/admin/paymentlist";
    }

    // Show edit form
    @GetMapping("/editPayment/{id}")
    public String editPayment(@PathVariable(value = "id") long id, Model model) {
        model.addAttribute("payment", paymentService.getPaymentById(id));
        return "edit-payment";
    }

    // Update
    @PostMapping("/updatePayment")
    public String updatePayment(@ModelAttribute("payment") Payment payment, HttpSession session) {
        paymentService.addPayment(payment);
        session.setAttribute("msgu", "Payment updated successfully!!");
        return "redirect:/admin/paymentlist";
    }

    // Excel export
    @GetMapping("/PrintPaymentExcelReport")
    public void exportPaymentExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=payment.xls");
        paymentService.generateExcel(response);
    }

    // PDF export
    @GetMapping("/PrintPaymentPdfReport")
    public void exportPaymentPdf(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());
        response.setHeader("Content-Disposition", "attachment; filename=payments_" + currentDateTime + ".pdf");

        PaymentPdfExporter exporter = new PaymentPdfExporter(paymentService.listAllPdf());
        exporter.export(response);
    }
}