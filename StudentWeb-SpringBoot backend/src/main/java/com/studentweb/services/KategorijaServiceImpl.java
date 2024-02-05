package com.studentweb.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.studentweb.exceptions.CustomException;
import com.studentweb.model.Kategorija;
import com.studentweb.repositories.KategorijaRepository;

@Service
public class KategorijaServiceImpl implements KategorijaService {

	@Autowired
	KategorijaRepository categoryRepository;
	
	@Override
	public Kategorija createCategory(Kategorija category) {
		return categoryRepository.save(category);
	}

	@Override
	public Kategorija updateCategory(Kategorija category, Integer id) {
		Kategorija foundcategory = categoryRepository.findById(id).orElseThrow(()->new CustomException("Kategorija nije nađena sa id :"+id,HttpStatus.NOT_FOUND));
		foundcategory.setName(category.getName());
		foundcategory.setDescription(category.getDescription());
		return categoryRepository.save(foundcategory);
	}

	@Override
	public void deleteCategory(Integer id) {
		categoryRepository.findById(id).orElseThrow(()->new CustomException("Kategorija nije nađena sa id :"+id,HttpStatus.NOT_FOUND));
		categoryRepository.deleteById(id);
	}

	@Override
	public Kategorija getCategoryById(Integer id) {
		Kategorija foundcategory = categoryRepository.findById(id).orElseThrow(()->new CustomException("Kategorija nije nađena sa id :"+id,HttpStatus.NOT_FOUND));
		return foundcategory;
	}

	@Override
	public List<Kategorija> getAllCategories() {
		List<Kategorija> allcategories = categoryRepository.findAll();
		if(allcategories.size()==0)
			throw new CustomException("No Category found",HttpStatus.NOT_FOUND);
		return allcategories;
	}
	
}
