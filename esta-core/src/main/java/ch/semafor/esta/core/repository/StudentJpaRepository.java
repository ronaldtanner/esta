package ch.semafor.esta.core.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import ch.semafor.esta.core.domain.Student;

public class StudentJpaRepository implements StudentRepository {

	EntityManager em;

	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@Override
	public Student save(Student student) {
		EntityTransaction tx = em.getTransaction();

		tx.begin();
		em.persist(student);
		tx.commit();
		return student;
	}

}
