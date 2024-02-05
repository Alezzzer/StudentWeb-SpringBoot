package com.studentweb.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studentweb.model.Korisnik;

public interface KorisnikRepository extends JpaRepository<Korisnik, Integer>{
	Optional<Korisnik> findUserByUsername(String username);
	Optional<Korisnik> findUserByUsernameAndPassword(String username,String password);

}
