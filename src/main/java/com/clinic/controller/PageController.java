package com.clinic.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class PageController {
    @GetMapping({"/", "/login-admin"}) public String adminLogin(){return "login-admin";}
    @GetMapping("/login-doctor") public String doctorLogin(){return "login-doctor";}
    @GetMapping("/login-patient") public String patientLogin(){return "login-patient";}
    @GetMapping("/admin") public String adminPortal(){return "admin";}
    @GetMapping("/patient") public String patientPortal(){return "patient";}
    @GetMapping("/doctor") public String doctorPortal(){return "doctor";}
}
