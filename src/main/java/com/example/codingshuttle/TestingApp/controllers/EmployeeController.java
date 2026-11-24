package com.example.codingshuttle.TestingApp.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.codingshuttle.TestingApp.dto.EmployeeDto;
import com.example.codingshuttle.TestingApp.services.EmployeeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

	
	private final EmployeeService employeeService;

	
	
	@GetMapping("/{id}")
	public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long id) {
		EmployeeDto employeeDto = employeeService.getEmployeeById(id);
		return ResponseEntity.ok(employeeDto);
	}
	  
	@GetMapping("/getAllEmployees")
	public List<EmployeeDto> getAllEmployees(){
		return employeeService.getAllEmployees();
	}

	@PostMapping
	public ResponseEntity<EmployeeDto> createNewEmployee(@RequestBody EmployeeDto employeeDto) {
		EmployeeDto createdEmployee = employeeService.createNewEmployee(employeeDto);
		return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
	}

	@PutMapping(path = "/{id}")
	public ResponseEntity<EmployeeDto> updateEmployeeById(@RequestBody EmployeeDto employeeDTO, @PathVariable Long id) {
		EmployeeDto updatedEmployee = employeeService.updateEmployee(id, employeeDTO);
		return ResponseEntity.ok(updatedEmployee);
	}	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Boolean>  deleteEmployeeById(@PathVariable Long id) {
		employeeService.deleteEmployeeById(id);
		return ResponseEntity.noContent().build();
	}

}
