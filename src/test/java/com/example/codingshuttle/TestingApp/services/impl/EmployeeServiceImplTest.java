package com.example.codingshuttle.TestingApp.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase.Replace;
import org.springframework.context.annotation.Import;

import com.example.codingshuttle.TestingApp.TestContainerConfiguration;
import com.example.codingshuttle.TestingApp.dto.EmployeeDto;
import com.example.codingshuttle.TestingApp.entities.Employee;
import com.example.codingshuttle.TestingApp.repositories.EmployeeRepository;


@Import(TestContainerConfiguration.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {
	
	@Mock
	private EmployeeRepository employeeRepository;
	
	@Spy
	private ModelMapper modelMapper;
	
	@InjectMocks
	private EmployeeServiceImpl employeeService;
	
	private Employee mockEmployee;
	private EmployeeDto mockEmployeeDto;
	
	@BeforeEach
	void setup() {
		mockEmployee = Employee.builder().id(1L).name("Ram").email("Ram@gmail.com").salary(200L).build();
		mockEmployeeDto = modelMapper.map(mockEmployee, EmployeeDto.class);
	}

	@Test
	void testGetEmployeeById_WhenEmployeeIdIsPresent_ThenReturnEmployeeDto() {
		Long id = mockEmployee.getId();
		//assign 
		when(employeeRepository.findById(1L)).thenReturn(Optional.of(mockEmployee));
		
		//act
		EmployeeDto employeeDto = employeeService.getEmployeeById(id);
		//assert
		assertThat(employeeDto.getId()).isEqualTo(1L);
		assertThat(employeeDto.getEmail()).isEqualTo(mockEmployee.getEmail());
		verify(employeeRepository).findById(id);
		verify(employeeRepository, times(1)).findById(id);
	}
	
	@Test
	void testCreateNewEmployee_WhenValidEmployee_ThenCreateNewEmployee() {
		//assign
		when(employeeRepository.findByEmail(anyString())).thenReturn(List.of());
		when(employeeRepository.save(any(Employee.class))).thenReturn(mockEmployee);
		//act
		EmployeeDto employeeDto = employeeService.createNewEmployee(mockEmployeeDto);
		
		//assert
		assertThat(employeeDto).isNotNull();
		assertThat(employeeDto.getEmail()).isEqualTo(mockEmployeeDto.getEmail());
		ArgumentCaptor<Employee> employeeArgumentCaptor = ArgumentCaptor.forClass(Employee.class);
		verify(employeeRepository).save(employeeArgumentCaptor.capture());
		
		Employee capturedEmployee = employeeArgumentCaptor.getValue();
		assertThat(capturedEmployee.getEmail()).isEqualTo(mockEmployee.getEmail());
	}
}
