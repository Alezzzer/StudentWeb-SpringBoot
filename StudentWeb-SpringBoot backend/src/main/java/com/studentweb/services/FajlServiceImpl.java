package com.studentweb.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.studentweb.exceptions.CustomException;

@Service
public class FajlServiceImpl implements FajlService {

	@Override
	public String uploadImage(String folderpath, MultipartFile file) throws IOException {
		//putanja foldera je  : /images/posts OR /images/userprofiles i bice dobije spolja
		String filename=file.getOriginalFilename();
		String filenamewithtimestamp="";
		if(filename.endsWith(".png") || filename.endsWith(".jpg") || filename.endsWith(".jpeg")) {
			Date d=new Date();
			filenamewithtimestamp=d.getTime()+"-"+filename;
			
		}else {
			throw new CustomException("Fajl format nije podržan",HttpStatus.BAD_REQUEST);
		}
		
		//kreiranje apsolutne putanje sa sve imenom
		String fullfilepath=folderpath+File.separator+filenamewithtimestamp;
		
		//kreiranje foldera gde ce slike biti smestene ukoliko folder ne postoji
		File f=new File(folderpath);
		if(!f.exists()) {
			
			f.mkdirs();
		}
			
		try {
			
			byte[] data = file.getBytes();
			FileOutputStream fos=new FileOutputStream(fullfilepath);
			fos.write(data);
			fos.close();

		}catch (Exception e) {
			System.out.println("Desila se greška");
		}
		return filenamewithtimestamp;
	}

	@Override
	public InputStream serveImage(String folderpath, String filename) throws FileNotFoundException {
		//putanja foldera je  : /images/posts OR /images/userprofiles i bice dobije spolja
		
		String fullfilepath=folderpath+File.separator+filename;
		InputStream is=new FileInputStream(fullfilepath);
		return is;
	}

}
