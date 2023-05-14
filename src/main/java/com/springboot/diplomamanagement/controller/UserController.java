package com.springboot.diplomamanagement.controller;

import com.springboot.diplomamanagement.model.Student;
import com.springboot.diplomamanagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class UserController {

    @Autowired
    StudentService studentService;

    @GetMapping("/student/profile")
    public String retrieveProfile(Model theModel,Principal principal) {
        theModel.addAttribute("student",studentService.retrieveProfile(principal.getName()));
        return "student/profile";
    }

    @GetMapping("/student/applyThesis/{id}")
    public String applyToSubject(Model theModel, Principal principal, @PathVariable("id") int id,RedirectAttributes redirectAttributes) {
        try {
            studentService.applyToSubject(principal.getName(), id);
        }
        catch(Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",e.getMessage());
        }
        return "redirect:/student/applications";
    }

    @RequestMapping("/student/applications")
    public String getUserApplications(Principal principal, Model model){
		model.addAttribute("applications",studentService.listStudentApplications(principal.getName()));
        return "student/applications";
    }

    @RequestMapping("/student/listSubjects")
    public String listSubjects(Model model){
        model.addAttribute("subjectList",studentService.listStudentSubjects());
        return "student/listSubjects";
    }

    @PostMapping("/saveStudent")
    public String saveProfile(@ModelAttribute("student") Student student, Model theModel, RedirectAttributes redirectAttributes) {
        // save the employee
        studentService.saveProfile(student);
        redirectAttributes.addFlashAttribute("successMessage","Signed Up Successfully");
        redirectAttributes.addFlashAttribute("success",true);

        return "redirect:/login";
    }
}