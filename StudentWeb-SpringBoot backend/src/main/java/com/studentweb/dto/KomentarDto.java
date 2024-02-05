package com.studentweb.dto;

import java.util.Date;

import com.studentweb.model.Korisnik;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class KomentarDto {
	private Integer cid;
	private String comment;
	private Date commentdate;
	private KorisnikDto user;
}
