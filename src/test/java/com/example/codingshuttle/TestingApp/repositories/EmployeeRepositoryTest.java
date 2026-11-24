package com.example.codingshuttle.TestingApp.repositories;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase.Replace;
import org.springframework.context.annotation.Import;

import com.example.codingshuttle.TestingApp.TestContainerConfiguration;
import com.example.codingshuttle.TestingApp.entities.Employee;

@Import(TestContainerConfiguration.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class EmployeeRepositoryTest {

	@Autowired
	private EmployeeRepository employeeRepository;

	private Employee employee;

	@BeforeEach
	public void setUp() {
		employee = Employee.builder().id(2L).name("Ram").email("Ram@gmail.com").salary(108L).build();
	}

	@Test
	void testFindByEmail_whenEmailIsValid_thenReturnEmployee() {
		// Arrange, Given
		employeeRepository.save(employee);
		
		// Act, When
		List<Employee> employeeList = employeeRepository.findByEmail(employee.getEmail());
		// Assert, Then
		Assertions.assertThat(employeeList).isNotNull();
		Assertions.assertThat(employeeList).isNotEmpty();
		Assertions.assertThat(employeeList.get(2).getEmail()).isEqualTo(employee.getEmail());
	}

	@Test
	void testFindByEmail_whenEmailIsNotFound_thenReturnEmptyEmployeeList() {

		//Given
		String email = "notpresent@gmail.com";
		//when
		List<Employee> employeeList = employeeRepository.findByEmail(email);
		//Then
		Assertions.assertThat(employeeList).isNotNull();
		Assertions.assertThat(employeeList).isEmpty();
	}
}
