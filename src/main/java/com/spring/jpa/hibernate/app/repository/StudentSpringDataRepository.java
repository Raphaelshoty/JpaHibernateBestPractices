package com.spring.jpa.hibernate.app.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.jpa.hibernate.app.entity.Student;

public interface StudentSpringDataRepository extends JpaRepository<Student, Long> {

	@Query("Select s From Student s Where lower(s.name) like lower(:name)")
	Set<Student> findByPartialName(String name);
	
	@Query("Select s From Student s Join Fetch s.courses c Where lower(c.name) like(:name) ")
	Set<Student> findAllByCourseName(String name);
	
	@Query("Select s From Student s Join Fetch s.reviews r Where r.id = :revId ")
	Student findByReviewId(Long revId);
	
	@Query("Select s from Student s Join Fetch s.passport p Where p.id = :id")
	Student findByPassportId(Long id);
	
}
