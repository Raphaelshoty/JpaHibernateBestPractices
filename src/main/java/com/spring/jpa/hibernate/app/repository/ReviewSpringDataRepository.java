package com.spring.jpa.hibernate.app.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.jpa.hibernate.app.entity.Review;

public interface ReviewSpringDataRepository extends JpaRepository<Review, Long>{
	
	
	Set<Review> findByRating(String rating);
	
	@Query("Select r From Review r Left Join Fetch r.course c Where c.id = :id")
	Set<Review> findByCourseId(Long id);
	
	@Query("Select r From Review r Join Fetch r.student s Where lower(s.name) = lower(:studentName)")
	Set<Review> findByStudentName(String studentName);
	
	@Query("Select r From Review r Where lower(r.description) like(%"+":description"+"%)")
	Set<Review> findByDescriptionContent(String description);

}
