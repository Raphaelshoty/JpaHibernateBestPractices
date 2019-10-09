package com.spring.jpa.hibernate.app.repository;

import static org.junit.Assert.assertFalse;

import java.util.Objects;
import java.util.Set;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//import java.sql.Connection; // here i can see the isolation levels and choose between them 

import com.spring.jpa.hibernate.app.AppApplication;
import com.spring.jpa.hibernate.app.entity.Student;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppApplication.class)
public class StudentSpringDataRepositoryTest {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	StudentSpringDataRepository repository;
	
	@Autowired
	EntityManager em;
	
		
	@Test
	public void findByPartialName() {
		logger.info("Tests Running ! find by name");
		Set<Student> students = repository.findByPartialName("%"+"Lima"+"%");
		assertFalse(students.isEmpty());		
	}
	
	@Test
	public void findByCourseName() {
		logger.info("Tests Running ! find by Course Name");
		Set<Student> students = repository.findAllByCourseName("%"+"spring"+"%");
		assertFalse(students.isEmpty());		
	}
	
	@Test
	public void findByReviewId() {
		logger.info("Tests Running ! find by review ID");
		Student student = repository.findByReviewId(1l);
		assertFalse(Objects.isNull(student));		
	}
	
	@Test
	public void findByPassportId() {
		logger.info("Tests Running ! find by passport ID");
		Student student = repository.findByPassportId(1l);
		assertFalse(Objects.isNull(student));		
	}
	
	
	
	
	
	
		
	

	
	


	
	
	
	


}
