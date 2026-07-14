package com.vgnit.medical.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.vgnit.medical.entity.AddDoctor;
import com.vgnit.medical.entity.Appointment;
import com.vgnit.medical.entity.Bed;
import com.vgnit.medical.entity.Department;
import com.vgnit.medical.entity.DoctorReport;
import com.vgnit.medical.entity.Emp;
import com.vgnit.medical.entity.Notice;
import com.vgnit.medical.entity.Nur;
import com.vgnit.medical.entity.Patient;
import com.vgnit.medical.entity.PatientReport;
import com.vgnit.medical.entity.Phar;
import com.vgnit.medical.entity.Schedule;
import com.vgnit.medical.entity.User;
import com.vgnit.medical.repository.UserRepository;
import com.vgnit.medical.service.AppointmentService;
import com.vgnit.medical.service.BedService;
import com.vgnit.medical.service.DepartmentService;
import com.vgnit.medical.service.DoctorRportService;
import com.vgnit.medical.service.DoctorService;
import com.vgnit.medical.service.EmpService;
import com.vgnit.medical.service.NoticeService;
import com.vgnit.medical.service.NurseService;
import com.vgnit.medical.service.PatientRportService;
import com.vgnit.medical.service.PatientService;
import com.vgnit.medical.service.PharmaService;
import com.vgnit.medical.service.ScheduleService;

import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.lowagie.text.DocumentException;
import com.vgnit.medical.pdf.PatientPdfExporter;
import com.vgnit.medical.pdf.PatientWisePdfExporter;
import com.vgnit.medical.pdf.DoctortWisePdfExporter;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private DoctorService doctorService;

	@Autowired
	private PatientService patientService;

	@Autowired
	private ScheduleService scheduleService;

	@Autowired
	private AppointmentService appointmentService;

	@Autowired
	private BedService bedService;

	@Autowired
	private NoticeService noticeService;

	@Autowired
	private EmpService empService;

	@Autowired
	private NurseService nurService;

	@Autowired
	private PharmaService pharService;

	@Autowired
	public DepartmentService departmentService;

	@Autowired
	private PatientRportService prService;

	@Autowired
	private DoctorRportService drService;

	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private UserRepository userRepository;

	/*
	 * // Return to ADMIN DASHBOARD !!
	 * 
	 * @GetMapping("/admin-page") // This "/admin-page" @GetMapping name will be
	 * same as given in security configuration or CustomSuccessHandler class !!
	 * public String adminPage(Model model, Principal principal) { UserDetails
	 * loadUserByUsername =
	 * userDetailsService.loadUserByUsername(principal.getName());
	 * model.addAttribute("user", loadUserByUsername);
	 * 
	 * return "admin-dashboard"; }
	 */

	
	@ModelAttribute
	public void getLoggedUser(Model model, Principal principal) {
		if (principal != null) {
			String email = principal.getName();
			User user = userRepository.findByEmail(email);
			if (user != null) {                        // guard against not-found user
				String role = user.getRole().toUpperCase();
				if (role.equalsIgnoreCase("ROLE_ADMIN")) {
					model.addAttribute("admin", role);
				}
				model.addAttribute("user", user);
			}
		}
	}
	
	// Return to ADMIN DASHBOARD !!
	@GetMapping("/admin-page") // This "/admin-page" @GetMapping name will be same as given in security
								// configuration or CustomSuccessHandler class !!
	public String adminPage(Model model ) {
//		UserDetails loadUserByUsername = userDetailsService.loadUserByUsername(principal.getName());
//		model.addAttribute("user", loadUserByUsername);

		long dPPP = departmentService.aql();
		model.addAttribute("opqr", dPPP);

		List<Bed> bddd = bedService.bpl();
		model.addAttribute("gaq", bddd.size());

		List<AddDoctor> findst = doctorService.findst();
		int size = findst.size();
		// System.out.println(size);
		model.addAttribute("findst", size);

		List<Patient> p = patientService.pp();
		model.addAttribute("p", p.size());

		List<Nur> n = nurService.mm();
		model.addAttribute("nn", n.size());

		List<Phar> phop = pharService.lop();
		model.addAttribute("gkl", phop.size());

		List<Patient> allPatient = patientService.getAllPatient();
		model.addAttribute("patientlist", allPatient);
		
		

		return "index";
	}

	@GetMapping("/add-doctor")
	public String addDoctor(Model model, Principal principal) {
		UserDetails loadUserByUsername = userDetailsService.loadUserByUsername(principal.getName());
		model.addAttribute("user", loadUserByUsername);

		AddDoctor addDoctor = new AddDoctor();
		model.addAttribute("addDoctor", addDoctor);
		return "add-doctor";
	}

	// #Image Variable uploadDir
		//public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/doctorimages";
	    public static String uploadDir = System.getProperty("user.dir") + "/uploads/doctorimages";
		@PostMapping("/saveDoctor")
		public String saveDoctor(@ModelAttribute("addDoctor") @RequestBody AddDoctor addDoctor,
								@RequestParam("doctorImage") MultipartFile file,
								@RequestParam("imgName") String imgName,
								HttpSession session) throws IOException
		{
			boolean checkEmail = doctorService.checkEmail(addDoctor.getEmail());
			if(checkEmail)
			{
				session.setAttribute("msg", "Given email id already exists!!");
				//System.out.println("AdmissionId already exist...!!");
			}
			else
			{
				String imageUUID;
				if(!file.isEmpty())
				{
					imageUUID = file.getOriginalFilename();
					//Path fileNameAndPath = Paths.get(uploadDir, imageUUID);
					//Files.write(fileNameAndPath, file.getBytes());	
				    Path fileNameAndPath = Paths.get(uploadDir, imageUUID);
					Files.createDirectories(fileNameAndPath.getParent());
					Files.write(fileNameAndPath, file.getBytes());
				}
				else
				{
					imageUUID = imgName;
				}
				addDoctor.setImageName(imageUUID);
				
				AddDoctor addDoctrWithCondition = doctorService.addDoctor(addDoctor);
				if(addDoctrWithCondition != null)
				{
					session.setAttribute("msg", "Record added successfully!!");
					//System.out.println("Student Register Successfully...!!");
				}
				else
				{
					session.setAttribute("msg", "Somthing went wrong!!");
					//System.out.println("Something error in server...!!");
				}
			}
			 return "redirect:/admin/doctorlist";
		}

	@GetMapping("/doctorlist")
	public String doctorList(Model model, @Param("keyword") String keyword, Principal principal) {
		UserDetails loadUserByUsername = userDetailsService.loadUserByUsername(principal.getName());
		model.addAttribute("user", loadUserByUsername);

		List<AddDoctor> doctorlist = doctorService.listAll(keyword);

		model.addAttribute("doctorlist", doctorlist);
		model.addAttribute("keyword", keyword);
		return "doctor-list";
	}

	@GetMapping("/deleteDoctor/{id}")
	public String deletDoctorById(@PathVariable(value = "id") Long id, HttpSession session) {
		session.setAttribute("msgd", "Record deleted successfully!!");
		doctorService.deletDoctor(id);
		return "redirect:/admin/doctorlist";
	}

	@GetMapping("/editDoctor/{id}")
	public String editDoctor(@PathVariable(value = "id") Long id, Model model, Principal principal) {
		UserDetails loadUserByUsername = userDetailsService.loadUserByUsername(principal.getName());
		model.addAttribute("user", loadUserByUsername);

		model.addAttribute("addDoctor", doctorService.getDoctorById(id));
		return "edit-doctor";

	}

	@PostMapping("/updateDoctor")
	public String updateDoctor(@ModelAttribute("addDoctor") @RequestBody AddDoctor addDoctor,
							@RequestParam("doctorImage") MultipartFile file,
							@RequestParam("imgName") String imgName,
							HttpSession session) throws IOException
	{
			String imageUUID;
			if(!file.isEmpty())
			{
				imageUUID = file.getOriginalFilename();
				//Path fileNameAndPath = Paths.get(uploadDir, imageUUID);
				//Files.write(fileNameAndPath, file.getBytes());	
				Path fileNameAndPath = Paths.get(uploadDir, imageUUID);
				Files.createDirectories(fileNameAndPath.getParent());
				Files.write(fileNameAndPath, file.getBytes());
			}
			else
			{
				imageUUID = imgName;
			}
			addDoctor.setImageName(imageUUID);
			
			doctorService.addDoctor(addDoctor);
			session.setAttribute("msgu", "Record updated successfully!!");
			return "redirect:/admin/doctorlist";
	}

	@GetMapping("/add-patient")
	public String addPatient(Model model, Principal principal) {
		UserDetails loadUserByUsername = userDetailsService.loadUserByUsername(principal.getName());
		model.addAttribute("user", loadUserByUsername);

		Patient patient = new Patient();
		model.addAttribute("patient", patient);
		return "add-patient";
	}
	
	
	@PostMapping("/savePatient")
	public String savePatient(@ModelAttribute("patient") @RequestBody Patient patient, Model model, HttpSession session, Principal principal)
	{
		UserDetails loadUserByUsername = userDetailsService.loadUserByUsername(principal.getName());
		model.addAttribute("user", loadUserByUsername); 
		
		Patient addpatient = patientService.addPatient(patient);
		session.setAttribute("msg", "Record added successfully!!");
		return "redirect:/admin/patientlist";
	}
	
	@GetMapping("/patientlist")
	public String gettAllPatient(Model model, @Param("keyword") String keyword, Principal principal)
	{
		UserDetails loadUserByUsername = userDetailsService.loadUserByUsername(principal.getName());
		model.addAttribute("user", loadUserByUsername);
		
		List<Patient> allPatient = patientService.getAllPatient(keyword);
		model.addAttribute("patientlist", allPatient);
		model.addAttribute("keyword", keyword);
		return "patient-list";
		
	}
	
	@GetMapping("/deletePatient/{id}")
	public String deletePatient(@PathVariable(value = "id") Long id, HttpSession session)
	{
		patientService.deletePatient(id);
		session.setAttribute("msgd", "Record deleted successfully!!");
		return "redirect:/admin/patientlist";
	}
	
	@GetMapping("/editPatient/{id}")
	public String editPaient(@PathVariable(value = "id") Long id, Model model)
	{
		model.addAttribute("patient", patientService.getPatientById(id));
		return "edit-patient";
	}
	
	@PostMapping("/updatePatient")
	public String updatePatient(@ModelAttribute("patient") @RequestBody Patient patient, Model model, HttpSession session)
	{	
		patientService.addPatient(patient);
		session.setAttribute("msgu", "Record added successfully!!");
		return "redirect:/admin/patientlist";
	}
	
	@GetMapping("/add-Schedule")
	public String addSchedule(Model model)
	{
		Schedule schedule= new Schedule();
		model.addAttribute("schedule", schedule);
		return "add-schedule";
	}
	
	
	@PostMapping("/saveSchedule")
	public String saveShedule(@ModelAttribute("schedule") @RequestBody Schedule schedule, HttpSession session)
	{
		Schedule saveShedule = scheduleService.saveShedule(schedule);
		session.setAttribute("msg", "Record added successfully!");
		return "redirect:/admin/schedulelist";
	}
	
	@GetMapping("/schedulelist")
	public String getSchedule(@Param("keyword") String keyword, Model model)
	{
		List<Schedule> allSchedule = scheduleService.getAllSchedule(keyword);
		model.addAttribute("allSchedule", allSchedule);
		model.addAttribute("keyword", keyword);
		return "schedule-list";
	}
	
	@GetMapping("/deleteSchedule/{id}")
	public String delete(@PathVariable(value = "id") Long id, HttpSession session)
	{
		scheduleService.deleteSchedule(id);
		session.setAttribute("msgd", "Record deleted successfully");
		return "redirect:/admin/schedulelist";
	}
	
	@GetMapping("/getSchedule/{id}")
	public String getSchedule(@PathVariable(value = "id") Long id, Model model)
	{
		model.addAttribute("schedule", scheduleService.getParticularSchedule(id));
		return "edit-schedule";
	}
	
	@PostMapping("/uSchedule")
	public String uShedule(@ModelAttribute("schedule") @RequestBody Schedule schedule, HttpSession session)
	{
		Schedule saveShedule = scheduleService.saveShedule(schedule);
		session.setAttribute("msgu", "Record updated successfully!");
		return "redirect:/admin/schedulelist";
	}
	
	
	@GetMapping("/add-Appointment")
	public String addAppointment(Model model)
	{
		Appointment appointment= new Appointment();
		model.addAttribute("appointment", appointment);
		return "add-appointment";
	}
	
	
	@PostMapping("/saveAppointment")
	public String saveAppoint(@ModelAttribute("appointment") @RequestBody Appointment appointment, HttpSession session)
	{
		Appointment saveappointment = appointmentService.saveAppointment(appointment);
		session.setAttribute("msg", "Record added successfully!");
		return "redirect:/admin/appointmentlist";
	}
	
	@GetMapping("/appointmentlist")
	public String getAppoint(@Param("keyword") String keyword, Model model)
	{
		List<Appointment> allappointment = appointmentService.getAllAppointment(keyword);
		model.addAttribute("allappointment", allappointment);
		model.addAttribute("keyword", keyword);
		return "appointment-list";
	}
	
	@GetMapping("/deleteAppointment/{id}")
	public String deleteApp(@PathVariable(value = "id") Long id, HttpSession session)
	{
		appointmentService.deleteAppointment(id);
		session.setAttribute("msgd", "Record deleted successfully");
		return "redirect:/admin/appointmentlist";
	}
	
	@GetMapping("/getAppointment/{id}")
	public String getAppoint(@PathVariable(value = "id") Long id, Model model)
	{
		model.addAttribute("appointment", appointmentService.getParticularAppointment(id));
		return "edit-appointment";
	}
	
	@PostMapping("/uAppointment")
	public String uAppoint(@ModelAttribute("appointment") @RequestBody Appointment appointment, HttpSession session)
	{
		Appointment uappointment = appointmentService.saveAppointment(appointment);
		session.setAttribute("msgu", "Record updated successfully!");
		return "redirect:/admin/appointmentlist";
	}
	
	
	@GetMapping("/add-Bed")
	public String addBed(Model model)
	{
		Bed bed= new Bed();
		model.addAttribute("bed", bed);
		return "add-bed";
	}
	
	
	@PostMapping("/saveBed")
	public String saveBed(@ModelAttribute("bed") @RequestBody Bed bed, HttpSession session)
	{
		Bed sbed = bedService.saveBed(bed);
		session.setAttribute("msg", "Record added successfully!");
		return "redirect:/admin/bedlist";
	}
	
	@GetMapping("/bedlist")
	public String getBed(@Param("keyword") String keyword, Model model)
	{
		List<Bed> bed = bedService.getAllBed(keyword);
		model.addAttribute("allbed", bed);
		model.addAttribute("keyword", keyword);
		return "bed-list";
	}
	
	@GetMapping("/deletebed/{id}")
	public String deleteBed(@PathVariable(value = "id") Long id, HttpSession session)
	{
		bedService.deleteBed(id);
		session.setAttribute("msgd", "Record deleted successfully");
		return "redirect:/admin/bedlist";
	}
	
	@GetMapping("/getBed/{id}")
	public String getBed(@PathVariable(value = "id") Long id, Model model)
	{
		model.addAttribute("bed", bedService.getParticularBed(id));
		return "edit-bed";
	}
	
	@PostMapping("/uBed")
	public String uBed(@ModelAttribute("bed") @RequestBody Bed bed, HttpSession session)
	{
		Bed ubed = bedService.saveBed(bed);
		session.setAttribute("msgu", "Record updated successfully!");
		return "redirect:/admin/bedlist";
	}
	
	
	
	@GetMapping("/add-Notice")
	public String addNotice(Model model)
	{
		Notice notice= new Notice();
		model.addAttribute("notice", notice);
		return "add-notice";
	}
	
	
	@PostMapping("/saveNotice")
	public String saveNotice(@ModelAttribute("notice") @RequestBody Notice notice, HttpSession session)
	{
		Notice sNotice = noticeService.saveNotice(notice);
		session.setAttribute("msg", "Record added successfully!");
		return "redirect:/admin/noticelist";
	}
	
	@GetMapping("/noticelist")
	public String getNotice(@Param("keyword") String keyword, Model model)
	{
		List<Notice> notice = noticeService.getAllNotice(keyword);
		model.addAttribute("allnotice", notice);
		model.addAttribute("keyword", keyword);
		return "notice-list";
	}
	
	@GetMapping("/deletenotice/{id}")
	public String deleteNotice(@PathVariable(value = "id") Long id, HttpSession session)
	{
		noticeService.deleteNotice(id);
		session.setAttribute("msgd", "Record deleted successfully");
		return "redirect:/admin/noticelist";
	}
	
	@GetMapping("/getNotice/{id}")
	public String getNotice(@PathVariable(value = "id") Long id, Model model)
	{
		model.addAttribute("notice", noticeService.getParticularNotice(id));
		return "edit-notice";
	}
	
	@PostMapping("/uNotice")
	public String uNotice(@ModelAttribute("bed") @RequestBody Notice notice, HttpSession session)
	{
		Notice unotice = noticeService.saveNotice(notice);
		session.setAttribute("msgu", "Record updated successfully!");
		return "redirect:/admin/noticelist";
	}
	
	
	@GetMapping("/add-Emp")
	public String addEmp(Model model)
	{
		Emp emp= new Emp();
		model.addAttribute("emp", emp);
		return "add-employee";
	}
	
	
	@PostMapping("/saveEmp")
	public String saveEmp(@ModelAttribute("emp") @RequestBody Emp emp, HttpSession session)
	{
		Emp semp = empService.saveEmp(emp);
		session.setAttribute("msg", "Record added successfully!");
		return "redirect:/admin/emplist";
	}
	
	@GetMapping("/emplist")
	public String getEmp(@Param("keyword") String keyword, Model model)
	{
		List<Emp> emp = empService.getAllEmp(keyword);
		model.addAttribute("allemp", emp);
		model.addAttribute("keyword", keyword);
		return "employee-list";
	}
	
	@GetMapping("/deleteemp/{id}")
	public String deleteEmp(@PathVariable(value = "id") Long id, HttpSession session)
	{
		empService.deleteEmp(id);
		session.setAttribute("msgd", "Record deleted successfully");
		return "redirect:/admin/emplist";
	}
	
	@GetMapping("/getEmp/{id}")
	public String getEmp(@PathVariable(value = "id") Long id, Model model)
	{
		model.addAttribute("emp", empService.getParticularEmp(id));
		return "edit-employee";
	}
	
	@PostMapping("/uEmp")
	public String uEmp(@ModelAttribute("emp") @RequestBody Emp emp, HttpSession session)
	{
		Emp uemp = empService.saveEmp(emp);
		session.setAttribute("msgu", "Record updated successfully!");
		return "redirect:/admin/emplist";
	}
	
	
	@GetMapping("/add-Nur")
	public String addNur(Model model)
	{
		Nur nur= new Nur();
		model.addAttribute("nur", nur);
		return "add-nurse";
	}
	
	
	@PostMapping("/saveNur")
	public String saveNur(@ModelAttribute("nur") @RequestBody Nur nur, HttpSession session)
	{
		Nur snur = nurService.saveNur(nur);
		session.setAttribute("msg", "Record added successfully!");
		return "redirect:/admin/nurlist";
	}
	
	@GetMapping("/nurlist")
	public String getNur(@Param("keyword") String keyword, Model model)
	{
		List<Nur> nur = nurService.getAllNur(keyword);
		model.addAttribute("allnur", nur);
		model.addAttribute("keyword", keyword);
		return "nurse-list";
	}
	
	@GetMapping("/deletenur/{id}")
	public String deleteNur(@PathVariable(value = "id") Long id, HttpSession session)
	{
		nurService.deleteNur(id);
		session.setAttribute("msgd", "Record deleted successfully");
		return "redirect:/admin/nurlist";
	}
	
	@GetMapping("/getNur/{id}")
	public String getNur(@PathVariable(value = "id") Long id, Model model)
	{
		model.addAttribute("nur", nurService.getParticularNur(id));
		return "edit-nurse";
	}
	
	@PostMapping("/uNur")
	public String unur(@ModelAttribute("nur") @RequestBody Nur nur, HttpSession session)
	{
		Nur unur = nurService.saveNur(nur);
		session.setAttribute("msgu", "Record updated successfully!");
		return "redirect:/admin/nurlist";
	}
	

	@GetMapping("/add-Phar")
	public String addPhar(Model model)
	{
		Phar phar= new Phar();
		model.addAttribute("phar", phar);
		return "add-pharmacist";
	}
	
	
	@PostMapping("/savePhar")
	public String savePhar(@ModelAttribute("phar") @RequestBody Phar phar, HttpSession session)
	{
		Phar sphar = pharService.savePhar(phar);
		session.setAttribute("msg", "Record added successfully!");
		return "redirect:/admin/pharlist";
	}
	
	@GetMapping("/pharlist")
	public String getPhar(@Param("keyword") String keyword, Model model)
	{
		List<Phar> phar = pharService.getAllPhar(keyword);
		model.addAttribute("allphar", phar);
		model.addAttribute("keyword", keyword);
		return "pharmacist-list";
	}
	
	@GetMapping("/deletephar/{id}")
	public String deletePhar(@PathVariable(value = "id") Long id, HttpSession session)
	{
		pharService.deletePhar(id);
		session.setAttribute("msgd", "Record deleted successfully");
		return "redirect:/admin/pharlist";
	}
	
	@GetMapping("/getPhar/{id}")
	public String getPhar(@PathVariable(value = "id") Long id, Model model)
	{
		model.addAttribute("phar", pharService.getParticularPhar(id));
		return "edit-pharmacist";
	}
	
	@PostMapping("/uPhar")
	public String uphar(@ModelAttribute("phar") @RequestBody Phar phar, HttpSession session)
	{
		Phar uphar = pharService.savePhar(phar);
		session.setAttribute("msgu", "Record updated successfully!");
		return "redirect:/admin/pharlist";
	}
	

	@GetMapping("/add-department")
	public String AddDept(Model model)
	{
		Department dept= new Department();
		model.addAttribute("dept", dept);
		return "add-department";
	}

	@PostMapping("/saveDept")
	public String saveDept(@ModelAttribute("dept") @RequestBody Department dept, HttpSession session ) 
	{
		departmentService.adddepartment(dept);
		session.setAttribute("msg","Record added successfully!...." );
		return "redirect:/admin/departmentlist";
	}
	
	
	@GetMapping("/departmentlist")
	public String getDept(@Param("keyword") String keyword, Model model)
	{
		List<Department> deptall = departmentService.getAllDept(keyword);
		model.addAttribute("deptall", deptall);
		model.addAttribute("keyword", keyword);
		return "department-list";
	}
	
	@GetMapping("/deletedpt/{id}")
	public String deleteDept(@PathVariable(value = "id") Long id, HttpSession session)
	{
		departmentService.deleteDept(id);
		session.setAttribute("msgd", "Record deleted successfully");
		return "redirect:/admin/departmentlist";
	}
	
	@GetMapping("/getDept/{id}")
	public String getDept(@PathVariable(value = "id") Long id, Model model)
	{
		model.addAttribute("dept", departmentService.getParticularDept(id));
		return "edit-department";
	}
	
	@PostMapping("/uDept")
	public String uDept(@ModelAttribute("dept") @RequestBody Department dept, HttpSession session)
	{
		Department department = departmentService.adddepartment(dept);
		session.setAttribute("msgu", "Record updated successfully!");
		return "redirect:/admin/departmentlist";
	}
	
/*=================================================================================================*/	
	
	@GetMapping("/add-patient-wise-report")
	public String AddReportPatient(Model model) 
	{
		PatientReport patientReport = new PatientReport();
		model.addAttribute("prp", patientReport);
		return "add-patient-wise-report";

	}

	@PostMapping("/saveReportPatient")
	public String saveReportPatient(@ModelAttribute("prp") @RequestBody PatientReport patientReport, HttpSession session)
	{
		prService.addReportPatient(patientReport);
		session.setAttribute("msg", "Record added successfully!");
		return "redirect:/admin/preportlist";
	}
	
	@GetMapping("/preportlist")
	public String getReport(@Param("keyword") String keyword, Model model)
	{
		List<PatientReport> ptall = prService.getAllRep(keyword);
		model.addAttribute("ptall", ptall);
		model.addAttribute("keyword", keyword);
		return "patient-wise-report";
	}
	
	@GetMapping("/deletedptr/{id}")
	public String deleteRep(@PathVariable(value = "id") Long id, HttpSession session)
	{
		prService.deleteRep(id);
		session.setAttribute("msgd", "Record deleted successfully");
		return "redirect:/admin/preportlist";
	}
	
	@GetMapping("/getpt/{id}")
	public String getRep(@PathVariable(value = "id") Long id, Model model)
	{
		model.addAttribute("prp", prService.getParticularRep(id));
		return "edit-patient-wise-report";
	}
	
	@PostMapping("/uRPT")
	public String uRPT(@ModelAttribute("prp") @RequestBody PatientReport patientReport, HttpSession session)
	{
		prService.addRep(patientReport);
		session.setAttribute("msgu", "Record updated successfully!");
		return "redirect:/admin/preportlist";
	}
	
	/*=================================================================================================*/	
	
	@GetMapping("/add-doctor-wise-report")
	public String AddReportDocter(Model model)
	{
		DoctorReport reportDocter = new DoctorReport();
		model.addAttribute("drp", reportDocter);
		return ("add-doctor-wise-report");

	}

	@PostMapping("/saveReportDocter")
	public String saveReportDocter(@ModelAttribute("drp") @RequestBody DoctorReport doctorReport,
			HttpSession session) throws IOException 
	{
		DoctorReport addReportWithCondition = drService.addReportDocter(doctorReport);
		if (addReportWithCondition != null) 
		{
			session.setAttribute("msg", "Record added Successfully!....");
		}
		else 
		{
			session.setAttribute("msga", "Something Wrong!...");
		}

		return "redirect:/admin/dreportlist";
	}
	
	@GetMapping("/dreportlist")
	public String getDReport(@Param("keyword") String keyword, Model model)
	{
		List<DoctorReport> dtall = drService.getAllRep(keyword);
		model.addAttribute("dtall", dtall);
		model.addAttribute("keyword", keyword);
		return "doctor-wise-report";
	}
	
	@GetMapping("/deleteddt/{id}")
	public String deleteDRep(@PathVariable(value = "id") Long id, HttpSession session)
	{
		drService.deleteRep(id);
		session.setAttribute("msgd", "Record deleted successfully");
		return "redirect:/admin/dreportlist";
	}
	
	@GetMapping("/getdt/{id}")
	public String getDRep(@PathVariable(value = "id") Long id, Model model)
	{
		model.addAttribute("drp", drService.getParticularRep(id));
		return "edit-doctor-wise-report";
	}
	
	@PostMapping("/uRDT")
	public String uRDT(@ModelAttribute("drp") @RequestBody DoctorReport doctorReport, HttpSession session)
	{
		drService.addRep(doctorReport);
		session.setAttribute("msgu", "Record updated successfully!");
		return "redirect:/admin/dreportlist";
	}
	
	
	@GetMapping("/filterByDate")
	public String getData(@Param("startDate") String startDate,
						  @Param("endDate") String endDate, Model model)
	{
		 List<DoctorReport> filteredData = drService.findByDateBetween(startDate, endDate);
	     model.addAttribute("dtall", filteredData);
	     return "doctor-wise-report";
	}

	
	
	
	/* ============================ */

//	@PostMapping("/savePatient")
//	public String savePatient(@ModelAttribute("patient") @RequestBody Patient patient, Model model, HttpSession session,
//			Principal principal) {
//		UserDetails loadUserByUsername = userDetailsService.loadUserByUsername(principal.getName());
//		model.addAttribute("user", loadUserByUsername);
//
//		patientService.addPatient(patient);
//		session.setAttribute("msg", "Record added successfully!!");
//		return "redirect:/admin/patientlist";
//	}
//
//	@GetMapping("/patientlist")
//	public String gettAllPatient(Model model, @Param("keyword") String keyword, Principal principal) {
//		UserDetails loadUserByUsername = userDetailsService.loadUserByUsername(principal.getName());
//		model.addAttribute("user", loadUserByUsername);
//
//		List<Patient> allPatient = patientService.getAllPatient(keyword);
//		model.addAttribute("patientlist", allPatient);
//		model.addAttribute("keyword", keyword);
//		return "patient-list";
//
//	}
//
//	@GetMapping("/deletePatient/{id}")
//	public String deletePatient(@PathVariable(value = "id") Long id, HttpSession session) {
//		patientService.deletePatient(id);
//		session.setAttribute("msgd", "Record deleted successfully!!");
//		return "redirect:/admin/patientlist";
//	}
//
//	@GetMapping("/editPatient/{id}")
//	public String editPaient(@PathVariable(value = "id") Long id, Model model) {
//		model.addAttribute("patient", patientService.getPatientById(id));
//		return "edit-patient";
//	}
//
//	@PostMapping("/updatePatient")
//	public String updatePatient(@ModelAttribute("patient") @RequestBody Patient patient, Model model,
//			HttpSession session) {
//		patientService.addPatient(patient);
//		session.setAttribute("msgu", "Record updated successfully!!");
//		return "redirect:/admin/patientlist";
//	}
//
// (07) Print excel report for all...!!
@GetMapping("/PrintPatientExcelReport")
public void generateExcelReportDepartments(HttpServletResponse response) throws IOException {
	response.setContentType("application/octet-stream");

	String headerKey = "Content-Disposition";
	String headerValue = "attachment;filename=patient.xls";

	response.setHeader(headerKey, headerValue);

	patientService.generateExcel(response);
}
//
// (08) Print PDF Report for all...!!
@GetMapping("/PrintPatientPdfReport")
public void exportToPDF(HttpServletResponse HttpServletResponse) throws DocumentException, IOException {
	HttpServletResponse.setContentType("application/pdf");

	DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
	String currentDateTime = dateFormatter.format(new Date());

	String headerKey = "Content-Disposition";
	String headerValue = "attachment; filename= patients_" + currentDateTime + ".pdf";

	HttpServletResponse.setHeader(headerKey, headerValue);

	List<Patient> listPatients = patientService.listAllPdf();

	PatientPdfExporter exporter = new PatientPdfExporter(listPatients);
	exporter.export(HttpServletResponse);
}
//
//	@GetMapping("/add-Schedule")
//	public String addSchedule(Model model) {
//		Schedule schedule = new Schedule();
//		model.addAttribute("schedule", schedule);
//		return "add-schedule";
//	}
//
//	@PostMapping("/saveSchedule")
//	public String saveShedule(@ModelAttribute("schedule") @RequestBody Schedule schedule, HttpSession session) {
//		scheduleService.saveShedule(schedule);
//		session.setAttribute("msg", "Record added successfully!");
//		return "redirect:/admin/schedulelist";
//	}
//
//	@GetMapping("/schedulelist")
//	public String getSchedule(@Param("keyword") String keyword, Model model) {
//		List<Schedule> allSchedule = scheduleService.getAllSchedule(keyword);
//		model.addAttribute("allSchedule", allSchedule);
//		model.addAttribute("keyword", keyword);
//		return "schedule-list";
//	}
//
//	@GetMapping("/deleteSchedule/{id}")
//	public String delete(@PathVariable(value = "id") Long id, HttpSession session) {
//		scheduleService.deleteSchedule(id);
//		session.setAttribute("msgd", "Record deleted successfully");
//		return "redirect:/admin/schedulelist";
//	}
//
//	@GetMapping("/getSchedule/{id}")
//	public String getSchedule(@PathVariable(value = "id") Long id, Model model) {
//		model.addAttribute("schedule", scheduleService.getParticularSchedule(id));
//		return "edit-schedule";
//	}
//
//	@PostMapping("/uSchedule")
//	public String uShedule(@ModelAttribute("schedule") @RequestBody Schedule schedule, HttpSession session) {
//		scheduleService.saveShedule(schedule);
//		session.setAttribute("msgu", "Record updated successfully!");
//		return "redirect:/admin/schedulelist";
//	}
//
//	@GetMapping("/add-Appointment")
//	public String addAppointment(Model model) {
//		Appointment appointment = new Appointment();
//		model.addAttribute("appointment", appointment);
//		return "add-appointment";
//	}
//
//	@PostMapping("/saveAppointment")
//	public String saveAppoint(@ModelAttribute("appointment") @RequestBody Appointment appointment,
//			HttpSession session) {
//		appointmentService.saveAppointment(appointment);
//		session.setAttribute("msg", "Record added successfully!");
//		return "redirect:/admin/appointmentlist";
//	}
//
//	@GetMapping("/appointmentlist")
//	public String getAppoint(@Param("keyword") String keyword, Model model) {
//		List<Appointment> allappointment = appointmentService.getAllAppointment(keyword);
//		model.addAttribute("allappointment", allappointment);
//		model.addAttribute("keyword", keyword);
//		return "appointment-list";
//	}
//
//	@GetMapping("/deleteAppointment/{id}")
//	public String deleteApp(@PathVariable(value = "id") Long id, HttpSession session) {
//		appointmentService.deleteAppointment(id);
//		session.setAttribute("msgd", "Record deleted successfully");
//		return "redirect:/admin/appointmentlist";
//	}
//
//	@GetMapping("/getAppointment/{id}")
//	public String getAppoint(@PathVariable(value = "id") Long id, Model model) {
//		model.addAttribute("appointment", appointmentService.getParticularAppointment(id));
//		return "edit-appointment";
//	}
//
//	@PostMapping("/uAppointment")
//	public String uAppoint(@ModelAttribute("appointment") @RequestBody Appointment appointment, HttpSession session) {
//		appointmentService.saveAppointment(appointment);
//		session.setAttribute("msgu", "Record updated successfully!");
//		return "redirect:/admin/appointmentlist";
//	}
//
//	@GetMapping("/add-Bed")
//	public String addBed(Model model) {
//		Bed bed = new Bed();
//		model.addAttribute("bed", bed);
//		return "add-bed";
//	}
//
//	@PostMapping("/saveBed")
//	public String saveBed(@ModelAttribute("bed") @RequestBody Bed bed, HttpSession session) {
//		bedService.saveBed(bed);
//		session.setAttribute("msg", "Record added successfully!");
//		return "redirect:/admin/bedlist";
//	}
//
//	@GetMapping("/bedlist")
//	public String getBed(@Param("keyword") String keyword, Model model) {
//		List<Bed> bed = bedService.getAllBed(keyword);
//		model.addAttribute("allbed", bed);
//		model.addAttribute("keyword", keyword);
//		return "bed-list";
//	}
//
//	@GetMapping("/deletebed/{id}")
//	public String deleteBed(@PathVariable(value = "id") Long id, HttpSession session) {
//		bedService.deleteBed(id);
//		session.setAttribute("msgd", "Record deleted successfully");
//		return "redirect:/admin/bedlist";
//	}
//
//	@GetMapping("/getBed/{id}")
//	public String getBed(@PathVariable(value = "id") Long id, Model model) {
//		model.addAttribute("bed", bedService.getParticularBed(id));
//		return "edit-bed";
//	}
//
//	@PostMapping("/uBed")
//	public String uBed(@ModelAttribute("bed") @RequestBody Bed bed, HttpSession session) {
//		bedService.saveBed(bed);
//		session.setAttribute("msgu", "Record updated successfully!");
//		return "redirect:/admin/bedlist";
//	}
//
//	@GetMapping("/add-Notice")
//	public String addNotice(Model model) {
//		Notice notice = new Notice();
//		model.addAttribute("notice", notice);
//		return "add-notice";
//	}
//
//	@PostMapping("/saveNotice")
//	public String saveNotice(@ModelAttribute("notice") @RequestBody Notice notice, HttpSession session) {
//		noticeService.saveNotice(notice);
//		session.setAttribute("msg", "Record added successfully!");
//		return "redirect:/admin/noticelist";
//	}
//
//	@GetMapping("/noticelist")
//	public String getNotice(@Param("keyword") String keyword, Model model) {
//		List<Notice> notice = noticeService.getAllNotice(keyword);
//		model.addAttribute("allnotice", notice);
//		model.addAttribute("keyword", keyword);
//		return "notice-list";
//	}
//
//	@GetMapping("/deletenotice/{id}")
//	public String deleteNotice(@PathVariable(value = "id") Long id, HttpSession session) {
//		noticeService.deleteNotice(id);
//		session.setAttribute("msgd", "Record deleted successfully");
//		return "redirect:/admin/noticelist";
//	}
//
//	@GetMapping("/getNotice/{id}")
//	public String getNotice(@PathVariable(value = "id") Long id, Model model) {
//		model.addAttribute("notice", noticeService.getParticularNotice(id));
//		return "edit-notice";
//	}
//
//	@PostMapping("/uNotice")
//	public String uNotice(@ModelAttribute("bed") @RequestBody Notice notice, HttpSession session) {
//		noticeService.saveNotice(notice);
//		session.setAttribute("msgu", "Record updated successfully!");
//		return "redirect:/admin/noticelist";
//	}
//
//	@GetMapping("/add-Emp")
//	public String addEmp(Model model) {
//		Emp emp = new Emp();
//		model.addAttribute("emp", emp);
//		return "add-employee";
//	}
//
//	@PostMapping("/saveEmp")
//	public String saveEmp(@ModelAttribute("emp") @RequestBody Emp emp, HttpSession session) {
//		empService.saveEmp(emp);
//		session.setAttribute("msg", "Record added successfully!");
//		return "redirect:/admin/emplist";
//	}
//
//	@GetMapping("/emplist")
//	public String getEmp(@Param("keyword") String keyword, Model model) {
//		List<Emp> emp = empService.getAllEmp(keyword);
//		model.addAttribute("allemp", emp);
//		model.addAttribute("keyword", keyword);
//		return "employee-list";
//	}
//
//	@GetMapping("/deleteemp/{id}")
//	public String deleteEmp(@PathVariable(value = "id") Long id, HttpSession session) {
//		empService.deleteEmp(id);
//		session.setAttribute("msgd", "Record deleted successfully");
//		return "redirect:/admin/emplist";
//	}
//
//	@GetMapping("/getEmp/{id}")
//	public String getEmp(@PathVariable(value = "id") Long id, Model model) {
//		model.addAttribute("emp", empService.getParticularEmp(id));
//		return "edit-employee";
//	}
//
//	@PostMapping("/uEmp")
//	public String uEmp(@ModelAttribute("emp") @RequestBody Emp emp, HttpSession session) {
//		empService.saveEmp(emp);
//		session.setAttribute("msgu", "Record updated successfully!");
//		return "redirect:/admin/emplist";
//	}
//
//	@GetMapping("/add-Nur")
//	public String addNur(Model model) {
//		Nur nur = new Nur();
//		model.addAttribute("nur", nur);
//		return "add-nurse";
//	}
//
//	@PostMapping("/saveNur")
//	public String saveNur(@ModelAttribute("nur") @RequestBody Nur nur, HttpSession session) {
//		nurService.saveNur(nur);
//		session.setAttribute("msg", "Record added successfully!");
//		return "redirect:/admin/nurlist";
//	}
//
//	@GetMapping("/nurlist")
//	public String getNur(@Param("keyword") String keyword, Model model) {
//		List<Nur> nur = nurService.getAllNur(keyword);
//		model.addAttribute("allnur", nur);
//		model.addAttribute("keyword", keyword);
//		return "nurse-list";
//	}
//
//	@GetMapping("/deletenur/{id}")
//	public String deleteNur(@PathVariable(value = "id") Long id, HttpSession session) {
//		nurService.deleteNur(id);
//		session.setAttribute("msgd", "Record deleted successfully");
//		return "redirect:/admin/nurlist";
//	}
//
//	@GetMapping("/getNur/{id}")
//	public String getNur(@PathVariable(value = "id") Long id, Model model) {
//		model.addAttribute("nur", nurService.getParticularNur(id));
//		return "edit-nurse";
//	}
//
//	@PostMapping("/uNur")
//	public String unur(@ModelAttribute("nur") @RequestBody Nur nur, HttpSession session) {
//		nurService.saveNur(nur);
//		session.setAttribute("msgu", "Record updated successfully!");
//		return "redirect:/admin/nurlist";
//	}
//
//	@GetMapping("/add-Phar")
//	public String addPhar(Model model) {
//		Phar phar = new Phar();
//		model.addAttribute("phar", phar);
//		return "add-pharmacist";
//	}
//
//	@PostMapping("/savePhar")
//	public String savePhar(@ModelAttribute("phar") @RequestBody Phar phar, HttpSession session) {
//		pharService.savePhar(phar);
//		session.setAttribute("msg", "Record added successfully!");
//		return "redirect:/admin/pharlist";
//	}
//
//	@GetMapping("/pharlist")
//	public String getPhar(@Param("keyword") String keyword, Model model) {
//		List<Phar> phar = pharService.getAllPhar(keyword);
//		model.addAttribute("allphar", phar);
//		model.addAttribute("keyword", keyword);
//		return "pharmacist-list";
//	}
//
//	@GetMapping("/deletephar/{id}")
//	public String deletePhar(@PathVariable(value = "id") Long id, HttpSession session) {
//		pharService.deletePhar(id);
//		session.setAttribute("msgd", "Record deleted successfully");
//		return "redirect:/admin/pharlist";
//	}
//
//	@GetMapping("/getPhar/{id}")
//	public String getPhar(@PathVariable(value = "id") Long id, Model model) {
//		model.addAttribute("phar", pharService.getParticularPhar(id));
//		return "edit-pharmacist";
//	}
//
//	@PostMapping("/uPhar")
//	public String uphar(@ModelAttribute("phar") @RequestBody Phar phar, HttpSession session) {
//		pharService.savePhar(phar);
//		session.setAttribute("msgu", "Record updated successfully!");
//		return "redirect:/admin/pharlist";
//	}
//
//	@GetMapping("/add-department")
//	public String AddDept(Model model) {
//		Department dept = new Department();
//		model.addAttribute("dept", dept);
//		return "add-department";
//	}
//
//	@PostMapping("/saveDept")
//	public String saveDept(@ModelAttribute("dept") @RequestBody Department dept, HttpSession session) {
//		departmentService.adddepartment(dept);
//		session.setAttribute("msg", "Record added successfully!....");
//		return "redirect:/admin/departmentlist";
//	}
//
//	@GetMapping("/departmentlist")
//	public String getDept(@Param("keyword") String keyword, Model model) {
//		List<Department> deptall = departmentService.getAllDept(keyword);
//		model.addAttribute("deptall", deptall);
//		model.addAttribute("keyword", keyword);
//		return "department-list";
//	}
//
//	@GetMapping("/deletedpt/{id}")
//	public String deleteDept(@PathVariable(value = "id") Long id, HttpSession session) {
//		departmentService.deleteDept(id);
//		session.setAttribute("msgd", "Record deleted successfully");
//		return "redirect:/admin/departmentlist";
//	}
//
//	@GetMapping("/getDept/{id}")
//	public String getDept(@PathVariable(value = "id") Long id, Model model) {
//		model.addAttribute("dept", departmentService.getParticularDept(id));
//		return "edit-department";
//	}
//
//	@PostMapping("/uDept")
//	public String uDept(@ModelAttribute("dept") @RequestBody Department dept, HttpSession session) {
//		departmentService.adddepartment(dept);
//		session.setAttribute("msgu", "Record updated successfully!");
//		return "redirect:/admin/departmentlist";
//	}
//
//	/*
//	 * =============================================================================
//	 * ====================
//	 */
//
//	@GetMapping("/add-patient-wise-report")
//	public String AddReportPatient(Model model) {
//		PatientReport patientReport = new PatientReport();
//		model.addAttribute("prp", patientReport);
//		return "add-patient-wise-report";
//
//	}
//
//	@PostMapping("/saveReportPatient")
//	public String saveReportPatient(@ModelAttribute("prp") @RequestBody PatientReport patientReport,
//			HttpSession session) {
//		prService.addReportPatient(patientReport);
//		session.setAttribute("msg", "Record added successfully!");
//		return "redirect:/admin/preportlist";
//	}
//
//	@GetMapping("/preportlist")
//	public String getReport(@Param("keyword") String keyword, Model model) {
//		List<PatientReport> ptall = prService.getAllRep(keyword);
//		model.addAttribute("ptall", ptall);
//		model.addAttribute("keyword", keyword);
//		return "patient-wise-report";
//	}
//
//	@GetMapping("/deletedptr/{id}")
//	public String deleteRep(@PathVariable(value = "id") Long id, HttpSession session) {
//		prService.deleteRep(id);
//		session.setAttribute("msgd", "Record deleted successfully");
//		return "redirect:/admin/preportlist";
//	}
//
//	@GetMapping("/getpt/{id}")
//	public String getRep(@PathVariable(value = "id") Long id, Model model) {
//		model.addAttribute("prp", prService.getParticularRep(id));
//		return "edit-patient-wise-report";
//	}
//
//	@PostMapping("/uRPT")
//	public String uRPT(@ModelAttribute("prp") @RequestBody PatientReport patientReport, HttpSession session) {
//		prService.addRep(patientReport);
//		session.setAttribute("msgu", "Record updated successfully!");
//		return "redirect:/admin/preportlist";
//	}
//
// (07) Print excel report for all...!!
@GetMapping("/PrintPatientWiseExcelReport")
public void generateExcelReportDepartments1(HttpServletResponse response) throws IOException {
	response.setContentType("application/octet-stream");

	String headerKey = "Content-Disposition";
	String headerValue = "attachment;filename=patient-wise.xls";

	response.setHeader(headerKey, headerValue);

	prService.generateExcel(response);
}
//
// (08) Print PDF Report for all...!!
@GetMapping("/PrintPatientWisePdfReport")
public void exportToPDF1(HttpServletResponse HttpServletResponse) throws DocumentException, IOException {
	HttpServletResponse.setContentType("application/pdf");

	DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
	String currentDateTime = dateFormatter.format(new Date());

	String headerKey = "Content-Disposition";
	String headerValue = "attachment; filename= patient-wise_" + currentDateTime + ".pdf";

	HttpServletResponse.setHeader(headerKey, headerValue);

	List<PatientReport> listPatientWise = prService.listAllPdf();

	PatientWisePdfExporter exporter = new PatientWisePdfExporter(listPatientWise);
	exporter.export(HttpServletResponse);
}
//
//	/*
//	 * =============================================================================
//	 * ====================
//	 */
//
//	@GetMapping("/add-doctor-wise-report")
//	public String AddReportDocter(Model model) {
//		DoctorReport reportDocter = new DoctorReport();
//		model.addAttribute("drp", reportDocter);
//		return ("/add-doctor-wise-report");
//
//	}
//
//	@PostMapping("/saveReportDocter")
//	public String saveReportDocter(@ModelAttribute("drp") @RequestBody DoctorReport doctorReport, HttpSession session)
//			throws IOException {
//		DoctorReport addReportWithCondition = drService.addReportDocter(doctorReport);
//		if (addReportWithCondition != null) {
//			session.setAttribute("msg", "Record added Successfully!....");
//		} else {
//			session.setAttribute("msga", "Something Wrong!...");
//		}
//
//		return "redirect:/admin/dreportlist";
//	}
//
//	@GetMapping("/dreportlist")
//	public String getDReport(@Param("keyword") String keyword, Model model) {
//		List<DoctorReport> dtall = drService.getAllRep(keyword);
//		model.addAttribute("dtall", dtall);
//		model.addAttribute("keyword", keyword);
//		return "doctor-wise-report";
//	}
//
//	@GetMapping("/deleteddt/{id}")
//	public String deleteDRep(@PathVariable(value = "id") Long id, HttpSession session) {
//		drService.deleteRep(id);
//		session.setAttribute("msgd", "Record deleted successfully");
//		return "redirect:/admin/dreportlist";
//	}
//
//	@GetMapping("/getdt/{id}")
//	public String getDRep(@PathVariable(value = "id") Long id, Model model) {
//		model.addAttribute("drp", drService.getParticularRep(id));
//		return "edit-doctor-wise-report";
//	}
//
//	@PostMapping("/uRDT")
//	public String uRDT(@ModelAttribute("drp") @RequestBody DoctorReport doctorReport, HttpSession session) {
//		drService.addRep(doctorReport);
//		session.setAttribute("msgu", "Record updated successfully!");
//		return "redirect:/admin/dreportlist";
//	}
//
//	@GetMapping("/filterByDate")
//	public String getData(@Param("startDate") String startDate, @Param("endDate") String endDate, Model model) {
//		List<DoctorReport> filteredData = drService.findByDateBetween(startDate, endDate);
//		model.addAttribute("filteredData", filteredData);
//		return "doctor-wise-report";
//	}
//
// (07) Print excel report for all...!!
@GetMapping("/PrintDoctorWiseExcelReport")
public void generateExcelReportDepartments11(HttpServletResponse response) throws IOException {
	response.setContentType("application/octet-stream");

	String headerKey = "Content-Disposition";
	String headerValue = "attachment;filename=doctor-wise.xls";

	response.setHeader(headerKey, headerValue);

	drService.generateExcel(response);
}
//
// (08) Print PDF Report for all...!!
@GetMapping("/PrintDoctorWisePdfReport")
public void exportToPDF11(HttpServletResponse HttpServletResponse) throws DocumentException, IOException {
	HttpServletResponse.setContentType("application/pdf");

	DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
	String currentDateTime = dateFormatter.format(new Date());

	String headerKey = "Content-Disposition";
	String headerValue = "attachment; filename= doctor-wise_" + currentDateTime + ".pdf";

	HttpServletResponse.setHeader(headerKey, headerValue);

	List<DoctorReport> listDoctorWise = drService.listAllPdf();

	DoctortWisePdfExporter exporter = new DoctortWisePdfExporter(listDoctorWise);
	exporter.export(HttpServletResponse);
}

}