package com.empmanage2.emp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.empmanage2.emp.service.S3BucketService;

@RestController
@RequestMapping("/s3")
@CrossOrigin("*")
public class S3BucketController {
	

	@Autowired
	private S3BucketService bucketService;
	
	@PostMapping("/createbucket")
	public void createBucket() {
		bucketService.createBucket();
	}
	
	@PostMapping("/")
	public String putObject(@RequestBody MultipartFile file) {
		System.out.println("call breached");
		return bucketService.putObject(file);
	}
	
}
