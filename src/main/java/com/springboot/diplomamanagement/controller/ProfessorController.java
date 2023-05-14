package com.springboot.diplomamanagement.controller;

import com.springboot.diplomamanagement.model.Application;
import com.springboot.diplomamanagement.model.Thesis;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.springboot.diplomamanagement.model.Professor;
import com.springboot.diplomamanagement.model.Subject;
import com.springboot.diplomamanagement.service.ProfessorService;
import com.springboot.diplomamanagement.service.SubjectService;
import com.springboot.diplomamanagement.service.UserService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;


@Controller
public class ProfessorController {
	
	private ProfessorService professorService; 
	private SubjectService subjectService;
	private UserService userService;
	
	public ProfessorController(ProfessorService theProfessorService, SubjectService theSubjectService, UserService theUserService) {
		professorService = theProfessorService; 
		subjectService = theSubjectService;
		userService = theUserService;
	}

	@GetMapping("/professor/home")
	public String getProfessorMainMenu() {
		return "professor/home";
	}

	@GetMapping("/professor/subjects")
	public String listProfessorSubjects(Principal principal,Model model) {
		model.addAttribute("subjects",professorService.listProfessorSubjects(principal.getName()));
		return "professor/subjects";
	}

	@GetMapping("/professor/profile")
	public String retrieveProfile(Model theModel,Principal principal) {
		theModel.addAttribute("professor",professorService.retrieveProfile(principal.getName()));
		return "professor/profile";
	}
	
	@PostMapping("saveProfessor")
	public String saveProfile(@ModelAttribute("professor")  Professor professor, Model theModel, RedirectAttributes redirectAttributes) {
		// save the employee
		try {
			professorService.saveProfile(professor);
			redirectAttributes.addFlashAttribute("successMessage", "Signed Up Successfully");
			redirectAttributes.addFlashAttribute("success", true);

		}
		catch(Exception e){
			System.out.println(e.getMessage());
			throw e;
		}
		return "redirect:/login";
	}

	@GetMapping("/professor/addSubject")
	public String showSubjectForm(Model theModel) {
		theModel.addAttribute("subject", new Subject());
		return "professor/addsubject";
	}

	@GetMapping("/professor/thesis/delete/{id}")
	public String deleteThesis(@PathVariable("id") int id,Model theModel) {
		professorService.deleteSubject(id);
		return "redirect:/professor/thesis";
	}

	@GetMapping("/professor/subject/delete/{id}")
	public String deleteSubject(@PathVariable("id") int id, Model theModel) {
		professorService.deleteSubject(id);
		return "redirect:/professor/subjects";
	}

	@GetMapping("/professor/addThesis")
	public String showThesisForm(Model theModel,Principal principal) {
		theModel.addAttribute("thesis", new Thesis());
		theModel.addAttribute("subjects",professorService.listProfessorSubjects(principal.getName()));
		return "professor/addthesis";
	}

	@PostMapping("/addSubject")
	public String addSubject(@ModelAttribute("subject") Subject theSubject,Model theModel,Principal principal,RedirectAttributes redirectAttributes) {
		try{
			professorService.addSubject(principal.getName(),theSubject);
			return "redirect:/professor/subjects";
		}
		catch(Exception e){
			redirectAttributes.addFlashAttribute("error",true);
			redirectAttributes.addFlashAttribute("errorMessage",e.getMessage());
			return "redirect:/professor/addSubject";
		}

	}

	@PostMapping("/addThesis")
	public String addThesis(@ModelAttribute("thesis") Thesis thesis, Model theModel, Principal principal,RedirectAttributes redirectAttributes) {
		try {
			professorService.addThesis(principal.getName(), thesis);
			return "redirect:/professor/thesis";
		}
		catch(Exception e){
			redirectAttributes.addFlashAttribute("error",true);
			redirectAttributes.addFlashAttribute("errorMessage",e.getMessage());
			return "redirect:/professor/addThesis";
		}
	}

	@RequestMapping("/listApplications")
	public String listApplications(Integer kati,Model theModel) {
		return null;
	}


	@GetMapping("/assignSubject/{id}/{strategy}")
	public String assignSubject(Model theModel,@PathVariable("id") int subjectId,@PathVariable("strategy") String strategy) {
		professorService.assignSubject(strategy,subjectId);
		return "redirect:/professor/applications";
	}
	
	@RequestMapping("/professor/thesis")
	public String listProfessorTheses(Model theModel,Principal principal) {
		theModel.addAttribute("thesisList",professorService.listProfessorTheses(principal.getName()));
		return "professor/thesis";
	}


	@RequestMapping("/professor/applications/{id}")
	public String listApplications(Model theModel, @PathVariable(value = "id",required = true) Integer id,Principal principal) {
		theModel.addAttribute("applications", professorService.listApplications(principal.getName(), id));
		theModel.addAttribute("subjects",professorService.listProfessorSubjects(principal.getName()));
		theModel.addAttribute("id",id);
		return "professor/applications";
	}

	@RequestMapping("/professor/applications")
	public String listApplicationsAll(Model theModel ,Principal principal) {
		theModel.addAttribute("subjects",professorService.listProfessorSubjects(principal.getName()));
		return "professor/applications";
	}

	@RequestMapping("/professor/applications/accepted")
	public String listApplicationsAccepted(Model theModel ,Principal principal) {
		theModel.addAttribute("applications",professorService.listAcceptedApplications(principal.getName()));
		theModel.addAttribute("application",new Application());
		return "professor/acceptedApplications";
	}

	@PostMapping("/assignGrade")
	public String assignGrade(@ModelAttribute("application") Application application, Model theModel) {
			professorService.assignGrade(application);
			return "redirect:/professor/applications/accepted";
	}
}

