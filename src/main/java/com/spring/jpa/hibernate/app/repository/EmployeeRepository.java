package com.spring.jpa.hibernate.app.repository;


import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.spring.jpa.hibernate.app.entity.Employee;

@Repository
@Transactional
public class EmployeeRepository {
	
	@Autowired
	private EntityManager em;
	
	// persist an employee
	public void insertEmployee(Employee emp) {
		em.persist(emp);
	}
	
	// retrieve all employee
	public Set<Employee> findAll(){
		TypedQuery<Employee> qry = em.createQuery("Select emp from Employee emp", Employee.class);
		return qry.getResultList().stream().sorted(Comparator.comparing(Employee::getName)).collect(Collectors.toSet());
	}

}
