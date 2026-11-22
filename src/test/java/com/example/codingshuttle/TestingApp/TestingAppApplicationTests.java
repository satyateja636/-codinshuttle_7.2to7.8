package com.example.codingshuttle.TestingApp;

import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class TestingAppApplicationTests {
	
	@BeforeEach //this method method will before each test case
	void setUp() {
		log.info("Starting the method, setting up config");
	}
	
	@AfterEach //this mwthod will run after each test case
	void tearDown() {
		log.info("tearing down the method");
	}
	
	@BeforeAll //thismethod will run only once before all the testcases
	static void setUpOnce() {
		log.info("Setup Once...");
	}
	
	@AfterAll //this method will run only once after all the testcases
	static void tearDownOnce() {
		log.info("tearing down once");
	}

	@Test
	@Disabled //this annotation is used to disable this testcase from being run
	void contextLoads() {
	}
	
	@Test
	@DisplayName("displayTestNameOne") //this annotatio is used to display the custom name for the testcase
	void testNumberOne() {
		log.info("test one is run");
		int a = 5;
		int b = 3;
		int result = a+b;
//		Assertions.assertEquals(result, 8);
		Assertions.assertThat(result).isEqualTo(8).isCloseTo(9,  Offset.offset(1)); 
	}
	
	@Test
	void testNumberTwo() {
		log.info("test two is run");
		Assertions.assertThat("Apple").isEqualTo("Apple").startsWith("App").endsWith("le").hasSize(5);
	}  
	
	@Test
	void testDivideTwoNumbers_whenDenominatorIsZero_ThenAithematicException() {
		int a = 5;
		int b = 0;
		
		Assertions.assertThatThrownBy(() -> divideTwoNumbers(a,b)).isInstanceOf(ArithmeticException.class).hasMessage("tried to divbide by zero");
		
		
	}
	
	double divideTwoNumbers(int a, int b) {
		try {
			return a/b;
		}catch(ArithmeticException e) {
			log.info("Arithmetic exception occured: "+e.getLocalizedMessage());
			throw new ArithmeticException("tried to divbide by zero");
		}
	}
}
