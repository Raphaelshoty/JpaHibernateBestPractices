package com.spring.jpa.hibernate.app.repository;

import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.spring.jpa.hibernate.app.entity.Course;

//mark this repository as a Stereotype Repository, this way the repo is collected and inserted on SPRING 
//context
@Repository
@Transactional
public class CourseRepository  {
	//inject the entity manager
	@Autowired
	EntityManager em;
	
	public Course findById(Long id) {
		return em.find(Course.class, id);
	}
	
	public List<Course> findByName(String name) {
		String qryStr = "Select c From Course c Where UPPER(c.name) Like UPPER(:name) ORDER BY c.name ASC";		
		Query qry = em.createQuery(qryStr);
		qry.setParameter("name", "%"+name+"%");
		return (List<Course>) qry.getResultList();
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
	
}
