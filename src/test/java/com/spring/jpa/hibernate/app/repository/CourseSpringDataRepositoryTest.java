package com.spring.jpa.hibernate.app.repository;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

//import java.sql.Connection; // here i can see the isolation levels and choose between them 

import com.spring.jpa.hibernate.app.AppApplication;
import com.spring.jpa.hibernate.app.entity.Course;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppApplication.class)
public class CourseSpringDataRepositoryTest {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	CourseSpringDataRepository repository;
	
	@Autowired
	EntityManager em;
	
		
	@Test
	public void Course_findById() {
		logger.info("Tests Running ! find by id");
		Optional<Course> optionalCourse = repository.findById(1L);
		logger.info("{}",optionalCourse.isPresent()); 		
		assertEquals("Spring-Jpa-Hibernate", optionalCourse.get().getName()); // since the findById returns an optional i have to use .get() before the .getName()		
	}
	
	@Test
	@DirtiesContext
	public void save() {
		logger.info("Tests Running ! save/merge");
		repository.save(new Course("MicrOservices with spring boot"));		
		assertEquals("MicrOservices with spring boot", repository.findById(5l).get().getName());		
	}
	
	@Test	
	public void findAll() {
		logger.info("Tests Running ! find all");
		List<Course> courses = repository.findAll();
		logger.info("courses ->{}", courses);
		List<Long> ids = new ArrayList<>();
		ids.add(1l);
		ids.add(2l);
		ids.add(3l);
		ids.add(4l);
		assertEquals(ids, courses.stream().map(Course::getId).collect(Collectors.toList()));
	}
	
	@Test	
	public void findAllWithSort() {
		logger.info("Tests Running ! find all with sort");
		//Sort sort1 = new Sort(Direction.DESC, "name");
		//Sort sort = new Sort(Direction.DESC, "id").and(sort1); // its a sort like in where clause in SQL queries 
		List orders;		
		Sort sort = new Sort(Direction.DESC, "id"); // its a sort like in where clause in SQL queries 
		List<Course> courses = repository.findAll(sort); // here i apply the sort parameter
		logger.info("Sorted courses ->{}", courses);
		List<Long> ids = new ArrayList<>();
		ids.add(4l);
		ids.add(3l);
		ids.add(2l);
		ids.add(1l);		
		assertEquals(ids, courses.stream().map(Course::getId).collect(Collectors.toList()));
	}
	
	@Test	
	public void findAllWithPagination() {
		logger.info("Tests Running ! Pagination");
		PageRequest pageReq = PageRequest.of(0, 2);
		Page<Course> firstPage = repository.findAll(pageReq);
		logger.info("First page of results ->{}", firstPage.getContent());
		logger.info("Second page of results ->{}", firstPage.nextPageable());
		assertEquals(2, firstPage.nextPageable().getPageSize());
	}
		
	

	
	


	
	
	
	


}
