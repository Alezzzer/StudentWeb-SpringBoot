package com.studentweb.services;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.studentweb.dto.ObjavaDto;
import com.studentweb.dto.ObjavaResponseDto;
import com.studentweb.exceptions.CustomException;
import com.studentweb.model.Kategorija;
import com.studentweb.model.Korisnik;
import com.studentweb.model.Objava;
import com.studentweb.repositories.KategorijaRepository;
import com.studentweb.repositories.ObjavaRepository;
import com.studentweb.repositories.KorisnikRepository;

@Service
public class ObjavaServiceImpl implements ObjavaService {
	@Autowired
	ObjavaRepository postRepository;

	@Autowired
	KorisnikRepository userRepository;

	@Autowired
	KategorijaRepository categoryRepository;

	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	FajlService fileService;

	@Override
	public Objava createPost(Objava post, String username, String categoryname,MultipartFile file,String folderpath) {
		Korisnik founduser = userRepository.findUserByUsername(username.toLowerCase())
				.orElseThrow(() -> new CustomException("Korisnik nije nađen sa korisničkim imenom : " + username.toLowerCase(),
						HttpStatus.NOT_FOUND));

		Kategorija foundcategory = categoryRepository.findCategoryByName(categoryname).orElseThrow(
				() -> new CustomException("Kategorija nije pronađena po imenu : " + categoryname, HttpStatus.NOT_FOUND));

		post.setDate(new Date());
		post.setCategory(foundcategory);
		post.setUser(founduser);
		
		String filenamewithtimestamp="";
		if(file!=null) {
			try {
				filenamewithtimestamp=fileService.uploadImage(folderpath, file);
				post.setImage(filenamewithtimestamp);
			} catch (IOException e) {
				throw new CustomException("Greška prilikom učitavanja slike sa objavom!",HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return postRepository.save(post);
	}

	@Override
	public Objava getPostById(Integer id) {
		return postRepository.findById(id)
				.orElseThrow(() -> new CustomException("Objava nije pronađena po id :" + id, HttpStatus.NOT_FOUND));
	}

	@Override
	public Objava updatePostById(Objava newpostdata, Integer postid, String username) {
		Korisnik founduser = userRepository.findUserByUsername(username.toLowerCase())
				.orElseThrow(() -> new CustomException("Korisnik nije nađen sa korisničkim imenom : " + username.toLowerCase(),
						HttpStatus.NOT_FOUND));

		Objava foundPost = postRepository.findById(postid)
				.orElseThrow(() -> new CustomException("Objava nije pronađena po id :" + postid, HttpStatus.NOT_FOUND));
		foundPost.setTitle(newpostdata.getTitle() == null ? foundPost.getTitle() : newpostdata.getTitle());
		foundPost.setContent(newpostdata.getContent() == null ? foundPost.getContent() : newpostdata.getContent());
		System.out.println(newpostdata.getImage());
		foundPost.setImage(foundPost.getImage());
		return postRepository.save(foundPost);
	}

	@Override
	public void deletePostById(Integer id) {
		postRepository.findById(id)
				.orElseThrow(() -> new CustomException("Objava nije pronađena po id :" + id, HttpStatus.NOT_FOUND));
		postRepository.deleteById(id);
	}


	@Override
	public ObjavaResponseDto getAllPostsByCategory(String category, Integer pagenumber, Integer pagesize,
			boolean mostrecentfirst) {
		Sort sort = Sort.by(mostrecentfirst ? Direction.DESC : Direction.ASC, "date");
		Pageable pageable = PageRequest.of(pagenumber, pagesize, sort);
		Page<Objava> pageinfo = null;
		
		if (category.equals("All")) {
			pageinfo = postRepository.findAll(pageable);
		} else {
			Kategorija foundcategory = categoryRepository.findCategoryByName(category).orElseThrow(
					() -> new CustomException("Kategorija nije pronađena po imenu : " + category, HttpStatus.NOT_FOUND));

			pageinfo = postRepository.findPostByCategory(foundcategory, pageable);
		}
		List<Objava> posts = pageinfo.getContent();
		List<ObjavaDto> postsdtos = posts.stream().map(post -> modelMapper.map(post, ObjavaDto.class))
				.collect(Collectors.toList());
		
		ObjavaResponseDto postResponseDto = new ObjavaResponseDto();
		postResponseDto.setPosts(postsdtos);
		postResponseDto.setCurrentpage(pageinfo.getNumber());
		postResponseDto.setIslastpage(pageinfo.isLast());
		postResponseDto.setTotalpage(pageinfo.getTotalPages());
		postResponseDto.setTotalposts(pageinfo.getTotalElements());
		return postResponseDto;
	}

	@Override
	public ObjavaResponseDto getAllPosts(Integer pagenumber, Integer pagesize, boolean mostrecentfirst) {
		Sort sort = Sort.by(mostrecentfirst ? Direction.DESC : Direction.ASC, "date");
		Pageable pageable = PageRequest.of(pagenumber, pagesize, sort);
		Page<Objava> pageinfo = postRepository.findAll(pageable);
		List<Objava> posts = pageinfo.getContent();
		List<ObjavaDto> postsdtos = posts.stream().map(post -> modelMapper.map(post, ObjavaDto.class))
				.collect(Collectors.toList());

		ObjavaResponseDto postResponseDto = new ObjavaResponseDto();
		postResponseDto.setPosts(postsdtos);
		postResponseDto.setCurrentpage(pageinfo.getNumber());
		postResponseDto.setIslastpage(pageinfo.isLast());
		postResponseDto.setTotalpage(pageinfo.getTotalPages());
		postResponseDto.setTotalposts(pageinfo.getTotalElements());
		return postResponseDto;
	}

	@Override
	public List<Objava> getAllPostsByUser(String username,boolean mostrecentfirst) {
		Sort sort = Sort.by(mostrecentfirst ? Direction.DESC : Direction.ASC, "date");
		Korisnik founduser = userRepository.findUserByUsername(username)
				.orElseThrow(() -> new CustomException("Korisnik nije nađen u bazi podataka :" + username, HttpStatus.NOT_FOUND));
		return postRepository.findPostByUser(founduser,sort);
	}

}
