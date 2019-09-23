package com.spring.jpa.hibernate.app.repository;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.spring.jpa.hibernate.app.AppApplication;

import com.spring.jpa.hibernate.app.entity.Passport;
import com.spring.jpa.hibernate.app.entity.Student;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppApplication.class)
public class StudentRepositoryTest {
	
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private StudentRepository studentRepo;
	
	@Autowired
	EntityManager em;
	//(Hibernate) session(persistence context in hibernate terminology) and session factory
	//(JPA) EntityManager(jpa interface to persistence context) & persistence context(started at the start of the transaction begin and finished at it´s end)
	// transaction
	
	@Test		
	public void someDataBaseOperations() {
		studentRepo.someDataBaseOperations();		
	}
	
	@Test
	@Transactional
	//Any oneToOne relationship is eager fetch by default 
	//so if i Try to retrieve passport from student and the anotation is Lazy, i´m gonna get a lazyInitializeException 
	//this happens because the transaction is ended as soon the data needed is retrieved
	//when a transactional anotation is present like here, the fetch is lazy and this makes the data to be retrieved on demand
	public void retrieveStudentAndPassportId() {
		logger.info("Tests Running !");
		Student student = em.find(Student.class, 1L); 
		logger.info("Student info -> {}", student);
		logger.info("Student passport details - >{}", student.getPassport());
	}
	
	@Test
	@Transactional // remember that the @transactional makes possible lazy relationship to be fetched without lazyInitializedException
	public void retrievePassportAndRelatedStudent() {
		logger.info("Tests Running !");
		Passport pass = em.find(Passport.class, 1L);
		logger.info("Passport details -> {}",pass);
		logger.info("Student details -> {}", pass.getStudent());
		
		// during my tests i could notice that, on context of test, the only way to persist the modifications that i made on entities is forcing it by a flush() and for that i need a @Transactional for that
//		pass.getStudent().setName("Raphael Lima");
//		em.flush();
//		
	}
	
	

}
