package com.example.codingshuttle.TestingApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.example.codingshuttle.TestingApp.TestContainerConfiguration;

@AutoConfigureWebTestClient(timeout = "100000")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Import(TestContainerConfiguration.class)
public class AbstractIntegrationTest {


	@Autowired
	public WebTestClient webTestClient;
}
