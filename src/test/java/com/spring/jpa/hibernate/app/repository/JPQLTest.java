package com.spring.jpa.hibernate.app.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.spring.jpa.hibernate.app.AppApplication;
import com.spring.jpa.hibernate.app.entity.Course;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppApplication.class)
public class JPQLTest {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	EntityManager em;	
		
	@Test	
	public void JpqlNamedQueryTest() {		
		List courses =  em.createNamedQuery("find_all").getResultList();
		logger.info("Select c From Course c -> {}", courses );
	}
	
	@Test	
	public void JpqlTestWithExpectedResult() {
		Query qry = em.createQuery("Select c from Course c");
		List<Course> results = qry.getResultList();
		logger.info("Select c From Course c -> {}", results );
	}
	
	@Test	
	public void JpqlTestTyped() {
		TypedQuery<Course> query = em.createQuery("Select c from Course c", Course.class);
		List<Course> resultsList = query.getResultList();
		logger.info("Select c From Course c -> {}", resultsList );			
	}
	
	@Test	
	public void JpqlTestWithParameters() {
		String courseName = "updated";
		TypedQuery<Course> query = em.createQuery("Select c from Course c Where Upper(c.name) like Upper(:name)", Course.class);
		query.setParameter("name", "%"+courseName+"%");
		List<Course> resultsList = query.getResultList();
		logger.info("Select c From Course c -> {}", resultsList );			
	}
	
	



}
