package com.vgnit.medical.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.vgnit.medical.entity.User;
import com.vgnit.medical.repository.UserRepository;

/**
 * Handles the landing dashboards for every non-admin role.
 * ADMIN keeps its own landing page in AdminController (/admin/admin-page).
 *
 * Each method loads the currently logged-in user (username == email) and
 * puts it in the model as "user" so the templates can show the name.
 */
@Controller
public class DashboardController {

    @Autowired
    private UserRepository userRepository;

    private void addUser(Principal principal, Model model) {
        if (principal != null) {
            User user = userRepository.findByEmail(principal.getName());
            model.addAttribute("user", user);
        }
    }

    @GetMapping("/user/user-page")
    public String userPage(Principal principal, Model model) {
        addUser(principal, model);
        return "user-dashboard";
    }

    @GetMapping("/doctor/doctor-page")
    public String doctorPage(Principal principal, Model model) {
        addUser(principal, model);
        return "doctor-dashboard";
    }

    @GetMapping("/patient/patient-page")
    public String patientPage(Principal principal, Model model) {
        addUser(principal, model);
        return "patient-dashboard";
    }

    @GetMapping("/staff/staff-page")
    public String staffPage(Principal principal, Model model) {
        addUser(principal, model);
        return "staff-dashboard";
    }

    @GetMapping("/nurse/nurse-page")
    public String nursePage(Principal principal, Model model) {
        addUser(principal, model);
        return "nurse-dashboard";
    }

    @GetMapping("/accounts/accounts-page")
    public String accountsPage(Principal principal, Model model) {
        addUser(principal, model);
        return "accounts-dashboard";
    }

    @GetMapping("/hr/hr-page")
    public String hrPage(Principal principal, Model model) {
        addUser(principal, model);
        return "hr-dashboard";
    }
}