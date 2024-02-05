package com.studentweb.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studentweb.dto.KategorijaDto;
import com.studentweb.exceptions.ApiResponse;
import com.studentweb.model.Kategorija;
import com.studentweb.services.KategorijaService;

@RestController
@RequestMapping("/api")
public class KategorijaController {

	@Autowired
	KategorijaService categoryService;

	@Autowired
	ModelMapper modelMapper;

	// Kreiraj novu kategoriju
	@PostMapping("/categories")
	public ResponseEntity<KategorijaDto> createCategory(@Valid @RequestBody Kategorija category) {
		Kategorija createdcategory = categoryService.createCategory(category);
		return new ResponseEntity<>(modelMapper.map(createdcategory,KategorijaDto.class), HttpStatus.CREATED);
	}

	// Dobavi sve kategorije
	@GetMapping("/categories")
	public ResponseEntity<List<KategorijaDto>> getAllCategories() {
		List<KategorijaDto> allCategories = categoryService.getAllCategories().stream().map(category->modelMapper.map(category,KategorijaDto.class)).collect(Collectors.toList());
		return new ResponseEntity<>(allCategories, HttpStatus.OK);
	}

	// Dobavi kategoriju po idu
	@GetMapping("/categories/{cid}")
	public ResponseEntity<KategorijaDto> getCategoryById(@PathVariable("cid") Integer cid) {
		Kategorija category = categoryService.getCategoryById(cid);
		return new ResponseEntity<>(modelMapper.map(category,KategorijaDto.class), HttpStatus.OK);
	}

	// Azuriraj kategoriju po idu
	@PutMapping("/categories/{cid}")
	public ResponseEntity<KategorijaDto> updateCategoryById(@PathVariable("cid") Integer cid,
			@Valid @RequestBody Kategorija category) {
		Kategorija updatedcategory = categoryService.updateCategory(category, cid);
		return new ResponseEntity<>(modelMapper.map(updatedcategory,KategorijaDto.class), HttpStatus.OK);
	}

	// Obrisi kategoriju po idu
	@DeleteMapping("/categories/{cid}")
	public ResponseEntity<ApiResponse> deleteCategoryById(@PathVariable("cid") Integer cid) {
		categoryService.deleteCategory(cid);
		return new ResponseEntity<>(new ApiResponse("Kategorija uspešno obrisana sa id :" + cid,
				LocalDateTime.now(), HttpStatus.OK, HttpStatus.OK.value()), HttpStatus.OK);
	}
}
