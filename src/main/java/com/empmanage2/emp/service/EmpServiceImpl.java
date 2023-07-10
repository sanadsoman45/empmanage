package com.empmanage2.emp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import com.empmanage2.emp.dao.EmployeeDao;
import com.empmanage2.emp.entities.Employee;


@Service
public class EmpServiceImpl implements EmpService {

	@Autowired
	private EmployeeDao empDao;
	
	@Autowired
	private S3BucketService s3Service;

	
	
	@Value("${aws.s3.bucket}") 
	String bucketName;

	public Employee addEmp(Employee emp) {

		Employee result = empDao.findByemailid(emp.getEmailid());

		if (result != null) {
			throw new RuntimeException("Employee Already Exists");
		}
		emp = empDao.save(emp);
		return emp;
	}

	@Override
	public List<Employee> getAllEmployee() {
		List<Employee> result = empDao.findAll();
		return result;
	}

	@Override
	public Employee getEmpByEmailid(String emailid) {
		Employee emp = empDao.findByemailid(emailid);
		if (emp == null) {
			throw new RuntimeException("No Employee Found with emailid: " + emailid);
		}
		return emp;
	}

	@Override
	public String deleteEmp(String emailid) {

		String message = "Records Deleted Successfully";

		if (checkEmp(emailid)) {
			empDao.delete(empDao.findByemailid(emailid));
		} else {
			message = "Records Not Deleted";
		}

		return message;
	}

	public boolean checkEmp(String emailid) {
		Employee emp = empDao.findByemailid(emailid);
		if (emp == null) {
			return false;
		}
		return true;
	}

	@Override
	public void deleteAll() {
		empDao.deleteAll();

	}

	@Override
	public void updateUser(Employee emp) {
		
		if(empDao.findByemailid(emp.getEmailid()) == null) {
			throw new RuntimeException("Employee not Found");
		}
		empDao.save(emp);
		
		
	}

	
	
	

//	public String uploadFileToS3(MultipartFile file) throws IOException {
//        String objectKey = generateUniqueObjectKey(file.getOriginalFilename());
//        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
//                .bucket(bucketName)
//                .key(objectKey)
//                .build();
//
//        byte[] fileBytes = file.getBytes();
//        PutObjectResponse response = s3Client.putObject(putObjectRequest, RequestBody.fromBytes(fileBytes));
//
//        return generateImageUrl(objectKey);
//    }
//	
//	private String generateUniqueObjectKey(String originalFilename) {
//        // Generate a unique object key as per your requirement
//        // For example, using a UUID
//        String uniqueId = UUID.randomUUID().toString();
//        String fileExtension = extractFileExtension(originalFilename);
//        return uniqueId + "." + fileExtension;
//    }

}
