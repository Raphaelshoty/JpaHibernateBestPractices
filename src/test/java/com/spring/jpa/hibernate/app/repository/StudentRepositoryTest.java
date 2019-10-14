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
import com.spring.jpa.hibernate.app.entity.Address;
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
		// pass.getStudent().setName("Raphael Lima");
		// em.flush();
		//	latter on i discovered that the behavior above is due to the em, the em is managing only the the passport not the entity related to it, on this case the Student.	
	}
	
	@Test
	@Transactional // remember that the @transactional makes possible lazy relationship to be fetched without lazyInitializedException
	public void retrieveDataFromCourseAndStudent() {
		logger.info("Tests Running !");
		Student stu = em.find(Student.class, 1L);
		logger.info("Student -> {}",stu);
		logger.info("Student courses-> {}",stu.getCourses());
				
	}
	
	@Test
	@Transactional
	public void setAddresDetails(){
		Student stu = studentRepo.findById(1L);
		stu.setAddress(new Address("Eufrásia Augusta de Jesus", 43));
		em.flush(); // ensuring that this new address will persisted on database 
		logger.info("Student {} lives in {}", stu.getName(), stu.getAddress());
	}
	
	@Test
	@Transactional
	public void setStudentAndAddress(){
		Student stu = new Student("Elias");
		stu.setAddress(new Address("Almerinda Costa Ribeiro", 25));
		studentRepo.save(stu);
		em.flush(); // ensuring that this new address will persisted on database 
		logger.info("Student {} lives in {}", studentRepo.findById(6l).getName(), studentRepo.findById(6l).getAddress()); // just to notice that in this step those data will not be searched on database
		// because those data are already on first level cache.
	}
	
	
	
	

}
