package com.empmanage2.emp.service;

import java.util.List;

import com.empmanage2.emp.entities.Employee;

import com.empmanage2.emp.entities.Employee;

public interface EmpService {
	public Employee addEmp(Employee emp) ;

	public List<Employee> getAllEmployee();

	public Employee getEmpByEmailid(String emailid);

	public String deleteEmp(String emailid);

	public void deleteAll();
	
//	public void putObject(String bucketName);s
}
