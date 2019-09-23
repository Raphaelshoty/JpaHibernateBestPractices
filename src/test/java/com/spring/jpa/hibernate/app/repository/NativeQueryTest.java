package com.spring.jpa.hibernate.app.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.spring.jpa.hibernate.app.AppApplication;
import com.spring.jpa.hibernate.app.entity.Course;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppApplication.class)
public class NativeQueryTest {
	
	/// scenarios where native queries are a good option:
	// 1 when jpa or Hibernate does not support the actions that you need to perform
	// 2 when a bulk update/delete is needed, because if using jpa you need to get each row/register/entity and then do the update

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	EntityManager em;	
		
	@Test// native query using simple SQL syntax 
	public void NativeQueryTestSimple() { 
		Query query = em.createNativeQuery("SELECT * FROM COURSE", Course.class); 
		List<Course> resultList = query.getResultList();
		logger.info("SELECT * FROM COURSE -> {}", resultList );
	}
	
	@Test// native query using simple SQL syntax 
	public void NativeQueryTestWithParameter() { 
		Query query = em.createNativeQuery("SELECT * FROM COURSE WHERE ID = :id", Course.class);
		query.setParameter("id", 1L);
		Course result = (Course) query.getSingleResult();
		logger.info("SELECT * FROM COURSE -> {}", result );
	}
	
	@Test// native query using simple SQL syntax 	
	@Transactional
	public void NativeQueryUpdateTest() { 
		Query query = em.createNativeQuery("UPDATE COURSE SET last_Update = SYSDATE() WHERE last_Update <> SYSDATE() ", Course.class); 
		int qtRowsUpdated = query.executeUpdate();
		logger.info("Amount of updated rows -> {}", qtRowsUpdated );
	}


}
