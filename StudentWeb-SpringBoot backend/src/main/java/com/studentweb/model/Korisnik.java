package com.studentweb.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Korisnik {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int uid;
	@NotEmpty(message = "Polje za unos imena ne sme biti prazno")
	private String name;
	@NotEmpty(message = "Polje za unos korisničkog imena ne sme biti prazno")
	@Column(unique = true)
	private String username;
	@NotEmpty(message = "Polje za šifru ne sme biti prazno")
	@Size(min = 5,max = 20,message = "Šifra mora biti dužine od 5 do 20 karaktera")
	private String password;
	private String about;
	private String profilepic="default.jpg";
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<Objava> allposts=new ArrayList<>();
	
	
	
	
}
