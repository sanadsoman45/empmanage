package com.empmanage2.emp.service;

import java.io.File;
import java.util.UUID;

import javax.xml.crypto.dsig.keyinfo.KeyName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.SetPublicAccessBlockRequest;
import com.amazonaws.auth.policy.Policy;
import com.amazonaws.auth.policy.Principal;
import com.amazonaws.auth.policy.Resource;
import com.amazonaws.auth.policy.Statement;
import com.amazonaws.auth.policy.actions.S3Actions;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.PublicAccessBlockConfiguration;

@Service
public class S3BucketServiceImpl implements S3BucketService {

	@Autowired
	private AmazonS3 s3client;

	// property to store the policies to be set on the bucket
	String policy_text;

	@Value("${aws.s3.bucket}")
	String bucketName;

	@Override
	public String getPublicReadPolicy() {
		Policy bucket_policy = new Policy()
				.withStatements(new Statement(Statement.Effect.Allow).withPrincipals(Principal.AllUsers)
						.withActions(S3Actions.GetObject, S3Actions.DeleteObject, S3Actions.PutObject)
						.withResources(new Resource("arn:aws:s3:::" + bucketName + "/*")));
		return bucket_policy.toJson();
	}

	public void configurePublicAccess() {
		PublicAccessBlockConfiguration config = new PublicAccessBlockConfiguration().withBlockPublicAcls(false)
				.withIgnorePublicAcls(false).withBlockPublicPolicy(false).withRestrictPublicBuckets(false);
		s3client.setPublicAccessBlock(new SetPublicAccessBlockRequest().withBucketName(bucketName)
				.withPublicAccessBlockConfiguration(config));
	}

	public Bucket createBucket() {
		Bucket b = null;
		if (s3client.doesBucketExistV2(bucketName)) {
			System.out.println("Bucket Exists");
			throw new RuntimeException("Bucket Already Exists with Name: " + bucketName);
		} else {
			try {
				b = s3client.createBucket(bucketName);
				configurePublicAccess();
				setBucketPolicy();
			} catch (AmazonS3Exception e) {
				System.err.println(e.getErrorMessage());
			}
		}
		return b;
	}

	public void setBucketPolicy() {

		try {
			policy_text = getPublicReadPolicy();
			if (!bucketName.isEmpty() && !policy_text.isEmpty()) {
				s3client.setBucketPolicy(bucketName, policy_text);
			} else {
				throw new RuntimeException("Bucket doesn't exist");
			}

		} catch (AmazonServiceException e) {
			System.err.println(e.getErrorMessage());
		}

	}

	@Override
	public String putObject(String emailid, String filePath) {

		PutObjectResult result;
		String keyName = null;

		try {
			createBucket();
			keyName = "document/" + emailid + "_" + UUID.randomUUID().toString() + ".jpg";
			result = s3client.putObject(bucketName, keyName, new File(filePath));
		} catch (AmazonServiceException exception) {
			exception.printStackTrace();
		}

		return keyName;
	}

}
