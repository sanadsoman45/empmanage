package com.empmanage2.emp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empmanage2.emp.dao.EmployeeDao;
import com.empmanage2.emp.entities.Employee;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpServiceImpl implements EmpService{
	
	@Autowired
	private EmployeeDao empDao;
	
	public Employee addEmp(Employee emp) {
		
		
		Employee result = empDao.findByemailid(emp.getEmailid());
		
		if(result != null) {
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
		if(emp == null) {
			throw new RuntimeException("No Employee Found with emailid: "+emailid);
		}
		return emp;
	}

	@Override
	public String deleteEmp(String emailid) {
		
		String message = "Records Deleted Successfully";
		
		if(checkEmp(emailid)) {
			empDao.delete(empDao.findByemailid(emailid));
		}
		else {
			message = "Records Not Deleted";
		}
		
		return message;
	}
	
	
	public boolean checkEmp(String emailid) {
		Employee emp = empDao.findByemailid(emailid);
		if(emp == null) {
			return false;
		}
		return true;
	}

	@Override
	public void deleteAll() {
		empDao.deleteAll();
		
	}

}
