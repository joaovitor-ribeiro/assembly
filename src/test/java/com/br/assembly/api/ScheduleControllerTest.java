package com.br.assembly.api;

import static io.restassured.RestAssured.given;

import java.util.List;

import org.apache.http.HttpStatus;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.br.assembly.controller.schedule.ScheduleController;
import com.br.assembly.dto.schedule.ScheduleRequest;
import com.br.assembly.dto.schedule.ScheduleResponse;
import com.br.assembly.service.schedule.ScheduleService;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;


@WebMvcTest(ScheduleController.class)
public class ScheduleControllerTest extends RestAssuredTest {
	
	@MockBean
	private ScheduleService service;
	
	@Test
	@DisplayName("Create schedule with description null")
	public void testCreateScheduleDescriptionNull() {
		ScheduleRequest requestBody = new ScheduleRequest(null);
		
		given()
			.contentType(ContentType.JSON)
			.body(requestBody)
		.when()
			.post("schedule/create")
		.then()
			.statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
	}
	
	@Test
	@DisplayName("Create schedule with description empty")
	public void testCreateScheduleDescriptionEmpty() {
		ScheduleRequest requestBody = new ScheduleRequest("");
		
		given()
			.contentType(ContentType.JSON)
			.body(requestBody)
		.when()
			.post("schedule/create")
		.then()
			.statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
	}
	
	@Test
	@DisplayName("Create schedule")
	public void testCreateSchedule() {
		ScheduleRequest requestBody = new ScheduleRequest("Pauta");
		
		Mockito.when(service.create(requestBody)).thenReturn(getSchedulesResponse().get(0));
		
		RestAssuredMockMvc.given()
						  		.contentType(ContentType.JSON)
						  		.body(requestBody)
						   .when()
								.post("schedule/create")
						   .then()
								.statusCode(HttpStatus.SC_CREATED);
	}
	
	@Test
	@DisplayName("List all schedules")
	public void testListAllSchedules() {
		
		Mockito.when(service.listAll()).thenReturn(getSchedulesResponse());
		
		RestAssuredMockMvc.given()
						  		.contentType(ContentType.JSON)
						  .when()
						  		.get("schedule/list/all")
						  .then()
						  		.statusCode(HttpStatus.SC_OK);
	}
	
	@Test
	@DisplayName("List one schedule")
	public void testListOneSchedules() {
		ObjectId id = new ObjectId("65c6cc805f61400459180785");
		
		Mockito.when(service.listOne(id)).thenReturn(getSchedulesResponse().get(0));
		
		RestAssuredMockMvc.given()
						  		.contentType(ContentType.JSON)
						  .when()
						  		.get("schedule/list/" + id.toHexString())
						  .then()
						  		.statusCode(HttpStatus.SC_OK);
	}
	
	
	private static List<ScheduleResponse> getSchedulesResponse() {
		return List.of( new ScheduleResponse("65c6cc805f61400459180785", "Schedule 1"), 
						new ScheduleResponse("65c6cc805f61400459180788", "Schedule 2") 
					  );
	}

}
