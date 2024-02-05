package com.studentweb.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.studentweb.model.Kategorija;
import com.studentweb.model.Korisnik;
import com.studentweb.model.Objava;



public interface ObjavaRepository extends JpaRepository<Objava,Integer> {
	
	List<Objava> findPostByUser(Korisnik username);
	List<Objava> findPostByUser(Korisnik username,Sort sort);
	List<Objava> findPostByCategory(Kategorija category);
	Page<Objava> findPostByCategory(Kategorija category,Pageable pageable);

}
