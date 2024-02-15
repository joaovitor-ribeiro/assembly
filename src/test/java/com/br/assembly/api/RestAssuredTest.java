package com.br.assembly.api;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.port;

import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import io.restassured.RestAssured;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

public class RestAssuredTest  {
	
	private static final String  BASEURI = "http://localhost/assembly/v1/";
	private static final Integer PORT    = 8080;
	
	@Autowired
	private MockMvc mockMvc;
	
	@BeforeClass
    public static void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
	
	@BeforeEach
	public void beforeEach() {
		baseURI = BASEURI;
		port = PORT;
		
		RestAssuredMockMvc.mockMvc(mockMvc);
		RestAssuredMockMvc.basePath = BASEURI;
	}
	

}
