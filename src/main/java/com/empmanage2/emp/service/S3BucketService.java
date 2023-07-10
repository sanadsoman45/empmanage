package com.empmanage2.emp.service;

import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.services.s3.model.Bucket;

public interface S3BucketService {
	
	//Create Policies that needs to be set for our bucket
	public String createBucketPolicy();
	
	//Create bucket
	public Bucket createBucket();
	
	//Set the policies created to the bucket
	public void setBucketPolicy();
	
	public String putObject(MultipartFile file);
	
	public void configurePublicAccess();

}
