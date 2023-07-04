package com.empmanage2.emp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.empmanage2.emp.entities.Employee;

public interface EmployeeDao extends JpaRepository<Employee, Integer>{

	public Employee findByemailid(String emailid);
	
}
