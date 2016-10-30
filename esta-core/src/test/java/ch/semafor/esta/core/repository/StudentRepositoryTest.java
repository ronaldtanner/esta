package ch.semafor.esta.core.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import ch.semafor.esta.core.domain.Student;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource(value="classpath:hibernate-test.properties")
@TestExecutionListeners({
    TransactionalTestExecutionListener.class, 
    DirtiesContextTestExecutionListener.class,
    DependencyInjectionTestExecutionListener.class, 
    DbUnitTestExecutionListener.class })
public class StudentRepositoryTest {

	@Autowired
	StudentRepository studentRepository;
		
	@Test
	public void testSave() {
		Student stud = new Student();
		stud.setFirstname("firstname");
	    stud.setLastname("lastname");
	    assertNull("empty pk", stud.getId());
	    stud = studentRepository.save(stud);
	    assertNotNull("generated pk", stud.getId());
	  }
	
	@Test@DatabaseSetup("/db-export.xml") 
	public void findall() {
		List<Student> students = studentRepository.findAll();
		assertEquals(2, students.size());
	}

}
