package ch.semafor.esta.core.service;

import org.springframework.transaction.annotation.Transactional;

import ch.semafor.esta.core.domain.Student;
import ch.semafor.esta.core.repository.StudentRepository;

@Transactional
public class StudentServiceImpl implements StudentService{

	StudentRepository studentRepository;
	
	@Override
	public Student save(Student student) {
		return studentRepository.save(student);
	}

}
