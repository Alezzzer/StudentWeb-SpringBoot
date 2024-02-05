package com.studentweb.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface FajlService {
	String uploadImage(String folderpath, MultipartFile file) throws IOException;
	InputStream serveImage(String folderpath, String filename) throws FileNotFoundException;
}
