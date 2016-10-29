package ch.semafor.esta;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import ch.semafor.esta.core.domain.Student;
import ch.semafor.esta.core.repository.StudentJpaRepository;

public class App {

	  public static void main(String[] args) {
	    EntityManagerFactory emf = 
	       Persistence.createEntityManagerFactory("esta-pu");
	    StudentJpaRepository studentRepository = 
	       new StudentJpaRepository();
	    studentRepository.setEntityManager(
	       emf.createEntityManager());

	    Student stud = new Student();
	    stud.setFirstname("firstname");
	    stud.setLastname("lastname");
	    
	    stud = studentRepository.save( stud );
	    System.out.println("Student " + stud.getId() + " saved.");
	  }
	}
