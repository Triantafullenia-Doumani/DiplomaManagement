package com.springboot.diplomamanagement.controller;

import com.springboot.diplomamanagement.model.Professor;
import com.springboot.diplomamanagement.model.Role;
import com.springboot.diplomamanagement.model.Student;
import com.springboot.diplomamanagement.model.User;
import com.springboot.diplomamanagement.service.ProfessorService;
import com.springboot.diplomamanagement.service.StudentService;
import com.springboot.diplomamanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController { 
	
    @Autowired
    UserService userService;

    @Autowired
    ProfessorService professorService;

    @Autowired
    StudentService studentService;

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new User());
        return "auth/signup";
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("user",new User());
        return "auth/login";
    }


    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("user",new User());
        return "auth/login";
    }

    @PostMapping("/save")
    public String registerUser(@ModelAttribute("user") User user, Model model, RedirectAttributes redirectAttributes){
       
        if(userService.isUserPresent(user)){
            redirectAttributes.addFlashAttribute("errorFound",true);
            redirectAttributes.addFlashAttribute("errorMessage", "User already registered with this username!");

            return "redirect:/register";
        }

        userService.saveUser(user);
        User savedUser= userService.findByUserName(user.getUsername()).get();

        if(user.getRole().toString().toUpperCase().equals(Role.PROFESSOR.toString().toUpperCase())) {
            Professor professor = new Professor();
            professor.setUser(savedUser);
            model.addAttribute("professor", professor);
            return "auth/professorSignup";
        }
        else {
            Student student = new Student();
            student.setUser(savedUser);
            model.addAttribute("student", student);
            return "auth/studentSignup";
        }

    }

}