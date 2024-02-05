package com.studentweb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studentweb.model.Komentar;

public interface KomentarRepository extends JpaRepository<Komentar, Integer> {

}
