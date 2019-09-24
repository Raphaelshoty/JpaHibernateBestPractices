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
import com.spring.jpa.hibernate.app.repository.ReviewRepository;
import com.spring.jpa.hibernate.app.repository.StudentRepository;

@SpringBootApplication
public class AppApplication implements CommandLineRunner {

	@Autowired
	private CourseRepository courseRepo;
	
	@Autowired
	private StudentRepository studentRepo;
	
	@Autowired
	private ReviewRepository revRepo;
	
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Student stu = new Student("Jose");
		Course course  = courseRepo.findById(1L);
		stu.setCourse(course);
		Review rev = new Review("Foda", "5", course, stu);		
		
		//studentRepo.insertStudentAndReview(stu, rev);
		revRepo.insertReviewAndStudent(rev, stu);
		
	}

}
