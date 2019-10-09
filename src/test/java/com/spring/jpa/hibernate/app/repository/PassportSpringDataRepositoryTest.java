package com.spring.jpa.hibernate.app.repository;

import static org.junit.Assert.assertFalse;

import java.util.Objects;

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
import com.spring.jpa.hibernate.app.entity.Passport;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppApplication.class)
public class PassportSpringDataRepositoryTest {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	PassportSpringDataRepository repository;
	
	@Autowired
	EntityManager em;
	
		
	@Test
	public void findByStudentName() {
		logger.info("Tests Running ! find by name");
		Passport passport = repository.findByStudentName("Raphael Rodrigues Lima");
		assertFalse(Objects.isNull(passport));		
	}
	
	
	
	
	
	
	
		
	

	
	


	
	
	
	


}
