package com.vgnit.medical.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lowagie.text.DocumentException;
import com.vgnit.medical.pdf.AppointmentPdfExporter;
import com.vgnit.medical.pdf.BedPdfExporter;
import com.vgnit.medical.pdf.DepartmentPdfExporter;
import com.vgnit.medical.pdf.DoctorPdfExporter;
import com.vgnit.medical.pdf.EmpPdfExporter;
import com.vgnit.medical.pdf.NoticePdfExporter;
import com.vgnit.medical.pdf.NurPdfExporter;
import com.vgnit.medical.pdf.PharPdfExporter;
import com.vgnit.medical.pdf.SchedulePdfExporter;
import com.vgnit.medical.service.ExportService;

import jakarta.servlet.http.HttpServletResponse;

/**
 * All Excel/PDF download endpoints for the list sections that previously had
 * dead export buttons. Lives under /admin so existing security rules apply.
 */
@Controller
@RequestMapping("/admin")
public class ExportController {

    @Autowired
    private ExportService exportService;

    private void excelHeaders(HttpServletResponse response, String file) {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + file + ".xls");
    }

    private void pdfHeaders(HttpServletResponse response, String file) {
        response.setContentType("application/pdf");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        response.setHeader("Content-Disposition",
                "attachment; filename=" + file + "_" + df.format(new Date()) + ".pdf");
    }

    // ---------- Doctor ----------
    @GetMapping("/PrintDoctorExcelReport")
    public void doctorExcel(HttpServletResponse response) throws IOException {
        excelHeaders(response, "doctors");
        exportService.doctorExcel(response);
    }

    @GetMapping("/PrintDoctorPdfReport")
    public void doctorPdf(HttpServletResponse response) throws DocumentException, IOException {
        pdfHeaders(response, "doctors");
        new DoctorPdfExporter(exportService.allDoctors()).export(response);
    }

    // ---------- Department ----------
    @GetMapping("/PrintDepartmentExcelReport")
    public void departmentExcel(HttpServletResponse response) throws IOException {
        excelHeaders(response, "departments");
        exportService.departmentExcel(response);
    }

    @GetMapping("/PrintDepartmentPdfReport")
    public void departmentPdf(HttpServletResponse response) throws DocumentException, IOException {
        pdfHeaders(response, "departments");
        new DepartmentPdfExporter(exportService.allDepartments()).export(response);
    }

    // ---------- Schedule ----------
    @GetMapping("/PrintScheduleExcelReport")
    public void scheduleExcel(HttpServletResponse response) throws IOException {
        excelHeaders(response, "schedules");
        exportService.scheduleExcel(response);
    }

    @GetMapping("/PrintSchedulePdfReport")
    public void schedulePdf(HttpServletResponse response) throws DocumentException, IOException {
        pdfHeaders(response, "schedules");
        new SchedulePdfExporter(exportService.allSchedules()).export(response);
    }

    // ---------- Employee ----------
    @GetMapping("/PrintEmployeeExcelReport")
    public void empExcel(HttpServletResponse response) throws IOException {
        excelHeaders(response, "employees");
        exportService.empExcel(response);
    }

    @GetMapping("/PrintEmployeePdfReport")
    public void empPdf(HttpServletResponse response) throws DocumentException, IOException {
        pdfHeaders(response, "employees");
        new EmpPdfExporter(exportService.allEmps()).export(response);
    }

    // ---------- Nurse ----------
    @GetMapping("/PrintNurseExcelReport")
    public void nurseExcel(HttpServletResponse response) throws IOException {
        excelHeaders(response, "nurses");
        exportService.nurseExcel(response);
    }

    @GetMapping("/PrintNursePdfReport")
    public void nursePdf(HttpServletResponse response) throws DocumentException, IOException {
        pdfHeaders(response, "nurses");
        new NurPdfExporter(exportService.allNurses()).export(response);
    }

    // ---------- Bed ----------
    @GetMapping("/PrintBedExcelReport")
    public void bedExcel(HttpServletResponse response) throws IOException {
        excelHeaders(response, "beds");
        exportService.bedExcel(response);
    }

    @GetMapping("/PrintBedPdfReport")
    public void bedPdf(HttpServletResponse response) throws DocumentException, IOException {
        pdfHeaders(response, "beds");
        new BedPdfExporter(exportService.allBeds()).export(response);
    }

    // ---------- Notice ----------
    @GetMapping("/PrintNoticeExcelReport")
    public void noticeExcel(HttpServletResponse response) throws IOException {
        excelHeaders(response, "notices");
        exportService.noticeExcel(response);
    }

    @GetMapping("/PrintNoticePdfReport")
    public void noticePdf(HttpServletResponse response) throws DocumentException, IOException {
        pdfHeaders(response, "notices");
        new NoticePdfExporter(exportService.allNotices()).export(response);
    }

    // ---------- Pharmacist ----------
    @GetMapping("/PrintPharmacistExcelReport")
    public void pharExcel(HttpServletResponse response) throws IOException {
        excelHeaders(response, "pharmacists");
        exportService.pharExcel(response);
    }

    @GetMapping("/PrintPharmacistPdfReport")
    public void pharPdf(HttpServletResponse response) throws DocumentException, IOException {
        pdfHeaders(response, "pharmacists");
        new PharPdfExporter(exportService.allPharmacists()).export(response);
    }

    // ---------- Appointment ----------
    @GetMapping("/PrintAppointmentExcelReport")
    public void appointmentExcel(HttpServletResponse response) throws IOException {
        excelHeaders(response, "appointments");
        exportService.appointmentExcel(response);
    }

    @GetMapping("/PrintAppointmentPdfReport")
    public void appointmentPdf(HttpServletResponse response) throws DocumentException, IOException {
        pdfHeaders(response, "appointments");
        new AppointmentPdfExporter(exportService.allAppointments()).export(response);
    }
}