package com.empmanage2.emp.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int empid;
	
	private String emailid;
	
	private String firstName;
	
	private String midName;
	
	private String lastName;
	
	private String dob;
	
	private String gender;
	
	private String doj;
	
	private String retireDate;
	
	private String bloodgroup;
	
	private String state;
	
	private String district;
	
	private String imageUrl;

	public Employee() {
		super();
	}

	public Employee(int empid, String emailid, String firstName, String midName, String lastName, String dob,
			String gender, String doj, String retireDate, String bloodgroup, String state, String district,
			String imageUrl) {
		super();
		this.empid = empid;
		this.emailid = emailid;
		this.firstName = firstName;
		this.midName = midName;
		this.lastName = lastName;
		this.dob = dob;
		this.gender = gender;
		this.doj = doj;
		this.retireDate = retireDate;
		this.bloodgroup = bloodgroup;
		this.state = state;
		this.district = district;
		this.imageUrl = imageUrl;
	}



	public int getEmpid() {
		return empid;
	}

	public void setEmpid(int empid) {
		this.empid = empid;
	}

	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMidName() {
		return midName;
	}

	public void setMidName(String midName) {
		this.midName = midName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDoj() {
		return doj;
	}

	public void setDoj(String doj) {
		this.doj = doj;
	}

	public String getRetireDate() {
		return retireDate;
	}

	public void setRetireDate(String retireDate) {
		this.retireDate = retireDate;
	}

	public String getBloodgroup() {
		return bloodgroup;
	}

	public void setBloodgroup(String bloodgroup) {
		this.bloodgroup = bloodgroup;
	}



	public String getState() {
		return state;
	}



	public void setState(String state) {
		this.state = state;
	}



	public String getDistrict() {
		return district;
	}



	public void setDistrict(String district) {
		this.district = district;
	}
	
	
	public String getImageUrl() {
		return imageUrl;
	}



	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	
	

}
