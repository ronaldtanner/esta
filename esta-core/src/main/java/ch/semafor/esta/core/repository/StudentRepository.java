package ch.semafor.esta.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.semafor.esta.core.domain.Student;

public interface StudentRepository extends JpaRepository<Student, Long>{
}
