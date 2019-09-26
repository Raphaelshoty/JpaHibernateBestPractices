package com.spring.jpa.hibernate.app.repository;

import java.util.Comparator;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.spring.jpa.hibernate.app.entity.Passport;
import com.spring.jpa.hibernate.app.entity.Student;

@Repository
@Transactional
public class PassportRepository   {

	@Autowired
	private EntityManager em;
	
	public Passport findById(Long id) {
		return em.find(Passport.class, id);
	}
	
	public Set<Passport> findByNumber(String number){
		TypedQuery<Passport> query = em.createQuery("Select p from Passport p Where UPPER(p.number) Like Upper(:number)", Passport.class);
		query.setParameter("number", "%"+number+"%");
		return query.getResultList().stream().sorted(Comparator.comparing(Passport::getNumber)).distinct().collect(Collectors.toSet());
	}
	
	public Passport save(Passport pass) {
		Long id = pass.getId();
		if(Objects.isNull(id)) {
			em.persist(pass);
		}else {
			em.merge(pass);
		}
		return pass;
	}
	
	public void saveWithStudent(Passport pass, Student student) {
		em.persist(pass);
		student.setPassport(pass);
		em.persist(student);		
	}
	
	public void deleteById(Long id) {
		Passport pass = em.find(Passport.class, id);
		em.remove(pass);
	}
	
	public void deleteEntity(Passport pass) {
		em.remove(pass);
	}
	
		
	
}
