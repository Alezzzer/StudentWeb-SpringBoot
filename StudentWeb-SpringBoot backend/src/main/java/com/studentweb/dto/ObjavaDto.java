package com.studentweb.dto;

import java.util.Date;
import java.util.List;

import com.studentweb.model.Kategorija;
import com.studentweb.model.Korisnik;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class ObjavaDto {
	private int pid;
	private String title;
	private String content;
	private String image;
	private Date date;
	private KategorijaDto category;
	private KorisnikDto user;
	private List<KomentarDto> comments;
}
