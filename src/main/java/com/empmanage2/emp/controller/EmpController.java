package com.empmanage2.emp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.empmanage2.emp.entities.Employee;
import com.empmanage2.emp.service.EmpService;
import com.empmanage2.emp.service.S3BucketService;

@RestController
@RequestMapping("/emp")
@CrossOrigin("*")
public class EmpController {
	
	@Autowired
	private EmpService empService;
	

	
	@PostMapping("/")
	public Employee addEmp(@RequestBody Employee emp) {
		return empService.addEmp(emp);
	}
	
	@GetMapping("/")
	public List<Employee> getAllEmployee(){
		return empService.getAllEmployee();
	}
	
	@GetMapping("/{emailid}")
	public Employee getEmpByEmailid(@PathVariable String emailid) {
		return empService.getEmpByEmailid(emailid);
	}
	
	@DeleteMapping("/{emailid}")
	public String deleteEmp(@PathVariable String emailid) {
		return empService.deleteEmp(emailid);
	}
	
	@DeleteMapping("/all")
	public void deleteAll() {
		empService.deleteAll();
	}
	
	

}
