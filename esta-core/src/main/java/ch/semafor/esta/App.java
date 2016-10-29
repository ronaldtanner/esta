package ch.semafor.esta;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ch.semafor.esta.core.config.JpaConfig;
import ch.semafor.esta.core.domain.Student;
import ch.semafor.esta.core.repository.StudentRepository;

public class App {

	  public static void main(String[] args) {
		  ApplicationContext context =
				   new AnnotationConfigApplicationContext(JpaConfig.class);
				StudentRepository studentRepository = 
				   (StudentRepository) context.getBean("studentRepository");

	    Student stud = new Student();
	    stud.setFirstname("firstname");
	    stud.setLastname("lastname");
	    
	    stud = studentRepository.save( stud );
	    System.out.println("Student " + stud.getId() + " saved.");
	  }
	}
