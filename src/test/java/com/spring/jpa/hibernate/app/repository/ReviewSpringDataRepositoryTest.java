package com.spring.jpa.hibernate.app.repository;

import static org.junit.Assert.assertTrue;

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
import org.springframework.transaction.annotation.Transactional;

//import java.sql.Connection; // here i can see the isolation levels and choose between them 

import com.spring.jpa.hibernate.app.AppApplication;
import com.spring.jpa.hibernate.app.entity.Course;
import com.spring.jpa.hibernate.app.entity.Review;
import com.spring.jpa.hibernate.app.entity.ReviewRating;
import com.spring.jpa.hibernate.app.entity.Student;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppApplication.class)
public class ReviewSpringDataRepositoryTest {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	ReviewSpringDataRepository repository;
	
	@Autowired
	ReviewRepository repo;
	
	@Autowired
	EntityManager em;
	
		
	@Test
	public void findByRating() {
		logger.info("Tests Running ! find by Rating");
		Set<Review> rev = repository.findByRating("5");
		assertTrue(Objects.nonNull(rev));		
	}
	
	@Test
	public void findByCourseId() {
		logger.info("Tests Running ! find by course id");
		Set<Review> rev = repository.findByCourseId(1L);
		assertTrue(Objects.nonNull(rev));		
	}
	
	@Test
	public void findByStudentName() {
		logger.info("Tests Running ! find by studentName");
		Set<Review> rev = repository.findByStudentName("Raphael Rodrigues Lima");
		assertTrue(Objects.nonNull(rev));		
	}
	
	@Test
	public void findByDescriptionContent() {
		logger.info("Tests Running ! find by studentName");
		Set<Review> rev = repository.findByDescriptionContent("%"+"COOL"+"%");
		assertTrue(Objects.nonNull(rev));		
	}
	
	@Test
	@Transactional
	public void SaveReviewWithEnumRating() {
		logger.info("Tests Running ! Save Review With enum as rating");
		
		Student stu = new Student("Marcos");
		Course course = em.find(Course.class, 1L);
		stu.setCourse(course);
		em.persist(stu);
		
		Review rev = new Review("Excelent course and excelent instructor", ReviewRating.FIVE);
		rev.setCourse(course);
		rev.setStudent(stu);
		
		repository.saveAndFlush(rev);		
		
	}
	
	
	
	
	
	
	
	
	
		
	

	
	


	
	
	
	


}
