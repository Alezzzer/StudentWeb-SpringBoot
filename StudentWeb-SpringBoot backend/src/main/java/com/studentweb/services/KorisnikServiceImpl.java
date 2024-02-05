package com.studentweb.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.studentweb.exceptions.CustomException;
import com.studentweb.model.Korisnik;
import com.studentweb.repositories.KorisnikRepository;

@Service
public class KorisnikServiceImpl implements KorisnikService {

	@Autowired
	KorisnikRepository userRepository;

	@Override
	public Korisnik createUser(Korisnik user) {
	
		if (userRepository.findUserByUsername(user.getUsername().toLowerCase()).isPresent())
			throw new CustomException("Korisničko ime već postoji : " + (user.getUsername().toLowerCase()),
					HttpStatus.CONFLICT);
		user.setUsername(user.getUsername().toLowerCase());
		return userRepository.save(user);
	}

	@Override
	public Korisnik updateUserByUsername(Korisnik user, String username) {
		Korisnik founduser = userRepository.findUserByUsername(username.toLowerCase())
				.orElseThrow(() -> new CustomException("Korisnik nije nađen sa korisničkim imenom : " + username.toLowerCase(),
						HttpStatus.NOT_FOUND));
		if (user.getUsername() != null)
			if (userRepository.findUserByUsername(user.getUsername().toLowerCase()).isPresent())
				throw new CustomException("Korisničko ime već postoji : " + (user.getUsername().toLowerCase()),
						HttpStatus.CONFLICT);
		founduser.setPassword(user.getName() == null ? founduser.getName() : user.getName());
		founduser.setPassword(user.getPassword() == null ? founduser.getPassword() : user.getPassword());
		System.out.println("PASSED USERNAME VALUE:" + user.getUsername() == null);
		founduser.setUsername(
				user.getUsername() == null ? founduser.getUsername().toLowerCase() : user.getUsername().toLowerCase());
		founduser.setAbout(user.getAbout() == null ? founduser.getAbout() : user.getAbout());
		Korisnik updateduser = userRepository.save(founduser);
		return updateduser;
	}

	@Override
	public Korisnik getUserByUsername(String username) {
		Korisnik foundUser = userRepository.findUserByUsername(username)
				.orElseThrow(() -> new CustomException("Korisnik nije nađen sa korisničkim imenom : " + username.toLowerCase(),
						HttpStatus.NOT_FOUND));
		return foundUser;
	}

	@Override
	public boolean deleteUserByUsername(String username) {
		Korisnik foundUser = userRepository.findUserByUsername(username).orElseThrow(
				() -> new CustomException("Korisnik nije nađen sa korisničkim imenom : " + username, HttpStatus.NOT_FOUND));
		userRepository.deleteById(foundUser.getUid());
		return true;
	}

	@Override
	public List<Korisnik> getAllUsers() {
		List<Korisnik> allusers = userRepository.findAll();
		if (allusers.size() == 0)
			throw new CustomException("Nema korisnika u bazi podataka", HttpStatus.NOT_FOUND);

		return allusers;
	}

}
