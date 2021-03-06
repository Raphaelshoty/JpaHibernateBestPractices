package com.spring.jpa.hibernate.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.spring.jpa.hibernate.app.repository.CourseRepository;
import com.spring.jpa.hibernate.app.repository.EmployeeRepository;
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
	
	@Autowired
	private EmployeeRepository empRepo;
	
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
//		empRepo.insertEmployee(new FullTimeEmployee("Josías José Jeager", new BigDecimal(10000)));
//		
//		empRepo.insertEmployee(new PartTimeEmployee("Isaías Jose Jeager", new BigDecimal(75)));
//		
//		//logger.info("All employees -> {}", empRepo.findAll());
//		logger.info("Full time employees -> {}", empRepo.findAllFullTimeEmployee());
//		logger.info("Part Time employees -> {}", empRepo.findAllPartTimeTimeEmployee());
		
	}

}
