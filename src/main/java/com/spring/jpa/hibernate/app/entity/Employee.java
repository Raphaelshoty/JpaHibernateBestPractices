package com.spring.jpa.hibernate.app.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@MappedSuperclass // when using mappedSuperClass inheritance strategy the superclass will no longer be an entity, only those that specialized from it, it means that only the specialized entity will be stored on database.
//@Entity 
@Table(name="Employee")
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // on that strategy, there will be created just one table containing all properties from mother class and the specialized classes, but to mark different classes on that table they will have flags to separate them
//@DiscriminatorColumn(name = "employee_type", discriminatorType = DiscriminatorType.STRING)// flag to column to diff entities on single table strategy
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) // on that strategy only the materialized class will have table and they will be separated from the mother class
//@Inheritance(strategy = InheritanceType.JOINED) // on that strategy, each class will have your table, but. that table will have only the specific properties of that class and those are in common with the mother class will be on a table that represents the mother class
public abstract class Employee {
	
	@Id
	//@GeneratedValue // identity generation strategy cannot be used on table_per_class strategy of inheritance 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name", nullable = false)
	@NotBlank	
	private String name;
	
	public Employee() {}
	
	public Employee(@NotBlank String name) {
		super();		
		this.name = name;
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

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + "]";
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
		Employee other = (Employee) obj;
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
