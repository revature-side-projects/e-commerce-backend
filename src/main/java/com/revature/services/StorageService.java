package com.revature.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StorageService {

	@Value("$(application.bucket.name)")
	private String bucketName;
	
	@Autowired
	private AmazonS3 s3Client;
	
	public String uploadFile(MultipartFile file) {
		File fileObj = convertMultipartToFile(file);
		String fileName = file.getOriginalFilename();
		s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
		fileObj.delete();
		return "File uploaded: " + fileName;
	}
	
	private File convertMultipartToFile(MultipartFile file) {
		File convertFile = new File(file.getOriginalFilename());
		try(FileOutputStream fos = new FileOutputStream(convertFile)){
			fos.write(file.getBytes());
		}catch(IOException e){
			log.error("Error converting multipart file to file ", e);
		}
		return convertFile;
	}
}
