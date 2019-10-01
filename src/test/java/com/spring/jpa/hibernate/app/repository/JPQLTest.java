package com.spring.jpa.hibernate.app.repository;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hamcrest.collection.IsEmptyIterable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.spring.jpa.hibernate.app.AppApplication;
import com.spring.jpa.hibernate.app.entity.Course;
import com.spring.jpa.hibernate.app.entity.Review;
import com.spring.jpa.hibernate.app.entity.Student;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppApplication.class)
public class JPQLTest {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	EntityManager em;	
		
	@Test	
	public void JpqlNamedQueryTest() {		
		List courses =  em.createNamedQuery("find_all").getResultList();
		logger.info("Select c From Course c -> {}", courses );
	}
	
	@Test	
	public void JpqlTestWithExpectedResult() {
		Query qry = em.createQuery("Select c from Course c");
		List<Course> results = qry.getResultList();
		logger.info("Select c From Course c -> {}", results );
	}
	
	@Test	
	public void JpqlTestTyped() {
		TypedQuery<Course> query = em.createQuery("Select c from Course c", Course.class);
		List<Course> resultsList = query.getResultList();
		logger.info("Select c From Course c -> {}", resultsList );			
	}
	
	@Test	
	public void JpqlTestWithParameters() {
		String courseName = "updated";
		TypedQuery<Course> query = em.createQuery("Select c from Course c Where Upper(c.name) like Upper(:name)", Course.class);
		query.setParameter("name", "%"+courseName+"%");
		List<Course> resultsList = query.getResultList();
		logger.info("Select c From Course c -> {}", resultsList );			
	}
	
	@Test
	public void jpqlCoursesWithoutStudents() {
		TypedQuery<Course> qry = em.createQuery("Select c From Course c Where c.students is empty ", Course.class);
		logger.info("Courses with no students registered in -> {} ",qry.getResultList());
	}
	
	@Test
	public void jpqlCoursesWithTwoOrMoreStudents() {
		TypedQuery<Course> qry = em.createQuery("Select c From Course c Where size(c.students) >= 2 ", Course.class);
		logger.info("Courses with two or more students registered in -> {} ",qry.getResultList());
	}
	
	@Test
	public void jpqlCoursesOrderedByStudents() {
		TypedQuery<Course> qry = em.createQuery(" Select c From Course c Order by size(c.students) ", Course.class);
		logger.info("Courses Ordered by amount of students -> {} ",qry.getResultList());
	}

	@Test
	public void findStudentByPassport() {
		TypedQuery<Student> qry = em.createQuery("Select s From Student s Where Lower(s.passport.number) Like Lower(:passportNumber) ", Student.class);
		qry.setParameter("passportNumber", "%"+"88"+"%");
		logger.info("Students by passport-> {}", qry.getResultList()
				.parallelStream()
				.distinct()
				.sorted(Comparator.comparing(Student::getName))
				.collect(Collectors.toList()));
	}
	
	// Join -> Select c, s From Course c Join c.students s // Join its like a inner join
	// left join -> Select c, s From Course c Left Join c.students s
	// cross join -> SELECT c, s From Course c, Student s // this results in something like that -> 3 and 4 -> 3*4 = 12 rows
	
	@Test
	public void join() {
		Query qry = em.createQuery("Select c, s From Course c Join c.students s"); // when doing a query that results more than one entity, pay attention to the fact that an array will be the result of it query
		List<Object[]> results  = qry.getResultList();
		logger.info("Join between Course and Students -> {}", results.size());
		results.stream().forEach(res ->{
			logger.info("Course-> {} - Student-> {}", res[0], res[1]);
		});
	}
	
	@Test
	public void leftJoin() {
		Query qry = em.createQuery("Select c, s From Course c Left Join c.students s"); // when doing a query that results more than one entity, pay attention to the fact that an array will be the result of it query
		List<Object[]> results  = qry.getResultList();
		logger.info("Left Join between Course and Students -> {}", results.size());
		results.stream().forEach(res ->{
			logger.info("Course-> {} - Student-> {}", res[0], res[1]);
		});
	}
	
	@Test 
	public void leftJoinFetch() {
		Query qry = em.createQuery("Select s, COUNT(r) as reviews From Student s Left Join s.reviews r Group By s"); // when doing a query that results more than one entity, pay attention to the fact that an array will be the result of it query
		List<Object[]> results  = qry.getResultList();
		logger.info("Left Join between Student and Review -> {}", results.size());
		results.stream().forEach(res ->{
			logger.info("Student-> {} - Review-> {}", res[0], res[1]);
		});
	}
	
	@Test 
	public void leftJoinFetchStudentReviews() {
		Query qry = em.createQuery("Select s, r From Student s Left Join FETCH s.reviews r"); // when doing a query that results more than one entity, pay attention to the fact that an array will be the result of it query
		List<Student> students  = (List<Student>) qry.getResultList().stream().distinct().collect(Collectors.toList());		
		students.stream().forEach(student ->{
			logger.info("Student-> {} - Review-> {}", student.getName(), student.getReviews());
		});
	}
	
	@Test
	public void CrossJoin() {
		Query qry = em.createQuery("Select c, s From Course c, Student s"); // when doing a query that results more than one entity, pay attention to the fact that an array will be the result of it query
		List<Object[]> results  = qry.getResultList();
		logger.info("Cross Join between Course and Students -> {}", results.size());
		results.stream().forEach(res ->{
			logger.info("Course-> {} - Student-> {}", res[0], res[1]);
		});
	}
	

}
