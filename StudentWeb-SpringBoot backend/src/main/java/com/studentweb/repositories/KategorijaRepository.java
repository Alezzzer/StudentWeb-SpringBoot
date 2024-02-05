package com.studentweb.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studentweb.model.Kategorija;

public interface KategorijaRepository extends JpaRepository<Kategorija,Integer> {

	Optional<Kategorija> findCategoryByName(String categoryname);
}
