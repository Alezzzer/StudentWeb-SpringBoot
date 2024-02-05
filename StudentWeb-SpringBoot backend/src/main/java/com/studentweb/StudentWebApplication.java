package com.studentweb;

import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.studentweb.model.Kategorija;
import com.studentweb.repositories.KategorijaRepository;


@SpringBootApplication
public class StudentWebApplication implements ApplicationRunner{

	
	@Autowired
	KategorijaRepository categoryRepository;

	public static void main(String[] args) {
		SpringApplication.run(StudentWebApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
	
		
		
		Kategorija category;
		if(categoryRepository.findById(1).isEmpty()) {
			category=new Kategorija();
			category.setName("Fakultet");
			category.setDescription("O fakultetu");
			categoryRepository.save(category);
		}
		if(categoryRepository.findById(2).isEmpty()) {
			category=new Kategorija();
			category.setName("Posao");
			category.setDescription("O poslu");
			categoryRepository.save(category);
		}
		if(categoryRepository.findById(3).isEmpty()) {
			category=new Kategorija();
			category.setName("Menza");
			category.setDescription("O menzi");
			categoryRepository.save(category);
		}
		if(categoryRepository.findById(4).isEmpty()) {
			category=new Kategorija();
			category.setName("Žurka");
			category.setDescription("O zurkama i koncertima");
			categoryRepository.save(category);
		}
		if(categoryRepository.findById(5).isEmpty()) {
			category=new Kategorija();
			category.setName("Sport");
			category.setDescription("O sportu");
			categoryRepository.save(category);
		}
		if(categoryRepository.findById(6).isEmpty()) {
			category=new Kategorija();
			category.setName("Društveni život");
			category.setDescription("O drustvenom zivotu");
			categoryRepository.save(category);
		}
	}
}
