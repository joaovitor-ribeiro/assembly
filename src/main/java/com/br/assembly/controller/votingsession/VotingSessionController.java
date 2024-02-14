package com.br.assembly.controller.votingsession;

import java.net.URI;
import java.net.URISyntaxException;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.br.assembly.dto.votingsession.VoteRequest;
import com.br.assembly.dto.votingsession.VoteResultResponse;
import com.br.assembly.dto.votingsession.VotingSessionRequest;
import com.br.assembly.dto.votingsession.VotingSessionResponse;
import com.br.assembly.service.votingsession.VotingSessionService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping ("assembly/v1/session")
@CrossOrigin
@Tag(name = "Voting Session", description = "Voting session control")
@ApiResponse(responseCode = "500", description = "Internal Error")
public class VotingSessionController {
	
	private final VotingSessionService votingSessionService;
	
	@Autowired
	public VotingSessionController(VotingSessionService votingSessionService) {
		this.votingSessionService = votingSessionService;
	}
	
	@ResponseStatus(code = HttpStatus.CREATED)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Voting session successfully created") })
	@RequestMapping(method = RequestMethod.POST, path = "/open")
	public ResponseEntity<VotingSessionResponse> openSession(@RequestBody @Valid VotingSessionRequest sessionRecord) throws URISyntaxException {
		VotingSessionResponse response = votingSessionService.openSession(sessionRecord);
		
		return ResponseEntity.created(new URI(response.id())).body(response);
	}
	
	@ResponseStatus(code = HttpStatus.OK)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Vote successfully submit") })
	@RequestMapping(method = RequestMethod.PUT, path = "/submit/vote")
	public ResponseEntity<Void> voteSubmit(@RequestBody @Valid VoteRequest voteRecord) throws URISyntaxException {
		votingSessionService.voteSubmit(voteRecord);
		
		return ResponseEntity.ok(null);
	}
	
	@ResponseStatus(code = HttpStatus.OK)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Result vote") })
	@RequestMapping(method = RequestMethod.GET, path = "/result/vote/{idSession}")
	public ResponseEntity<VoteResultResponse> resultVote(@PathVariable ObjectId idSession) throws URISyntaxException {
		VoteResultResponse response = votingSessionService.resultVote(idSession);
		
		return ResponseEntity.ok(response);
	}

}
