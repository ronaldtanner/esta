package ch.semafor.esta.jsf.controller;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ch.semafor.esta.core.domain.Student;
import ch.semafor.esta.core.service.StudentService;

@Component
@Scope("request")
public class StudentController {
	@Autowired
	private StudentService studentService;
	
  private Student student = new Student();
  
  public List<Student> getStudents(){
	     return studentService.findAll();
	  }

  public Student getStudent() {
    return student;
  }
  public void setStudent(Student student) {
    this.student = student;
  }
  public String edit(Long id){
	  if(id == null){
		  student = new Student();
	  }
	  else {
		  student = studentService.findOne(id);
	  }
	  return "editStudent";
  }
  public String save(){
      	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Value submitted."));
      	studentService.save(student);
    return "listStudents";
  }
}
