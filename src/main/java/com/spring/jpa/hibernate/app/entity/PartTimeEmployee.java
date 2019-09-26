package com.spring.jpa.hibernate.app.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

@Entity
public class PartTimeEmployee  extends Employee{
		
	private BigDecimal hourlyWage;	
	
	public PartTimeEmployee() {}
	
	public PartTimeEmployee(@NotBlank String name, BigDecimal hourlyWage) {
		//super(name); this would have the same effect of the line below, but calling the superclass method 
		this.setName(name);
		this.hourlyWage = hourlyWage;
	}

	public BigDecimal getHourlyWage() {
		return hourlyWage;
	}

	public void setHourlyWage(BigDecimal hourlyWage) {
		this.hourlyWage = hourlyWage;
	}

	@Override
	public String toString() {
		return "PartTimeEmployee [name = " + this.getName() + " hourlyWage= " + hourlyWage + "] \n";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((hourlyWage == null) ? 0 : hourlyWage.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PartTimeEmployee other = (PartTimeEmployee) obj;
		if (hourlyWage == null) {
			if (other.hourlyWage != null)
				return false;
		} else if (!hourlyWage.equals(other.hourlyWage))
			return false;
		return true;
	}

}
