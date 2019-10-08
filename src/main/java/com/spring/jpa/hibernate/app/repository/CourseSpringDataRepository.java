package com.spring.jpa.hibernate.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.jpa.hibernate.app.entity.Course;

public interface CourseSpringDataRepository extends JpaRepository<Course, Long> {
	
}
