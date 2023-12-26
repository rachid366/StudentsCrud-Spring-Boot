package com.rachid.Crud.controller;

import com.rachid.Crud.model.Student;
import com.rachid.Crud.repos.StudentRepo;
import com.rachid.Crud.service.PdfService;
import com.rachid.Crud.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private PdfService pdfService;

//    @RequestMapping("/")
//    public String index(Model model){
//       model.addAttribute("students", studentService.getAll());
//        return "index";
//    }

    @RequestMapping("/")
    public String index(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "3") int size,
            Model model,
            @RequestParam(value = "keyWord", defaultValue = "") String keyWord
    ){
        Page<Student> studentPage = studentRepo.findByNameContains(keyWord, PageRequest.of(page, size));
        model.addAttribute("students", studentPage.getContent());
        model.addAttribute("pages", studentPage.getTotalPages());
        model.addAttribute("currentPages", page);
        model.addAttribute("keyWord", keyWord);
        return "index";
    }


    @RequestMapping("/new")
    public String newStudent(Model model){
        model.addAttribute("student", new Student());
        return "student-form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("student") Student student, BindingResult result){
       if (result.hasErrors()){
           return "student-form";
       }
        studentService.save(student);
        return "redirect:/";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model){
        Student student = studentService.get(id);
        model.addAttribute("student", student);
        return "student-form";
    }
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id){
        studentService.delete(id);
        return "redirect:/";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/403")
    public String accessDenied(){
        return "accessDenied";
    }

    @GetMapping("/download-pdf")
    public void downloadPdf(HttpServletResponse response){
        try {
            Path file = Paths.get(pdfService.generatePdf().getAbsolutePath());
            if(Files.exists(file)){
                response.setContentType("application/pdf");
                response.addHeader("Content-Disposition", "attachment; filename"+ file.getFileName());
                Files.copy(file, response.getOutputStream());
                response.getOutputStream().flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


