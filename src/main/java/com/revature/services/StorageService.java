package com.revature.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.revature.exceptions.FileUploadException;
import com.revature.exceptions.MultipartFileConversionException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StorageService {

	// @Value("$(application.bucket.name)")
	private String bucketName = "revazon-image-bucket";

	@Autowired
	private AmazonS3 s3Client;

	public ResponseEntity<String> uploadFile(MultipartFile file) {
		String fileName = "";
		try {
			File fileObj = convertMultipartToFile(file);
			fileName = file.getOriginalFilename();
			s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
			if (fileObj.delete()) {
				log.info(fileName + "was deleted after sending to s3");
			}
		} catch (Exception e) {
			throw new FileUploadException(e.getMessage());
		}
		return new ResponseEntity<String>(fileName, HttpStatus.OK);
	}

	private File convertMultipartToFile(MultipartFile file) {
		File convertFile = new File(file.getOriginalFilename());
		try (FileOutputStream fos = new FileOutputStream(convertFile)) {
			fos.write(file.getBytes());
		} catch (IOException e) {
			throw new MultipartFileConversionException("Error converting multipart file to file");
		}
		return convertFile;
	}

	public String deleteFile(String filename) {
		s3Client.deleteObject(new DeleteObjectRequest(bucketName, filename));
		return "Deleted " + filename;
	}
}
