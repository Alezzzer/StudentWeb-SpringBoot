package com.studentweb.controllers;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.studentweb.dto.KorisnikDto;
import com.studentweb.exceptions.CustomException;
import com.studentweb.model.Korisnik;
import com.studentweb.repositories.KorisnikRepository;

@RestController
public class AutentikacijaController {

	@Autowired
	KorisnikRepository userRepository;
	
	@Autowired
	ModelMapper modeMapper;
	
	@PostMapping("/api/login")
	public ResponseEntity<KorisnikDto> performLogin(@RequestBody Korisnik user){
		Korisnik founduser = userRepository.findUserByUsernameAndPassword(user.getUsername(),user.getPassword()).orElseThrow(() -> new CustomException("Pogrešno korisničko ime ili šifra",
						HttpStatus.UNAUTHORIZED));
		return new ResponseEntity<KorisnikDto>( modeMapper.map(founduser,KorisnikDto.class),HttpStatus.OK);
	}
}
