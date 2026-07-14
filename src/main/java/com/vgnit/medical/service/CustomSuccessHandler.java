package com.vgnit.medical.service;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        var authorities = authentication.getAuthorities();
        String role = authorities.stream()
                .map(r -> r.getAuthority())
                .findFirst()
                .orElse("");

        // All paths are ABSOLUTE (leading slash) and the role strings match
        // exactly what the registration form saves.
        switch (role) {
            case "ADMIN":
                response.sendRedirect("/admin/admin-page");
                break;
            case "USER":
                response.sendRedirect("/user/user-page");
                break;
            case "DOCTOR":
                response.sendRedirect("/doctor/doctor-page");
                break;
            case "PATIENT":
                response.sendRedirect("/patient/patient-page");
                break;
            case "STAFF":
                response.sendRedirect("/staff/staff-page");
                break;
            case "NURSE":
                response.sendRedirect("/nurse/nurse-page");
                break;
            case "ACCOUNTS":
                response.sendRedirect("/accounts/accounts-page");
                break;
            case "HR":
                response.sendRedirect("/hr/hr-page");
                break;
            default:
                response.sendRedirect("/error");
                break;
        }
    }
}