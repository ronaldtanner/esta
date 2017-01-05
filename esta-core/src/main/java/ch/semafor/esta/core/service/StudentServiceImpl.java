package ch.semafor.esta.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.semafor.esta.core.domain.Student;
import ch.semafor.esta.core.repository.StudentRepository;

import java.util.List;

@Service("studentService")
@Transactional
public class StudentServiceImpl implements StudentService{

	@Autowired
	StudentRepository studentRepository;
	
	@Override
	public Student save(Student student) {
		return studentRepository.save(student);
	}

	@Override
	public Student findOne(Long id){
		return studentRepository.findOne(id);
	}
	@Override
	public List<Student> findAll(){
		return studentRepository.findAll();
	}
}
