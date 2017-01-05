package ch.semafor.esta.core;

import ch.semafor.esta.core.domain.Student;
import ch.semafor.esta.core.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.GregorianCalendar;

/**
 * Created by tar on 25.12.16.
 */
@Component
public class DatabaseLoader implements CommandLineRunner{
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public void run(String ... strings) throws Exception{
/*
    this.studentRepository.save(new Student(
                    "Walter", "White", new GregorianCalendar(1956,4,3).getTime()));
    this.studentRepository.save(new Student(
                "Jesse", "Pinkman", new GregorianCalendar(1976,6,29).getTime()));
    this.studentRepository.save(new Student(
                "Hank", "Schrader", new GregorianCalendar(1964,8,2).getTime()));
*/
        this.studentRepository.save(new Student(
                "Jakob", "Lanz", new GregorianCalendar(1993,3,9).getTime()));
        this.studentRepository.save(new Student(
                "Peter", "Meier", new GregorianCalendar(1986,6,29).getTime()));
        this.studentRepository.save(new Student(
                "Stefan", "Thommen", new GregorianCalendar(1991,2,23).getTime()));
        this.studentRepository.save(new Student(
                "Susanne", "Huber", new GregorianCalendar(1989,8,2).getTime()));
    }
}
