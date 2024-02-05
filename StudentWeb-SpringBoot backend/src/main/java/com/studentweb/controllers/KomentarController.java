package com.studentweb.controllers;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studentweb.dto.KomentarDto;
import com.studentweb.exceptions.ApiResponse;
import com.studentweb.model.Komentar;
import com.studentweb.services.KomentarService;

@RestController
@RequestMapping("/api")
public class KomentarController {

	@Autowired
	KomentarService commentService;

	@Autowired
	ModelMapper modelMapper;
//Dodaj komentar na objavu
	@PostMapping("/users/{username}/posts/{postid}/comments")
	public ResponseEntity<KomentarDto> addNewComment(@RequestBody Komentar comment,
			@PathVariable("username") String username, @PathVariable("postid") Integer postid) {
		Komentar createdComment = commentService.createComment(comment, username, postid);
		return new ResponseEntity<>(modelMapper.map(createdComment, KomentarDto.class), HttpStatus.CREATED);
	}

	// Izbrisi komentar sa objave
	@DeleteMapping("/users/{username}/posts/{postid}/comments/{commentid}")
	public ResponseEntity<ApiResponse> deleteCommentByCommentId(@PathVariable("username") String username,
			@PathVariable("postid") Integer postid, @PathVariable("commentid") Integer commentid) {

		commentService.deleteComment(username, commentid);
		ApiResponse apiResponse = new ApiResponse("Komentar uspešno obrisan sa id :" + commentid,
				LocalDateTime.now(), HttpStatus.OK, HttpStatus.OK.value());
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}
}
