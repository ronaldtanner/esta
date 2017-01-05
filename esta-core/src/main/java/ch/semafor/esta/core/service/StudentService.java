package ch.semafor.esta.core.service;

import ch.semafor.esta.core.domain.Student;

import java.util.List;

public interface StudentService {
	public Student save( Student student );

    List<Student> findAll();

    Student findOne(Long id);
}
