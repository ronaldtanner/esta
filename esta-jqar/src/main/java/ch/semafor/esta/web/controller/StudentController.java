package ch.semafor.esta.web.controller;

import ch.semafor.esta.core.domain.Student;
import ch.semafor.esta.core.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by tar on 25.12.16.
 */
@Controller
@RequestMapping(value = "/students")
public class StudentController {
    @Autowired
    StudentService studentService;

    @RequestMapping(value="/react")
    public String reactIndex(){
        return "react/students/view";
    }

    @RequestMapping(value="/angular")
    public String angularIndex(){
        return "angular/students/view";
    }


  @RequestMapping(method= RequestMethod.GET)
  public String getStudents(Model model) {
    model.addAttribute("student", new Student());
    model.addAttribute("students", studentService.findAll() );
    return "jquery/students/view";
  }

  @RequestMapping(value = "/{id}", method=RequestMethod.GET)
  public String getStudent(@PathVariable Long id, Model model) {
    model.addAttribute("student", studentService.findOne(id));
    model.addAttribute("students", studentService.findAll() );
    return "jquery/students/view";
  }

  /**
   * Handles the submission of the save form, either creating a new
   * or modifying an existing instance
   */
   @RequestMapping(method=RequestMethod.POST)
   public String saveStudent(@ModelAttribute Student student,
                             Model model) {
     studentService.save(student);
     return "redirect:/students";
   }

}
