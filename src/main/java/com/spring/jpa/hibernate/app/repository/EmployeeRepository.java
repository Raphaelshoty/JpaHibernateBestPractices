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
import com.spring.jpa.hibernate.app.entity.FullTimeEmployee;
import com.spring.jpa.hibernate.app.entity.PartTimeEmployee;

@Repository
@Transactional
public class EmployeeRepository {
	
	@Autowired
	private EntityManager em;
	
	// persist an employee
	public void insertEmployee(Employee emp) {
		em.persist(emp);
	}
	
	// retrieve all employee and it specialized types of employees 
	// note that method will not work using the @MappedSuperClass inheritance strategy because the Employee will no longer be an entity
	public Set<Employee> findAll(){
		TypedQuery<Employee> qry = em.createQuery("Select emp from Employee emp", Employee.class);
		return qry.getResultList().stream().sorted(Comparator.comparing(Employee::getName)).collect(Collectors.toSet());
	}
	
	// those methods above will work on the context of @MappedSuperClass inheritance strategy
	public Set<FullTimeEmployee>  findAllFullTimeEmployee(){
		TypedQuery<FullTimeEmployee> query = em.createQuery("Select fullTime From FullTimeEmployee fullTime", FullTimeEmployee.class);
		return query.getResultList().stream().sorted(Comparator.comparing(FullTimeEmployee::getName)).collect(Collectors.toSet());
	}
	
	public Set<PartTimeEmployee>  findAllPartTimeTimeEmployee(){
		TypedQuery<PartTimeEmployee> query = em.createQuery("Select partTime From PartTimeEmployee partTime", PartTimeEmployee.class);
		return query.getResultList().stream().sorted(Comparator.comparing(PartTimeEmployee::getName)).collect(Collectors.toSet());
	}

}
