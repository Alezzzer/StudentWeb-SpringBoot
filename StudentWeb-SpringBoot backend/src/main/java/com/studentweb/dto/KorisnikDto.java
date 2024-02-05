package com.studentweb.dto;

import java.util.List;



import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class KorisnikDto {
	private int uid;
	private String name;
	private String username;
	private String password;
	private String about;
	private String profilepic;
	
	
}
