package com.studentweb.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.studentweb.exceptions.CustomException;
import com.studentweb.model.Komentar;
import com.studentweb.model.Korisnik;
import com.studentweb.model.Objava;
import com.studentweb.repositories.KomentarRepository;
import com.studentweb.repositories.ObjavaRepository;
import com.studentweb.repositories.KorisnikRepository;

@Service
public class KomentarServiceImpl implements KomentarService {

	@Autowired
	ObjavaRepository postRepository;
	
	@Autowired
	KorisnikRepository userRepository;
	
	@Autowired
	KomentarRepository commentRepository;
	
	@Override
	public Komentar createComment(Komentar comment, String username, Integer postid) {
		Korisnik founduser = userRepository.findUserByUsername(username).orElseThrow(() -> new CustomException("Korisničko ime nije pronađeno : " + username, HttpStatus.NOT_FOUND));
		Objava foundpost=postRepository.findById(postid).orElseThrow(() -> new CustomException("Objava nije nađena sa id : " + postid, HttpStatus.NOT_FOUND));
		comment.setCommentdate(new Date());
		comment.setUser(founduser);
		comment.setPost(foundpost);
		return commentRepository.save(comment);
	}

	@Override
	public void deleteComment(String username, Integer commentid) {
		Korisnik founduser = userRepository.findUserByUsername(username).orElseThrow(() -> new CustomException("Korisničko ime nije pronađeno : " + username, HttpStatus.NOT_FOUND));
		Komentar foundcomment=commentRepository.findById(commentid).orElseThrow(() -> new CustomException("Komentar nije nađen sa id : " + commentid, HttpStatus.NOT_FOUND));
		commentRepository.deleteById(commentid);
	}

}
