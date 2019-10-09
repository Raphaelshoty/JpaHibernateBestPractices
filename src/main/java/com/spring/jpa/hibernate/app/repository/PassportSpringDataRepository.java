package com.spring.jpa.hibernate.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.jpa.hibernate.app.entity.Passport;

public interface PassportSpringDataRepository extends JpaRepository<Passport, Long>{

	
	Passport findByNumber(String number);
	
	@Query("Select p From Passport p Join Fetch p.student s Where lower(s.name) = lower(:name)")
	Passport findByStudentName(String name);
	
}
