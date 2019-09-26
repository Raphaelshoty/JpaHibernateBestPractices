package com.spring.jpa.hibernate.app.repository;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.spring.jpa.hibernate.app.entity.Course;
import com.spring.jpa.hibernate.app.entity.Review;
import com.spring.jpa.hibernate.app.entity.Student;

//mark this repository as a Stereotype Repository, this way the repo is collected and inserted on SPRING 
//context
@Repository
@Transactional
public class CourseRepository  {
	//inject the entity manager
	@Autowired
	EntityManager em;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	public Set<Course> Course_find_all() {
		logger.info("Tests Running !");
		TypedQuery<Course> query = em.createNamedQuery("find_all", Course.class);
		return query.getResultList().stream().distinct().sorted(Comparator.comparing(Course::getName)).collect(Collectors.toSet());		 
	}
	
	public Course findById(Long id) {
		return em.find(Course.class, id);
	}
	
	public List<Course> findByName(String name) {
		String qryStr = "Select c From Course c Where UPPER(c.name) Like UPPER(:name) ORDER BY c.name ASC";		
		Query qry = em.createQuery(qryStr);
		qry.setParameter("name", "%"+name+"%");
		return  (List<Course>) qry.getResultList().stream().distinct().collect(Collectors.toList());
	}
	
	public Course save(Course course) {
		if(Objects.isNull( course.getId() ) ) {
			em.persist(course);
		}else {
			em.merge(course);
		}
		return course;
	}
	
	public void deleteById(Long id) {
		 Course course = em.find(Course.class, id);
		 em.remove(course);
	}
	
	public void deleteEntity(Course course) {
		em.remove(course);
	}
	
	
	public void playWithEntityManager() {
//		Course course = new Course("Web Services in 100 steps");
//		em.persist(course);
		
//		// this will fire an action on em to persist the mofications on database after it close its connection to database
//		course.setName("Web Services in 100 steps - UPDATED"); 
		
//		// the method is going to be finished with a non persisted modification on a object managed by the EM
//		// so the EM persist it after it closes the database connection, all of it its because the annotation
//		// @Transactional on top of the repository class
//		
//		Course course1 = new Course("Angular in 100 steps");
//		em.persist(course1);
		
//		// this will force the em to persist those modifications on database 
//		em.flush(); 
		
//		// this em.detach(entity) will tell the em to stop tracking the entity, so its modifications will not be persisted
//		// the em.detach is the same as em.clear() -> this will clear everything that the em is managing 
//		// the em.detach(entity) detach only one the em.clear() detach all that were being managed by it
//		//em.clear();		
//		
//		course1.setName("Angular in 100 steps - UPDATED");
//		
//		// that action tell to the em to discard all non flushed modifications made on a entity
//		em.refresh(course1);
//		em.flush();
		
		Course course1 = new Course("Web Services in 100 steps");
		em.persist(course1);
		
		Course course2 = findById(2l);
		// remember that this action, changing a property of a managed entity tells the entity manager to persist that change on database
		course2.setName("Spring Boot MASTERCLASS");		
		
	}

	public void addHardCodeReviewsForCourse() {		
		// getting the course 2 - Spring Boot 
		Course course = this.findById(2L);
		logger.info("Course reviews -> {} ", course.getReviews());
		
		// creating 2 reviews to it
		Set<Review> reviews = new HashSet<>();
		reviews.add(new Review("Amazing course and amazing instructor", "5"));
		reviews.add(new Review("Very good explanations and examples", "4,8"));
		
		// for each review add it to the course set of reviews
		for (Review review : reviews) {
			review.setCourse(course);
			// at this moment im persisting the review with course reference, since the review is the owner of the relationship just it needs to be persisted.
			em.persist(review);
			course.addtReview(review);
		}
		// this comment code below is to remember that in this transactional context only the entity witch is being managed by the em at the end of the method is going to be persisted without telling the em to do it explicitly 
		//course.setName("Spring boot - updated");
		logger.info("course plus 2 reviews -> {}", course.getReviews());		
		
	}
	
	// this is a chopped and parameterized  version of the method above
	public void addReviewsForCourse(Long courseId, Set<Review> reviews) {		
		// getting the course  
		Course course = this.findById(courseId);		
		// for each review add it to the course set of reviews
		for (Review review : reviews) {
			review.setCourse(course);
			em.persist(review);
			course.addtReview(review);
		}	
		
	}
	
	public void insertCourseAndStudent(Course course, Student student) {
		
		em.persist(course);
		em.persist(student);
		
		course.setStudent(student);
		student.setCourse(course);
		
		em.persist(student); // remember that in those situations(manyToMany) on last persist action persist the owning side of the relationship, the side which does not have the mappedBy
		
		
	}
	
}
