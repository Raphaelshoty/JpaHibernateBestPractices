package com.spring.jpa.hibernate.app.entity;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name= "Student")
public class Student {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name", nullable = false) // @COLUMN is used just for columns or properties that are not joining columns
	@NotBlank
	private String name;
	
	// this way the Student is owning the relationship, and receive the foreign key from passport
	@OneToOne(fetch = FetchType.LAZY)// this modify the default behavior of the OneToOne fetch which is eager, to lazy	
	@JoinColumn(name = "passport_id", foreignKey = @ForeignKey(name = "fk_student_passport"))//  specify that this column is special, its a column to represents a join to another table 
	private Passport passport;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "student_course", 
		joinColumns = @JoinColumn(name = "student_id", foreignKey = @ForeignKey(name = "fk_course_student")), 
		inverseJoinColumns = @JoinColumn(name = "course_id", foreignKey = @ForeignKey(name = "fk_student_course"))
	)	// typical from use in manyToMany relationship, where i have one table between to entities		
	private Set<Course> courses = new HashSet<>();
	
	
	public Student() {		
	}
	
	public Student(@NotBlank String name) {		
		this.name = name;
	}
	
	public Student(@NotBlank String name, Passport passport) {
		this.name = name;
		this.passport = passport;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}
	
	public Passport getPassport() {
		return passport;
	}

	public void setPassport(Passport passport) {
		this.passport = passport;
	}
   	
	public Set<Course> getCourses() {
		return courses;
	}

	public void setCourse(Course course) {
		this.courses.add(course);
	}
	
	public void removeCourse(Course course) {
		this.courses.remove(course);
	}

	@Override
	public String toString() {
		return String.format("Student [%s]", name);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
	
	
}
