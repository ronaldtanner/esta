package ch.semafor.esta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ch.semafor.esta.core.domain.Student;
import ch.semafor.esta.core.repository.StudentRepository;

@SpringBootApplication // same as @Configuration @EnableAutoConfiguration
//@ComponentScan
public class App implements CommandLineRunner {
	@Autowired
	StudentRepository studentRepository;

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
	
	@Override
	public void run(String... args) {
	    Student stud = new Student();
	    stud.setFirstname("firstname");
	    stud.setLastname("lastname");
	    
	    stud = studentRepository.save( stud );
	    System.out.println("Student " + stud.getId() + " saved.");
	}
}
