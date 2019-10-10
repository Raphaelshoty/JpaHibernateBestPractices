package com.spring.jpa.hibernate.app.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


@Entity
@Table(name="Course")
@NamedQueries({
	@NamedQuery(name = "find_all", query = "Select c From Course c"),
	@NamedQuery(name = "find_all_parameter", query = "Select c from Course c Where Upper(c.name) = Upper('SPRING') ")
})
public class Course {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name", nullable = false)
	@NotBlank	
	private String name;	
	
	// for oneToMany mapping is Lazy by default
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "course")	 // WHERE THE mappedBy goes i should not use @JoinColumn or @JoinTable 
	private Set<Review> reviews = new HashSet<>();
	
	//@JsonIgnore // this is just for exemplification of the spring-boot-starter-data-rest dependency and @RepositoryRestResource over the interface that extends jpaRepository
	@ManyToMany(mappedBy = "courses",fetch = FetchType.LAZY)	
	private Set<Student> students = new HashSet<>();
	
	// register when the row or the entity was updated
	@UpdateTimestamp
	private LocalDateTime lastUpdate;
	
	// register when the row or the entity was created
	@CreationTimestamp
	private LocalDateTime creationDate;
	
	protected Course() {}
	
	public Course(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	

	public LocalDateTime getLastUpdate() {
		return lastUpdate;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}	

	public Set<Review> getReviews() {
		return reviews;
	}

	public void addtReview(Review review) {
		this.reviews.add(review);
	}
	
	public void removeReview(Review review){
		this.reviews.remove(review);
	}
	
	public Set<Student> getStudents() {
		return students;
	}

	public void setStudent(Student student) {
		this.students.add(student);
	}
	public void removeStudent(Student student) {
		this.students.remove(student);
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
		Course other = (Course) obj;
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

	@Override
	public String toString() {
		return "Course [id=" + id + ", name=" + name + "]";
	}
	
	
	
	
	

}
