package com.studentweb.services;

import java.util.List;

import com.studentweb.model.Korisnik;
public interface KorisnikService {
	Korisnik createUser(Korisnik user);
	Korisnik updateUserByUsername(Korisnik user,String username);
	Korisnik getUserByUsername(String username);
	boolean deleteUserByUsername(String username);
	List<Korisnik> getAllUsers();
}
