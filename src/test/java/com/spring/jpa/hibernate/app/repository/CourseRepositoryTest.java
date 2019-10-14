package com.spring.jpa.hibernate.app.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

//import java.sql.Connection; // here i can see the isolation levels and choose between them 

import com.spring.jpa.hibernate.app.AppApplication;
import com.spring.jpa.hibernate.app.entity.Course;
import com.spring.jpa.hibernate.app.entity.Review;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppApplication.class)
public class CourseRepositoryTest {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	CourseRepository repository;
	
	@Autowired
	EntityManager em;
	
		
	@Test
	public void Course_findById() {
		logger.info("Tests Running !");		
		Course course = repository.findById(2l);			
		assertEquals("Spring Boot", course.getName());
		assertEquals(new Long(2), course.getId());
	}
	
	@Test
	@Transactional
	public void Course_findByIdFirstLevelCache() { // first level cache - when a single data is required more than once in a single transaction and its stored in cache
		// as a good use of the first level cache, the transaction should start at the service layer, so... all the calls below it could be stored on cache for further data calls.
		logger.info("Tests Running !");		
		Course course = repository.findById(2l); // since this query is the first one, this will fire a query over the database 	
		logger.info("First course retrieved ! {}",course);
		
		Course course1 = repository.findById(2l); //  when inside an transaction if the same data is required again, there´s no need to retrieve it again over the database, its already stored on first level cache
		logger.info("The same course retrieved again ! {}", course1);
		
		assertEquals("Spring Boot", course.getName()); 
		assertEquals("Spring Boot", course1.getName());
	}
		
	@Test
	@DirtiesContext // this way the data changed will return to its original state before the test
	@Transactional(isolation = Isolation.READ_UNCOMMITTED)  // the isolation level is set to specific method but it could be stablished to an specific repository
	public void Course_deleteById() {
		logger.info("Tests Running !");			
		repository.deleteById(2l);		
		assertNull(repository.findById(2l));
	}
	@Test
	@DirtiesContext // this way the data changed will return to its original state before the test	
	public void deleteById() {
		logger.info("Tests Running ! delete a course by its ID");			
		repository.deleteById(2l);		
		assertNull(repository.findById(2l));
	}
	
	@Test
	@DirtiesContext // this way the data changed will return to its original state before the test
	@javax.transaction.Transactional // better use for just one database - for multiple dadabases use @Transactional from Spring
	public void Course_deleteByIdUsingJpaTransactional() {
		logger.info("Tests Running !");			
		repository.deleteById(2l);		
		assertNull(repository.findById(2l));
	}
	

	@Test
	@DirtiesContext // this way the data changed will return to its original state before the test
	public void Course_save() {
		logger.info("Tests Running !");			
		Course course = new Course("test");
		repository.save(course);		
		assertEquals("test",repository.findById(5l).getName());
		
		course = repository.findById(5l);
		course.setName("Teste update");
		repository.save(course);
		assertEquals("Teste update", repository.findById(course.getId()).getName());
		
	}
	
	@Test
	@DirtiesContext // this way the data changed will return to its original state before the test
	public void playWithEntityManagerTest() {
		logger.info("Play with entity manager start!");	
		repository.playWithEntityManager();		
	}

	
	
	@Test
	@Transactional
	public void retrieveReviewsForCourse() {
		Course course = repository.findById(2L);
		logger.info("Reviews of the course -> {}",course.getReviews());
	}
	
	@Test
	@Transactional
	public void retrieveCourseForReview() {		
		Review rev = em.find(Review.class, 3L); 
		logger.info("Course from review -> {}", rev.getCourse());
	}
	
	@Test
	@Transactional
	public void retrieveCourseAndStudents() {		
		Course course = em.find(Course.class, 2L);
		logger.info("Course -> {} and students attending to it -> {}", course, course.getStudents());
	}


	
	
	
	


}
