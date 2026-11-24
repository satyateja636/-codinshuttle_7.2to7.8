package com.example.codingshuttle.TestingApp.services;

import java.util.List;

import com.example.codingshuttle.TestingApp.dto.EmployeeDto;

public interface EmployeeService {

	EmployeeDto getEmployeeById(Long id);
	EmployeeDto createNewEmployee(EmployeeDto employeeDto);
	EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto);
	void deleteEmployeeById(Long id);
	List<EmployeeDto> getAllEmployees();
}
