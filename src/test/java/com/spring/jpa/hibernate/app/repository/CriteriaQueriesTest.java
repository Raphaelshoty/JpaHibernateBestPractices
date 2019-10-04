package com.spring.jpa.hibernate.app.repository;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
public class CriteriaQueriesTest {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	EntityManager em;	
		
	@Test	
	public void criteriaGetAllCourses() {		
		// Select c From Course c
		
		// to build Criteria queries those steps are recomended 
		// 1 Use Criteria Builder to create a Criteria Query returning the expected result object
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Course> cq = cb.createQuery(Course.class);
		
		// 2 define roots for tables which are involved in the query
		Root<Course> courseRoot = cq.from(Course.class);
		
		// 3 define predicates etc using criteria builder
		
		// 4 add predicates etc to criteria query
		
		// 5 build the typed query using the entity manager and criteria query 		
		TypedQuery<Course> query = em.createQuery(cq.select(courseRoot));
		List<Course> courses = query.getResultList();
		logger.info("Results from criteria query - >{}", courses);
	}
	
	@Test	
	public void criteriaGetAllCoursesWithCondition() {		
		// Select c From Course c where c.name like '%something%'
		//those two lines below should be parameters so the way its explained below its just an example		
		String condition = "Boot";
		String conditionPattern = "%"+condition+"%";
		
		// to build Criteria queries those steps are recomended 
		// 1 Use Criteria Builder to create a Criteria Query returning the expected result object
		
		CriteriaBuilder cb = em.getCriteriaBuilder(); // CriteriaBuilder to build the structure of the query
		CriteriaQuery<Course> cq = cb.createQuery(Course.class); // CriteriaQuery is the query itself which is built by CriteriaBuilder, the CriteriaQuery takes a type to return
		
		// 2 define roots for tables which are involved in the query
		
		Root<Course> courseRoot = cq.from(Course.class); // define a Root is define where those data will be searched, it means the entities itself 
		
		// 3 define predicates etc using criteria builder
		
		Predicate like = cb.like(courseRoot.get("name"), conditionPattern);	 // Predicates are conditions, equal, like and so on, those conditions are compared to patterns that are passed by parameters	
		
		// 4 add predicates etc to criteria query
		
		cq.where(like); // inserting the condition on the query itself, this is made using the query not the builder of it
		
		// 5 build the typed query using the entity manager and criteria query 	
		
		TypedQuery<Course> query = em.createQuery(cq.select(courseRoot));
		List<Course> courses = query.getResultList();
		logger.info("Results from criteria query - >{}", courses);
	}
	
	
	@Test	
	public void criteriaGetAllCoursesWithoutStudents() {		
		// Select c From Course c Where c.students is empty 		
		
		// to build Criteria queries those steps are recommended 
		// 1 Use Criteria Builder to create a Criteria Query returning the expected result object
		
		CriteriaBuilder cb = em.getCriteriaBuilder(); // CriteriaBuilder to build the structure of the query
		CriteriaQuery<Course> cq = cb.createQuery(Course.class); // CriteriaQuery is the query itself which is built by CriteriaBuilder, the CriteriaQuery takes a type to return
		
		// 2 define roots for tables which are involved in the query
		
		Root<Course> courseRoot = cq.from(Course.class); // define a Root is define where those data will be searched, it means the entities itself 
		
		// 3 define predicates etc using criteria builder
		
		Predicate noStudents = cb.isEmpty(courseRoot.get("students")) ;	 // Predicates are conditions, equal, like and so on, those conditions are compared to patterns that are passed by parameters	
		
		// 4 add predicates etc to criteria query
		
		cq.where(noStudents); // inserting the condition on the query itself, this is made using the query not the builder of it
		
		// 5 build the typed query using the entity manager and criteria query 	
		
		TypedQuery<Course> query = em.createQuery(cq.select(courseRoot));
		List<Course> courses = query.getResultList();
		logger.info("Results from criteria query - >{}", courses);
	}
	
	@Test	
	public void criteriaJoin() {		
		// Select c From Course c Join c.students s		
		
		// to build Criteria queries those steps are recommended 
		// 1 Use Criteria Builder to create a Criteria Query returning the expected result object
		
		CriteriaBuilder cb = em.getCriteriaBuilder(); // CriteriaBuilder to build the structure of the query
		CriteriaQuery<Course> cq = cb.createQuery(Course.class); // CriteriaQuery is the query itself which is built by CriteriaBuilder, the CriteriaQuery takes a type to return
		
		// 2 define roots for tables which are involved in the query
		
		Root<Course> courseRoot = cq.from(Course.class); // define a Root is define where those data will be searched, it means the entities itself 
		
		// 3 define predicates etc using criteria builder
		
		Join<Object, Object> join = courseRoot.join("students");
		
		// 4 add predicates etc to criteria query		
		
		
		// 5 build the typed query using the entity manager and criteria query 	
		
		TypedQuery<Course> query = em.createQuery(cq.select(courseRoot));
		List<Course> courses = query.getResultList();
		logger.info("Results from criteria query - >{}", courses);
	}
	
	@Test	
	public void criteriaLeftJoin() {		
		// Select c From Course c Left Join c.students s		
		
		// to build Criteria queries those steps are recommended 
		// 1 Use Criteria Builder to create a Criteria Query returning the expected result object
		
		CriteriaBuilder cb = em.getCriteriaBuilder(); // CriteriaBuilder to build the structure of the query
		CriteriaQuery<Course> cq = cb.createQuery(Course.class); // CriteriaQuery is the query itself which is built by CriteriaBuilder, the CriteriaQuery takes a type to return
		
		// 2 define roots for tables which are involved in the query
		
		Root<Course> courseRoot = cq.from(Course.class); // define a Root is define where those data will be searched, it means the entities itself 
		
		// 3 define predicates etc using criteria builder
		
		Join<Object, Object> join = courseRoot.join("students",JoinType.LEFT);
		
		// 4 add predicates etc to criteria query		
		
		
		// 5 build the typed query using the entity manager and criteria query 	
		
		TypedQuery<Course> query = em.createQuery(cq.select(courseRoot));
		List<Course> courses = query.getResultList();
		logger.info("Results from criteria query - >{} ", courses);
	}
	
	@Test	
	public void criteriaLeftFetchJoin() {		
		// Select s From Student s  Left Join Fetch s.passport p		
		
		// to build Criteria queries those steps are recommended 
		// 1 Use Criteria Builder to create a Criteria Query returning the expected result object
		
		CriteriaBuilder cb = em.getCriteriaBuilder(); // CriteriaBuilder to build the structure of the query
		CriteriaQuery<Student> cq = cb.createQuery(Student.class); // CriteriaQuery is the query itself which is built by CriteriaBuilder, the CriteriaQuery takes a type to return
		
		// 2 define roots for tables which are involved in the query
		
		Root<Student> studentRoot = cq.from(Student.class); // define a Root is define where those data will be searched, it means the entities itself 
		
		// 3 define predicates etc using criteria builder
		
		studentRoot.fetch("passport",JoinType.LEFT); // here is done the fetch instead of a simple join
		
		// 4 add predicates etc to criteria query		
		
		
		// 5 build the typed query using the entity manager and criteria query 	
		
		TypedQuery<Student> query = em.createQuery(cq.select(studentRoot));
		List<Student> students = query.getResultList();
		students.stream().forEach(stu -> {
			logger.info("Student {} - Passport - {}",stu.getName(), stu.getPassport().getNumber());
		});
	}
	
	
	

}
