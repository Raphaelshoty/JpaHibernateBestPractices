package com.spring.jpa.hibernate.app;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.spring.jpa.hibernate.app.entity.Course;
import com.spring.jpa.hibernate.app.entity.Review;
import com.spring.jpa.hibernate.app.entity.Student;
import com.spring.jpa.hibernate.app.repository.CourseRepository;
import com.spring.jpa.hibernate.app.repository.StudentRepository;

@SpringBootApplication
public class AppApplication implements CommandLineRunner {

	@Autowired
	private CourseRepository courseRepo;
	
	@Autowired
	private StudentRepository studentRepo;
	
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
//		Course course = courseRepo.findById(2L);
//		Set<Review> reviews = new HashSet<>();;
//		reviews.add(new Review("Hatsoff", "5"));
//		reviews.add(new Review("Amazing", "5"));
// 		
//		courseRepo.addReviewsForCourse(course.getId(), reviews);
		
		Student stu = new Student("Jose");
		Course course = new Course("Microservices");
				
		//studentRepo.insertStudentAndCourse(stu, course);
		
		courseRepo.insertCourseAndStudent(course, stu);
		
	}

}
