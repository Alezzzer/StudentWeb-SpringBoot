package com.studentweb.services;

import java.util.List;

import com.studentweb.model.Kategorija;

public interface KategorijaService {
	Kategorija createCategory(Kategorija category);
	Kategorija updateCategory(Kategorija category,Integer id);
	void deleteCategory(Integer id);
	Kategorija getCategoryById(Integer id);
	List<Kategorija> getAllCategories();
}
