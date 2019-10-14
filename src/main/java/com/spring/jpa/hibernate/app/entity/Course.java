package com.spring.jpa.hibernate.app.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.websocket.ClientEndpoint;

import org.apache.tomcat.jni.Address;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Entity
@Table(name="Course")
@NamedQueries({
	@NamedQuery(name = "find_all", query = "Select c From Course c"),
	@NamedQuery(name = "find_all_parameter", query = "Select c from Course c Where Upper(c.name) = Upper('SPRING') ")
})
@Cacheable(value = true) // this way Im informing that this entity can be stored on second level cache, because this data stored on course is not updated so frequently
@SQLDelete(sql = "UPDATE course SET is_deleted = true Where id = ?") // this simple sql will inform that if some row on this entity is being deleted, execute this sql
@Where(clause = "is_deleted = false")// this annotation will tell to select just those rows that are not marked as true
public class Course {
	
	private static Logger LOGGER = LoggerFactory.getLogger(Course.class);
	
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
	
	//in order to provide an way to maintain the data on database doing a soft delete this flag will tell if it is deleted from system or not.	
	@Column(name = "is_deleted")
	private boolean isDeleted;
	
	@PreRemove // provide some actions before the removal of the entity 
	private void preRemove() {
		LOGGER.info("Setting is_deleted to true");
		this.isDeleted = true;
	}
	
	protected Course() {}
	
	public Course(String name) {
		this.isDeleted = false; // to tell that the course is active since that was just created
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

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
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
