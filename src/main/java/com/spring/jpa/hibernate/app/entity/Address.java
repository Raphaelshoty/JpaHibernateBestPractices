package com.spring.jpa.hibernate.app.entity;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Embeddable // this mark that class as a class to use to be injected or embedded in other classes as property
public class Address {

	@NotBlank
	private String streetName;
	
	@NotNull
	private Integer number;
	
	public Address() {}
	
	public Address(@NotBlank String streetName,@NotNull Integer number) {
		super();
		this.streetName = streetName;
		this.number = number;
	}
	
	public String getStreetName() {
		return streetName;
	}
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		result = prime * result + ((streetName == null) ? 0 : streetName.hashCode());
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
		Address other = (Address) obj;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		if (streetName == null) {
			if (other.streetName != null)
				return false;
		} else if (!streetName.equals(other.streetName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Address [streetName=" + streetName + ", number=" + number + "]";
	}
	
	
	
	
}
