package com.studentweb.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import com.studentweb.dto.ObjavaResponseDto;
import com.studentweb.model.Kategorija;
import com.studentweb.model.Korisnik;
import com.studentweb.model.Objava;

public interface ObjavaService {
	
	Objava createPost(Objava post,String username,String categoryname,MultipartFile file,String folderpath);
	Objava getPostById(Integer id);
	Objava updatePostById(Objava newpostdata,Integer postid,String username);
	void deletePostById(Integer id);
	ObjavaResponseDto getAllPosts(Integer pagenumber,Integer pagesize,boolean mostrecentfirst);
	ObjavaResponseDto getAllPostsByCategory(String category,Integer pagenumber, Integer pagesize,
			boolean mostrecentfirst);
	List<Objava> getAllPostsByUser(String username,boolean mostrecentfirst);
}
