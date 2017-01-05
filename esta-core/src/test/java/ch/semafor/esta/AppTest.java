package ch.semafor.esta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

import ch.semafor.esta.core.domain.Student;
import ch.semafor.esta.core.repository.StudentRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.GregorianCalendar;


@SpringBootApplication // same as @Configuration @EnableAutoConfiguration
//@ComponentScan
public class AppTest implements CommandLineRunner {
	@Autowired
	StudentRepository studentRepository;

	public static void main(String[] args) {
		SpringApplication.run(AppTest.class, args);
	}
	
	@Override
	public void run(String... args) {
		this.studentRepository.save(new Student(
				"Walter", "White", new GregorianCalendar(1956,4,3).getTime()));
		this.studentRepository.save(new Student(
				"Jesse", "Pinkman", new GregorianCalendar(1976,6,29).getTime()));
		this.studentRepository.save(new Student(
				"Hank", "Schrader", new GregorianCalendar(1964,8,2).getTime()));
//	    Student stud = new Student();
//	    stud.setFirstname("firstname");
//	    stud.setLastname("lastname");
	    
//	    stud = studentRepository.save( stud );
//	    System.out.println("Student " + stud.getId() + " saved.");
		for( Student s: this.studentRepository.findAll()){
		    System.out.println(s.getId() + " " + s.getBirthdate());
		}
	}
}
