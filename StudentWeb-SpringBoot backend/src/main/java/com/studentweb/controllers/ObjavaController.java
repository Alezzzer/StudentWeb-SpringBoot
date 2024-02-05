package com.studentweb.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.studentweb.dto.ObjavaDto;
import com.studentweb.dto.ObjavaResponseDto;
import com.studentweb.exceptions.ApiResponse;
import com.studentweb.exceptions.CustomException;
import com.studentweb.model.Objava;
import com.studentweb.services.FajlService;
import com.studentweb.services.ObjavaService;

@RestController
@RequestMapping("/api")
public class ObjavaController {
	@Value("${studentweb.images.posts}")
	String postimagepath;

	@Autowired
	ObjavaService postService;

	@Autowired
	FajlService fileService;
	
	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	ModelMapper modelMapper;

	
	
	 //kreiranje objave 
	@PostMapping("/users/{username}/posts/{categoryname}")
	public ResponseEntity<ObjavaDto> createNewPostWithFormData(@RequestParam("post") String post,@RequestParam(name = "image",required = false) MultipartFile file, @PathVariable("username") String username,
			@PathVariable("categoryname") String categoryname) {
		
		Objava createdpost=null;
		try {
			//procitaj vrednost
			Objava postdata = objectMapper.readValue(post, Objava.class);
			//kreiraj
			createdpost=postService.createPost(postdata, username, categoryname, file, postimagepath);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<ObjavaDto>(modelMapper.map(createdpost, ObjavaDto.class), HttpStatus.CREATED);
	}

	// Dodavanje slike na objavu
	@PostMapping("/users/{username}/posts/{postid}/image")
	public ResponseEntity<ApiResponse> addImageToPost(@RequestParam("image") MultipartFile image,
			@PathVariable("username") String username, @PathVariable("postid") Integer postid) {
		String uploadedImageFilename = "";
		try {
			uploadedImageFilename = fileService.uploadImage(postimagepath, image);
		} catch (IOException e) {
			throw new CustomException("Došlo je do greške prilikom postavljanja slike", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		ApiResponse apiResponse = new ApiResponse("Slika je uspešno postavljena :" + uploadedImageFilename,
				LocalDateTime.now(), HttpStatus.OK, HttpStatus.OK.value());
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}


	@GetMapping(value = "/images/servepostimage/{imagename}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void serveImage(@PathVariable("imagename") String imagename, HttpServletResponse response) {

		try {
			InputStream is = fileService.serveImage(postimagepath, imagename);
			response.setContentType(MediaType.IMAGE_JPEG_VALUE);
			StreamUtils.copy(is, response.getOutputStream());

		} catch (FileNotFoundException e) {
			throw new CustomException("Fajl sa tim imenom nije pronađen:" + imagename, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Dobavljanje svih objava od strane odredjenog korisnika
	@GetMapping("/users/{username}/posts")
	public ResponseEntity<List<ObjavaDto>> getPostByUsername(@PathVariable("username") String username,
			@RequestParam(name = "mostrecentfirst", defaultValue = "true", required = false) Boolean mostrecentfirst) {
		List<ObjavaDto> allPostsByUser = postService.getAllPostsByUser(username, mostrecentfirst).stream()
				.map(post -> modelMapper.map(post, ObjavaDto.class)).collect(Collectors.toList());

		return new ResponseEntity<List<ObjavaDto>>(allPostsByUser, HttpStatus.OK);
	}

	// Dobavi jednu objavu na osnovu pid
	@GetMapping("/users/{username}/posts/{postid}")
	public ResponseEntity<ObjavaDto> getPostOfUserByPostId(@PathVariable("username") String username,
			@PathVariable("postid") Integer postid) {
		Objava post = postService.getPostById(postid);
		return new ResponseEntity<ObjavaDto>(modelMapper.map(post, ObjavaDto.class), HttpStatus.OK);
	}

	// Obrisi jednu objavu na osnovu pid
	@DeleteMapping("/users/{username}/posts/{postid}")
	public ResponseEntity<ApiResponse> deletePostOfUserByPostId(@PathVariable("username") String username,
			@PathVariable("postid") Integer postid) {
		postService.deletePostById(postid);
		ApiResponse apiResponse = new ApiResponse("Objava je uspešno obrisana :" + postid, LocalDateTime.now(),
				HttpStatus.OK, HttpStatus.OK.value());
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}

	// Azuriraj objavu na osnovu pid
	@PutMapping("/users/{username}/posts/{postid}")
	public ResponseEntity<ObjavaDto> updatePostOfUserByPostId(@PathVariable("username") String username,
			@PathVariable("postid") Integer postid, @RequestBody Objava newpostdata) {
		Objava updatedpost = postService.updatePostById(newpostdata, postid, username);

		return new ResponseEntity<ObjavaDto>(modelMapper.map(updatedpost, ObjavaDto.class), HttpStatus.OK);
	}

	// Dovavi objavu na osnovu pid
	@GetMapping("/posts/{postid}")
	public ResponseEntity<ObjavaDto> getPostByPostId(@PathVariable("postid") Integer postid) {
		Objava post = postService.getPostById(postid);
		return new ResponseEntity<ObjavaDto>(modelMapper.map(post, ObjavaDto.class), HttpStatus.OK);
	}

	
	//Dobavi sve objave po kategorijama
	@GetMapping("/posts/category/{categoryname}")
	public ResponseEntity<ObjavaResponseDto> getAllPostsByCategory(@PathVariable("categoryname") String categoryname,
			@RequestParam(value = "pagenumber", defaultValue = "0", required = false) Integer pagenumber,
			@RequestParam(value = "pagesize", defaultValue = "5", required = false) Integer pagesize,
			@RequestParam(value = "mostrecentfirst", defaultValue = "true", required = false) boolean mostrecentfirst) {
		ObjavaResponseDto allPostsByCategory = postService.getAllPostsByCategory(categoryname, pagenumber, pagesize,
				mostrecentfirst);
		return new ResponseEntity<ObjavaResponseDto>(allPostsByCategory, HttpStatus.OK);
	}

	// Dobavi sve objave
	@GetMapping("/posts")
	public ResponseEntity<ObjavaResponseDto> getAllPosts(
			@RequestParam(value = "pagenumber", defaultValue = "0", required = false) Integer pagenumber,
			@RequestParam(value = "pagesize", defaultValue = "5", required = false) Integer pagesize,
			@RequestParam(value = "mostrecentfirst", defaultValue = "true", required = false) boolean mostrecentfirst) {
		ObjavaResponseDto allPosts = postService.getAllPosts(pagenumber, pagesize, mostrecentfirst);

		return new ResponseEntity<>(allPosts, HttpStatus.OK);
	}

}
