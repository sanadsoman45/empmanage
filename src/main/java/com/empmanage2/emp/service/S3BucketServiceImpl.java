package com.empmanage2.emp.service;

import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.CreateBucketConfiguration;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketResponse;
import software.amazon.awssdk.services.s3.model.PublicAccessBlockConfiguration;
import software.amazon.awssdk.services.s3.model.PutBucketPolicyRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutPublicAccessBlockRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Service
public class S3BucketServiceImpl implements S3BucketService {

	@Autowired
	private S3Client s3client;

	// property to store the policies to be set on the bucket
	String policy_text;

	@Value("${aws.s3.bucket}")
	String bucketName;

	@Value("${aws.region}")
	String region;

	@Override
	public String createBucketPolicy() {
		String policy = null;
		try {
			String bucketArn = "arn:aws:s3:::" + bucketName;

			// Define the bucket policy
			policy = "{\n" + "  \"Version\":\"2012-10-17\",\n" + "  \"Statement\":[\n" + "    {\n"
					+ "      \"Sid\":\"AllowPictureUploads\",\n" + "      \"Effect\":\"Allow\",\n"
					+ "      \"Principal\": \"*\",\n" + "      \"Action\":[\"s3:PutObject\"],\n"
					+ "      \"Resource\":[\"" + bucketArn + "/*\"],\n" + "      \"Condition\": {\n"
					+ "        \"StringEquals\": {\n" + "          \"s3:x-amz-acl\": \"public-read\"\n" + "        }\n"
					+ "      }\n" + "    },\n" + "    {\n" + "      \"Sid\":\"AllowPictureRetrieval\",\n"
					+ "      \"Effect\":\"Allow\",\n" + "      \"Principal\": \"*\",\n"
					+ "      \"Action\":[\"s3:GetObject\"],\n" + "      \"Resource\":[\"" + bucketArn + "/*\"]\n"
					+ "    },\n" + "    {\n" + "      \"Sid\":\"AllowPictureDeletion\",\n"
					+ "      \"Effect\":\"Allow\",\n" + "      \"Principal\": \"*\",\n"
					+ "      \"Action\":[\"s3:DeleteObject\"],\n" + "      \"Resource\":[\"" + bucketArn + "/*\"]\n"
					+ "    }\n" + "  ]\n" + "}";

		} catch (AwsServiceException e) {
			e.printStackTrace();
		}
		return policy;
	}

	public void configurePublicAccess() {
		try {
			PublicAccessBlockConfiguration config = PublicAccessBlockConfiguration.builder().blockPublicAcls(false)
					.ignorePublicAcls(false).blockPublicPolicy(false).restrictPublicBuckets(false).build();

			s3client.putPublicAccessBlock(PutPublicAccessBlockRequest.builder().bucket(bucketName)
					.publicAccessBlockConfiguration(config).build());
		}

		catch (AwsServiceException e) {
			e.printStackTrace();
		}
	}

	public Bucket createBucket() {
		Bucket b = null;

		try {
			HeadBucketResponse headBucketResponse = s3client
					.headBucket(HeadBucketRequest.builder().bucket(bucketName).build());

			if (headBucketResponse.sdkHttpResponse().isSuccessful()) {
				System.out.println("Bucket Exists");
				throw new RuntimeException("Bucket Already Exists with Name: " + bucketName);
			}
		} catch (S3Exception e) {
			if (e.statusCode() != 404) {
				// If the exception is not due to the bucket not found (404),
				// rethrow the exception or handle it accordingly
				throw e;
			}
		}

		CreateBucketRequest createBucketRequest = CreateBucketRequest.builder().bucket(bucketName)
				.createBucketConfiguration(CreateBucketConfiguration.builder().locationConstraint(region).build())
				.build();

		s3client.createBucket(createBucketRequest);
		b = Bucket.builder().name(bucketName).build();
		configurePublicAccess();
		setBucketPolicy();

		return b;
	}

	public void setBucketPolicy() {

		try {
			String policyText = createBucketPolicy();
			if (!bucketName.isEmpty() && !policyText.isEmpty()) {
				PutBucketPolicyRequest setBucketPolicyRequest = PutBucketPolicyRequest.builder().bucket(bucketName)
						.policy(policyText).build();

				s3client.putBucketPolicy(setBucketPolicyRequest);
			} else {
				throw new RuntimeException("Bucket doesn't exist");
			}
		} catch (AwsServiceException e) {
			e.printStackTrace();
		}

	}

	@Override
	public String putObject(String emailId, String filePath) {
		String keyName = null;

		try {

			createBucket();

			keyName = "document/" + emailId + "_" + UUID.randomUUID().toString() + ".jpg";
			PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket(bucketName).key(keyName).build();

			s3client.putObject(putObjectRequest, Paths.get(filePath));

		} catch (AwsServiceException e) {
			e.printStackTrace();
		}

		return keyName;
	}

}
