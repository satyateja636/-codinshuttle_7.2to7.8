package com.example.codingshuttle.TestingApp.controllers;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;




import com.example.codingshuttle.TestingApp.dto.EmployeeDto;
import com.example.codingshuttle.TestingApp.entities.Employee;
import com.example.codingshuttle.TestingApp.repositories.EmployeeRepository;


class EmployeeControllerTestIT extends AbstractIntegrationTest{

	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	private Employee testEmployee;
	
	private EmployeeDto testEmployeeDto;
	
	@BeforeEach
	void setUp()
	{
		testEmployee =Employee.builder().name("Hanu").email("Hanu@gmail.com").salary(200L).build();
		testEmployeeDto = EmployeeDto.builder().id(1L).name("Hanu").email("Hanu@gmail.com").salary(200L).build();
		employeeRepository.deleteAll();
		
	}
	@Test
	void testGetEmployeeById_Success() {
		Employee savedEmployee = employeeRepository.save(testEmployee);
		webTestClient.get().uri("/employees/{id}", savedEmployee.getId())
		.exchange()
		.expectStatus().isOk()
		.expectBody()
		.jsonPath("$.id").isEqualTo(savedEmployee.getId())
		.jsonPath("$.email").isEqualTo(savedEmployee.getEmail());
//		.isEqualTo(testEmployeeDto);
//		.value(employeeDto ->{
//			assertThat(employeeDto.getEmail()).isEqualTo(savedEmployee.getEmail());
//			assertThat(employeeDto.getId()).isEqualTo(savedEmployee.getId());
//		});
	}
	
	@Test
	void testGetEmployeeById_Failure() {
		webTestClient.get()
		.uri("/employees/3")
		.exchange()
		.expectStatus().isNotFound();
	}
	
	@Test
	void testCreateNewEmployee_WhenEmployeeAlreadyExists_thenThrowException() {
		Employee savedEmployee = employeeRepository.save(testEmployee);
		webTestClient.post()
		.uri("/employees")
		.bodyValue(testEmployeeDto)
		.exchange()
		.expectStatus().is5xxServerError();
	}
	
	@Test
	void testCreateNewEmployee_WhenEmployeeDoesNotExists_thenCreateEmployee() {

		webTestClient.post()
		.uri("/employees")
		.bodyValue(testEmployeeDto)
		.exchange()
		.expectStatus().isCreated()
		.expectBody()
		.jsonPath("$.email").isEqualTo(testEmployeeDto.getEmail())
		.jsonPath("$.name").isEqualTo(testEmployeeDto.getName());
	}
	
	@Test
	void testUpdateEmployee_whenEmployeeDoesNotExists_thenThrowException() {
		webTestClient.put()
		.uri("/employee/999")
		.bodyValue(testEmployeeDto)
		.exchange()
		.expectStatus()
		.isNotFound();
	}
	
	@Test
	void testUpdateEmployee_whenAttemptingToUpdateTheEmail_thenThrowException() {
		Employee savedEmployee =employeeRepository.save(testEmployee);
		testEmployeeDto.setName("Random Name");
		testEmployeeDto.setEmail("random@gmail.com");
		webTestClient.put()
		.uri("/employees/{id}", savedEmployee.getId())
		.bodyValue(testEmployeeDto)
		.exchange()
		.expectStatus().is5xxServerError();
	}
	
	@Test
	void testUpdateEmployee_whenEmployeeIsValid_thenUpdateEmployee() {
		Employee savedEmployee =employeeRepository.save(testEmployee);
		testEmployeeDto.setName("Random Name");
		testEmployeeDto.setSalary(200L);
		webTestClient.put()
		.uri("/employees/{id}", savedEmployee.getId())
		.bodyValue(testEmployeeDto)
		.exchange()
		.expectStatus().isOk()
		.expectBody(EmployeeDto.class)
		.isEqualTo(testEmployeeDto);
	}
	
	@Test
	void testDeleteEmployee_whenEmployeeDoesNotExists_thenThrowException() {
		webTestClient.delete()
		.uri("/employee/1")
		.exchange()
		.expectStatus().isNotFound();
	}

	@Test
	void testDeleteEmployee_whenEmployeetExists_thenDeleteEmployee() {
		Employee savedEmployee =employeeRepository.save(testEmployee);

		webTestClient.delete()
		.uri("/employee/{id}", savedEmployee.getId())
		.exchange()
		.expectStatus().isNoContent()
		.expectBody(Void.class);
	}
}
