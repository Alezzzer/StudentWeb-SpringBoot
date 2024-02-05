package com.studentweb.controllers;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.hibernate.engine.jdbc.StreamUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studentweb.dto.KorisnikDto;
import com.studentweb.exceptions.ApiResponse;
import com.studentweb.exceptions.CustomException;
import com.studentweb.model.Korisnik;
import com.studentweb.services.FajlService;
import com.studentweb.services.KorisnikService;

@RestController
@RequestMapping("/api")
public class KorisnikController {
	@Value("${studentweb.images.userprofiles}")
	String userprofileimagepath;
	
	@Autowired
	KorisnikService userService;
	
	@Autowired
	FajlService fileService;

	@Autowired
	ModelMapper modelMapper;

	// Dobavi korisnika
	@GetMapping("/users/{username}")
	public ResponseEntity<KorisnikDto> getSingleUser(@PathVariable String username) {
		Korisnik user = userService.getUserByUsername(username);
		return new ResponseEntity<KorisnikDto>(modelMapper.map(user, KorisnikDto.class), HttpStatus.OK);
	}

	// Stavi sliku korisnika defaultnu
	@GetMapping(value = "/images/serveuserimage/{imagename}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void serveImage(@PathVariable("imagename") String imagename, HttpServletResponse response) {

		try {
			InputStream is = fileService.serveImage(userprofileimagepath, imagename);
			response.setContentType(MediaType.IMAGE_JPEG_VALUE);
			StreamUtils.copy(is, response.getOutputStream());

		} catch (FileNotFoundException e) {
			throw new CustomException("Fajl sa tim imenom nije nađen:" + imagename, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Azuriraj korisnika
	@PutMapping("/users/{username}")
	public ResponseEntity<KorisnikDto> updateSingleUser(@RequestBody Korisnik user, @PathVariable String username) {
		Korisnik updateduser = userService.updateUserByUsername(user, username);
		return new ResponseEntity<KorisnikDto>(modelMapper.map(user, KorisnikDto.class), HttpStatus.OK);
	}

	// Izbrisi korisnika
	@DeleteMapping("/users/{username}")
	public ResponseEntity<ApiResponse> deleteSingleUser(@PathVariable String username) {
		userService.deleteUserByUsername(username);
		ApiResponse apiResponse = new ApiResponse("Korisnik je uspešno obrisan sa imenom :" + username,
				LocalDateTime.now(), HttpStatus.OK, HttpStatus.OK.value());
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}

	// Kreiraj novog korisnika
	@PostMapping("/users")
	public ResponseEntity<KorisnikDto> addNewUser(@Valid @RequestBody Korisnik User) {
		Korisnik createdUser = userService.createUser(User);
		return new ResponseEntity<KorisnikDto>(modelMapper.map(createdUser, KorisnikDto.class), HttpStatus.CREATED);
	}

	// Dobavi sve korisnike
	@GetMapping("/users")
	public ResponseEntity<List<KorisnikDto>> getAllUsers() {
		List<KorisnikDto> allUsers = userService.getAllUsers().stream().map(user -> modelMapper.map(user, KorisnikDto.class))
				.collect(Collectors.toList());
		return new ResponseEntity<>(allUsers, HttpStatus.OK);
	}
}
