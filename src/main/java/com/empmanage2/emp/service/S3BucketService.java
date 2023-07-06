package com.empmanage2.emp.service;

import com.amazonaws.services.s3.model.Bucket;

public interface S3BucketService {
	
	//Create Policies that needs to be set for our bucket
	public String getPublicReadPolicy();
	
	//Create bucket
	public Bucket createBucket();
	
	//Set the policies created to the bucket
	public void setBucketPolicy();
	
	public String putObject(String emailid, String filePath);
	
	public void configurePublicAccess();

}
