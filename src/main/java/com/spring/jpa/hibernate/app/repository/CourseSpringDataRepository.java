package com.spring.jpa.hibernate.app.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.spring.jpa.hibernate.app.entity.Course;

@RepositoryRestResource(path = "courses") // to use this feature on exposing this methods and the repository as an service add the spring-boot-starter-data-rest dependency on POM.xml
// doing this above im exposing the repository as /courses
public interface CourseSpringDataRepository extends JpaRepository<Course, Long> {

	@Query("Select c From Course c Where UPPER(c.name) Like UPPER(:name) ORDER BY c.name ASC") // the advantage in this approach is that i can specify some conditions or 
	// use some piece of information as a parameters to find what i needs
	Set<Course> findByNameWithJPQL(String name);
	
	List<Course> findByName(String name, Sort sort); // in this approach i just need to declare the method using the naming convention, so it ready to use but it has its limitations based on the
	// fact that i need to inform the exact name content to be searched for.
	
	List<Course> findByNameAndId(String name, Long id); // example with two or more parameters which are present on Class base for the search
	
	@Query(value = "Select * from Course",nativeQuery = true) // this way i can use native query
	List<Course> findAllUsingNativeQuery();
	
	@Query(name = "find_all")
	List<Course> findAllUsingNamedQuery();
	
	@Modifying // everytime that a query on this repo is going to do some modifications on registers on database it need to be marked as @Modifying
	@Query("Update Course c set c.name = 'Spring is Awesome' Where Upper(c.name) like Upper(:name)")
	int updateCourseNames(String name);
	
	@Query("Select c from Course c Join Fetch c.students s Where Lower(s.name) Like Lower(:studentName)  Order By c.name DESC")
	Set<Course> findAllByStudent(String studentName);
	
	// if needed i can create update insert delete and so on just by following the naming covention  like deleteBySomething findBySomething 
	// if using a query to do something that is going to change database data... remember to use @Modifying annotation
}
