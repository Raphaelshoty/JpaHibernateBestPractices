package com.spring.jpa.hibernate.app.repository;

import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.spring.jpa.hibernate.app.entity.Passport;
import com.spring.jpa.hibernate.app.entity.Student;

@Repository
@Transactional
public class StudentRepository {
	
	@Autowired
	EntityManager em;
	
	public void someDataBaseOperations() {		
		// op1 - retrieve student		
		Student student = em.find(Student.class, 1L);		
		//op2 - retrieve passport
		Passport pass = student.getPassport();		
		//op3 - update passport
		pass.setNumber("RRL512905");		
		//op4 - update student
		student.setName("Raphael R. Lima");			
	}
	
	public Student findById(Long id) {
		return em.find(Student.class, id);
	}
	
	public List<Student> findByName(String name){
		TypedQuery<Student> qry = em.createQuery("SELECT s FROM Student s WHERE UPPER(s.name) LIKE UPPER(:name) ORDER BY s.name ASC", Student.class);
		qry.setParameter("name", "%"+name+"%");
		return qry.getResultList();
	}
	
	public Student save(Student student) {
		Long id = student.getId();
		if(Objects.isNull(id)) {
			em.persist(student);
		}else {
			em.merge(student);
		}
		return student;
	}
	
	public void saveWithPassport() { // here in this context, first i created an passport which is necessary to built the student with an passport_id
		// so in order to have an passport_id i have to save it on database first and them save the student.
		//otherwise a transient entity exception will be raised 
		Passport pass = new Passport("KFT25101988");
		em.persist(pass);
		Student student = new Student("Mario", pass);		
		em.persist(student);		
	}
	
	public void deleteById(Long id) {
		Student student = this.findById(id);
		em.remove(student);
	}
	
	public void deleteEntity(Student student) {
		em.remove(student);
	}

}
