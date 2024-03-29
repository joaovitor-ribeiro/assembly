package com.br.assembly.api;

import static io.restassured.RestAssured.given;

import org.apache.http.HttpStatus;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.br.assembly.controller.votingsession.VotingSessionController;
import com.br.assembly.dto.votingsession.VoteRequest;
import com.br.assembly.dto.votingsession.VoteResultResponse;
import com.br.assembly.dto.votingsession.VotingSessionRequest;
import com.br.assembly.dto.votingsession.VotingSessionResponse;
import com.br.assembly.service.votingsession.VotingSessionService;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

@WebMvcTest(VotingSessionController.class)
public class VotingSessionControllerTest extends RestAssuredTest {
	
	@MockBean
	private VotingSessionService service;
	
	@Test
	@DisplayName("Open session without id")
	public void testCreateScheduleDescriptionNull() {
		VotingSessionRequest requestBody = new VotingSessionRequest(null, null);
		
		given()
			.contentType(ContentType.JSON)
			.body(requestBody)
		.when()
			.post("session/open")
		.then()
			.statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
	}
	
	@Test
	@DisplayName("Open session schedule")
	public void testOpenSession() {
		ObjectId idSchedule = new ObjectId("65c6cc805f61400459180785");
		ObjectId idVotingSession = new ObjectId("65c6cc805f61400459180877");
		
		VotingSessionRequest  requestBody = new VotingSessionRequest(idSchedule, null);
		VotingSessionResponse response    = new VotingSessionResponse(idVotingSession.toHexString());
		
		Mockito.when(service.openSession(Mockito.any(VotingSessionRequest.class))).thenReturn(response);
		
		RestAssuredMockMvc.given()
						  		.contentType(ContentType.JSON)
						  		.body(requestBody)
						  .when()
						  		.post("session/open")
						  .then()
						  		.statusCode(HttpStatus.SC_CREATED);
	}
	
	@Test
	@DisplayName("Vote without id session")
	public void testVoteWithoutId() {
		VoteRequest requestBody = new VoteRequest(null, "94858686345", true);
		
		given()
			.contentType(ContentType.JSON)
			.body(requestBody)
		.when()
			.put("session/submit/vote")
		.then()
			.statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
	}
	
	@Test
	@DisplayName("Vote without CPF")
	public void testVoteWithoutCpf() {
		ObjectId idVotingSession = new ObjectId("65ca598482743337b7440e18");
		VoteRequest requestBody  = new VoteRequest(idVotingSession, null, true);
		
		given()
			.contentType(ContentType.JSON)
			.body(requestBody)
		.when()
			.put("session/submit/vote")
		.then()
			.statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
	}
	
	@Test
	@DisplayName("Vote without vote")
	public void testVoteWithoutVote() {
		ObjectId idVotingSession = new ObjectId("65ca598482743337b7440e18");
		VoteRequest requestBody  = new VoteRequest(idVotingSession, "94858686345", null);
		
		given()
			.contentType(ContentType.JSON)
			.body(requestBody)
		.when()
			.put("session/submit/vote")
		.then()
			.statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
	}
	
	@Test
	@DisplayName("Result vote")
	public void testResultVote() {
		ObjectId idVotingSession = new ObjectId("65ca598482743337b7440e18");
		
		VoteResultResponse response  = new VoteResultResponse(10, 6, 4);
		
		Mockito.when(service.resultVote(idVotingSession)).thenReturn(response);
		
		RestAssuredMockMvc.given()
						  		.contentType(ContentType.JSON)
						  .when()
						  		.get("session/result/vote/" + idVotingSession.toHexString())
						  .then()
						  		.statusCode(HttpStatus.SC_OK);
	}
	
}
