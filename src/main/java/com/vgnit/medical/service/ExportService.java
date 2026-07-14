package com.vgnit.medical.service;

import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vgnit.medical.entity.AddDoctor;
import com.vgnit.medical.entity.Appointment;
import com.vgnit.medical.entity.Bed;
import com.vgnit.medical.entity.Department;
import com.vgnit.medical.entity.Emp;
import com.vgnit.medical.entity.Notice;
import com.vgnit.medical.entity.Nur;
import com.vgnit.medical.entity.Phar;
import com.vgnit.medical.entity.Schedule;
import com.vgnit.medical.repository.AppointmentRepository;
import com.vgnit.medical.repository.BedRepository;
import com.vgnit.medical.repository.DepartmentRepository;
import com.vgnit.medical.repository.DoctorRepository;
import com.vgnit.medical.repository.EmpRepository;
import com.vgnit.medical.repository.NoticeRepository;
import com.vgnit.medical.repository.NurseRepository;
import com.vgnit.medical.repository.PharmaRepository;
import com.vgnit.medical.repository.ScheduleRepository;

import jakarta.servlet.http.HttpServletResponse;

/**
 * One service that produces Excel (.xls) reports for every list section.
 * Each method reads directly from that entity's repository and writes an
 * Apache POI workbook to the servlet response.
 */
@Service
public class ExportService {

    @Autowired private DoctorRepository doctorRepository;
    @Autowired private DepartmentRepository departmentRepository;
    @Autowired private ScheduleRepository scheduleRepository;
    @Autowired private EmpRepository empRepository;
    @Autowired private NurseRepository nurseRepository;
    @Autowired private BedRepository bedRepository;
    @Autowired private NoticeRepository noticeRepository;
    @Autowired private PharmaRepository pharmaRepository;
    @Autowired private AppointmentRepository appointmentRepository;

    // ---- small helpers -------------------------------------------------

    private HSSFRow header(HSSFSheet sheet, String... titles) {
        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i < titles.length; i++) {
            row.createCell(i).setCellValue(titles[i]);
        }
        return row;
    }

    private void write(HSSFWorkbook wb, HttpServletResponse response) throws IOException {
        wb.write(response.getOutputStream());
        wb.close();
    }

    // ---- Doctor --------------------------------------------------------

    public void doctorExcel(HttpServletResponse response) throws IOException {
        List<AddDoctor> list = doctorRepository.findAll();
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("Doctor Report");
        header(sheet, "FIRST NAME", "LAST NAME", "GENDER", "MOBILE", "EMAIL",
                "DEPARTMENT", "DESIGNATION", "SPECIALIST", "BLOOD GROUP", "STATUS");
        int r = 1;
        for (AddDoctor d : list) {
            HSSFRow row = sheet.createRow(r++);
            row.createCell(0).setCellValue(d.getFirstName());
            row.createCell(1).setCellValue(d.getLastName());
            row.createCell(2).setCellValue(d.getGender());
            row.createCell(3).setCellValue(d.getMobile());
            row.createCell(4).setCellValue(d.getEmail());
            row.createCell(5).setCellValue(d.getDepartment());
            row.createCell(6).setCellValue(d.getDesignation());
            row.createCell(7).setCellValue(d.getSpecialist());
            row.createCell(8).setCellValue(d.getBloodGroup());
            row.createCell(9).setCellValue(d.getStatus());
        }
        write(wb, response);
    }

    // ---- Department ----------------------------------------------------

    public void departmentExcel(HttpServletResponse response) throws IOException {
        List<Department> list = departmentRepository.findAll();
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("Department Report");
        header(sheet, "ID", "DEPARTMENT NAME", "DESCRIPTION", "STATUS");
        int r = 1;
        for (Department d : list) {
            HSSFRow row = sheet.createRow(r++);
            row.createCell(0).setCellValue(d.getId());
            row.createCell(1).setCellValue(d.getDeptName());
            row.createCell(2).setCellValue(d.getDescription());
            row.createCell(3).setCellValue(d.getStatus());
        }
        write(wb, response);
    }

    // ---- Schedule ------------------------------------------------------

    public void scheduleExcel(HttpServletResponse response) throws IOException {
        List<Schedule> list = scheduleRepository.findAll();
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("Schedule Report");
        header(sheet, "ID", "DOCTOR NAME", "DAYS", "TIME", "PER TIME", "VISIBILITY", "STATUS");
        int r = 1;
        for (Schedule s : list) {
            HSSFRow row = sheet.createRow(r++);
            row.createCell(0).setCellValue(s.getId());
            row.createCell(1).setCellValue(s.getDname());
            row.createCell(2).setCellValue(s.getDays());
            row.createCell(3).setCellValue(s.getTime());
            row.createCell(4).setCellValue(s.getPerTime());
            row.createCell(5).setCellValue(s.getVisibility());
            row.createCell(6).setCellValue(s.getStatus());
        }
        write(wb, response);
    }

    // ---- Employee ------------------------------------------------------

    public void empExcel(HttpServletResponse response) throws IOException {
        List<Emp> list = empRepository.findAll();
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("Employee Report");
        header(sheet, "ID", "TYPE", "FIRST NAME", "LAST NAME", "EMAIL", "MOBILE",
                "GENDER", "ADDRESS", "STATUS");
        int r = 1;
        for (Emp e : list) {
            HSSFRow row = sheet.createRow(r++);
            row.createCell(0).setCellValue(e.getId());
            row.createCell(1).setCellValue(e.getTypeEmp());
            row.createCell(2).setCellValue(e.getFirstName());
            row.createCell(3).setCellValue(e.getLastName());
            row.createCell(4).setCellValue(e.getEmail());
            row.createCell(5).setCellValue(e.getMobile());
            row.createCell(6).setCellValue(e.getGender());
            row.createCell(7).setCellValue(e.getAddress());
            row.createCell(8).setCellValue(e.getStatus());
        }
        write(wb, response);
    }

    // ---- Nurse ---------------------------------------------------------

    public void nurseExcel(HttpServletResponse response) throws IOException {
        List<Nur> list = nurseRepository.findAll();
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("Nurse Report");
        header(sheet, "ID", "FIRST NAME", "LAST NAME", "EMAIL", "MOBILE", "ADDRESS", "STATUS");
        int r = 1;
        for (Nur n : list) {
            HSSFRow row = sheet.createRow(r++);
            row.createCell(0).setCellValue(n.getId());
            row.createCell(1).setCellValue(n.getFirstName());
            row.createCell(2).setCellValue(n.getLastName());
            row.createCell(3).setCellValue(n.getEmail());
            row.createCell(4).setCellValue(n.getMobile());
            row.createCell(5).setCellValue(n.getAddress());
            row.createCell(6).setCellValue(n.getStatus());
        }
        write(wb, response);
    }

    // ---- Bed -----------------------------------------------------------

    public void bedExcel(HttpServletResponse response) throws IOException {
        List<Bed> list = bedRepository.findAll();
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("Bed Report");
        header(sheet, "ID", "BED TYPE", "CAPACITY", "DESCRIPTION", "SEX");
        int r = 1;
        for (Bed b : list) {
            HSSFRow row = sheet.createRow(r++);
            row.createCell(0).setCellValue(b.getId());
            row.createCell(1).setCellValue(b.getBedType());
            row.createCell(2).setCellValue(b.getBedCap());
            row.createCell(3).setCellValue(b.getBedDesc());
            row.createCell(4).setCellValue(b.getSex());
        }
        write(wb, response);
    }

    // ---- Notice --------------------------------------------------------

    public void noticeExcel(HttpServletResponse response) throws IOException {
        List<Notice> list = noticeRepository.findAll();
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("Notice Report");
        header(sheet, "ID", "TITLE", "DESCRIPTION", "START DATE", "END DATE");
        int r = 1;
        for (Notice n : list) {
            HSSFRow row = sheet.createRow(r++);
            row.createCell(0).setCellValue(n.getId());
            row.createCell(1).setCellValue(n.getTitle());
            row.createCell(2).setCellValue(n.getBedDesc());
            row.createCell(3).setCellValue(n.getStartDate());
            row.createCell(4).setCellValue(n.getEndDate());
        }
        write(wb, response);
    }

    // ---- Pharmacist ----------------------------------------------------

    public void pharExcel(HttpServletResponse response) throws IOException {
        List<Phar> list = pharmaRepository.findAll();
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("Pharmacist Report");
        header(sheet, "ID", "FIRST NAME", "LAST NAME", "EMAIL", "MOBILE",
                "GENDER", "ADDRESS", "STATUS");
        int r = 1;
        for (Phar p : list) {
            HSSFRow row = sheet.createRow(r++);
            row.createCell(0).setCellValue(p.getId());
            row.createCell(1).setCellValue(p.getFirstName());
            row.createCell(2).setCellValue(p.getLastName());
            row.createCell(3).setCellValue(p.getEmail());
            row.createCell(4).setCellValue(p.getMobile());
            row.createCell(5).setCellValue(p.getGender());
            row.createCell(6).setCellValue(p.getAddress());
            row.createCell(7).setCellValue(p.getStatus());
        }
        write(wb, response);
    }

    // ---- Appointment ---------------------------------------------------

    public void appointmentExcel(HttpServletResponse response) throws IOException {
        List<Appointment> list = appointmentRepository.findAll();
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("Appointment Report");
        header(sheet, "ID", "PATIENT", "DEPARTMENT", "DOCTOR", "DATE", "PROBLEM");
        int r = 1;
        for (Appointment a : list) {
            HSSFRow row = sheet.createRow(r++);
            row.createCell(0).setCellValue(a.getId());
            row.createCell(1).setCellValue(a.getPatient());
            row.createCell(2).setCellValue(a.getDepart());
            row.createCell(3).setCellValue(a.getDoctorn());
            row.createCell(4).setCellValue(a.getAdate());
            row.createCell(5).setCellValue(a.getProb());
        }
        write(wb, response);
    }

    // ---- findAll passthroughs used by the PDF exporters ----------------

    public List<AddDoctor> allDoctors()        { return doctorRepository.findAll(); }
    public List<Department> allDepartments()    { return departmentRepository.findAll(); }
    public List<Schedule> allSchedules()        { return scheduleRepository.findAll(); }
    public List<Emp> allEmps()                  { return empRepository.findAll(); }
    public List<Nur> allNurses()                { return nurseRepository.findAll(); }
    public List<Bed> allBeds()                  { return bedRepository.findAll(); }
    public List<Notice> allNotices()            { return noticeRepository.findAll(); }
    public List<Phar> allPharmacists()          { return pharmaRepository.findAll(); }
    public List<Appointment> allAppointments()  { return appointmentRepository.findAll(); }
}