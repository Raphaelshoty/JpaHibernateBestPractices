package com.spring.jpa.hibernate.app.repository;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.spring.jpa.hibernate.app.entity.Course;
import com.spring.jpa.hibernate.app.entity.Review;

@Repository
@Transactional
public class ReviewRepository {

	@Autowired
	private EntityManager em;
	
	public Review findById(Long id) {
		return em.find(Review.class, id);
	}
	
	public Set<Review> findByDescription(String description){
		TypedQuery<Review> query = em.createQuery("Select r From Review r Where Upper(r.description) Like Upper(:description)", Review.class );
		query.setParameter("description", "%"+description+"%");
		return query.getResultList().stream().distinct().sorted().collect(Collectors.toSet());
	}
	
	public Review save(Review rev) {
		Long id = rev.getId();
		if(Objects.isNull(id)) {
			em.persist(rev);
		}else {
			em.merge(rev);
		}
		return rev;
	}
	
	public void saveWithCourse(Course course, Review rev) {
		em.persist(course);
		rev.setCourse(course);
		em.persist(rev);		
	}
	
	public void deleteById(Long id) {
		Review rev = em.find(Review.class, id);
		em.remove(rev);
	}
	
	public void deleteEntity(Review rev) {
		em.remove(rev);
	}
	
}
