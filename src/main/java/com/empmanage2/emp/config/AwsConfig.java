package com.empmanage2.emp.config;


import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
public class AwsConfig {

	
	@Value("${aws.region}")
	String region;
	
	
	
    @Bean
    public S3Client s3() {
    	
    	//As no username has been passed as a parameter, so it will fetch the default credentials from the credentials file. 
    	ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.create();
    	
        return S3Client.builder().region(Region.AP_SOUTH_1).credentialsProvider(credentialsProvider).build();
                

    }
    
    @Bean
    public CommonsMultipartResolver multpartResolver() {
    	CommonsMultipartResolver resolver = new CommonsMultipartResolver();
    	resolver.setMaxUploadSize(52428800);
    	return resolver;
    }
}