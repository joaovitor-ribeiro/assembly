package com.br.assembly.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.br.assembly.dto.votingsession.VoteRequest;
import com.br.assembly.dto.votingsession.VoteResultResponse;
import com.br.assembly.dto.votingsession.VotingSessionRequest;
import com.br.assembly.dto.votingsession.VotingSessionResponse;
import com.br.assembly.exception.ValidationException;
import com.br.assembly.model.schedule.Schedule;
import com.br.assembly.model.votingsession.AssociatedVotes;
import com.br.assembly.model.votingsession.VotingSession;
import com.br.assembly.repository.schedule.ScheduleRepository;
import com.br.assembly.repository.votingsession.VotingSessionRepository;
import com.br.assembly.service.votingsession.VotingSessionService;
import com.br.assembly.validators.votingsession.VoteValidation;

@SpringBootTest
public class VotingSessionServiceTest {
	
	@Mock
	private VotingSessionRepository sessionRepository;
	
	@Mock
	private ScheduleRepository      scheduleRepository;
	
	@Autowired
	private VoteValidation          voteValidation;
	
	private VotingSessionService    service;
	
	@BeforeEach
	public void before() {
		MockitoAnnotations.openMocks(this);
		this.service = new VotingSessionService(sessionRepository, scheduleRepository, voteValidation);
	}
	
	@Test
	@DisplayName("Open voting session")
	public void openSession() {
		Schedule schedule                   = getSchedule();
		VotingSessionRequest sessionRequest = new VotingSessionRequest(schedule.getId(), null);
		VotingSession session               = new VotingSession(sessionRequest, schedule);
		session.setId(new ObjectId("65c6d3327466dd65a5cc5355"));
		
		Mockito.when( scheduleRepository.findById(schedule.getId()) ).thenReturn(Optional.of(schedule));
		Mockito.when( sessionRepository.insert( Mockito.any(VotingSession.class) ) ).thenReturn(session);
		
		VotingSessionResponse openSession = service.openSession(sessionRequest);
		
		Mockito.verify(sessionRepository).insert(Mockito.any(VotingSession.class));
		
		assertNotNull(openSession);
		assertEquals(session.getId().toHexString(), openSession.id());
	}
	
	@Test
	@DisplayName("Voting Result")
	public void votingResult() {
		Schedule schedule     = getSchedule();
		ObjectId idSession    = new ObjectId("65c6d3327466dd65a5cc5355");
		VotingSession session = new VotingSession(new VotingSessionRequest(schedule.getId(), null), schedule);
		session.setId(idSession);
		session.setVotes(getVotes());
		
		Mockito.when( sessionRepository.findById(idSession) ).thenReturn(Optional.of(session));
		
		VoteResultResponse response = service.resultVote(idSession);
		
		assertNotNull(response);
		assertEquals(response.total(), 3);
		assertEquals(response.quantityYes(), 1);
		assertEquals(response.quantityNo(), 2);
	}
	
	@Test
	@DisplayName("Add vote")
	public void voteSubmit() {
		Schedule schedule     = getSchedule();
		VotingSession session = new VotingSession(new VotingSessionRequest(schedule.getId(), null), schedule);
		ObjectId idSession    = new ObjectId("65c6d3327466dd65a5cc5355");
		session.setId(idSession);
		
		VoteRequest voteRecordRequest = new VoteRequest(idSession, "87708545137", true);
		
		Mockito.when( sessionRepository.findById(idSession) ).thenReturn(Optional.of(session));
		
		service.voteSubmit(voteRecordRequest);
		
		Mockito.verify(sessionRepository).save(Mockito.any(VotingSession.class));
	}
	
	@Test
	@DisplayName("Session is closed")
	public void voteClosedSession() {
		Schedule schedule     = getSchedule();
		VotingSession session = new VotingSession(new VotingSessionRequest(schedule.getId(), null), schedule);
		ObjectId idSession    = new ObjectId("65c6d3327466dd65a5cc5355");
		session.setId(idSession);
		session.setEndSession(Instant.now().minusSeconds(120));
		
		VoteRequest voteRecordRequest = new VoteRequest(idSession, "87708545137", true);
		
		Mockito.when( sessionRepository.findById(idSession) ).thenReturn(Optional.of(session));
		
		assertThrows(ValidationException.class, () -> service.voteSubmit(voteRecordRequest), "Session is closed!");
	}
	
	@Test
	@DisplayName("Duplicate vote!")
	public void voteDuplicated() {
		Schedule schedule     = getSchedule();
		VotingSession session = new VotingSession(new VotingSessionRequest(schedule.getId(), null), schedule);
		ObjectId idSession    = new ObjectId("65c6d3327466dd65a5cc5355");
		session.setId(idSession);
		session.setVotes(getVotes());
		
		VoteRequest voteRecordRequest = new VoteRequest(idSession, session.getVotes().get(0).getCpf(), true);
		
		Mockito.when( sessionRepository.findById(idSession) ).thenReturn(Optional.of(session));
		
		assertThrows(ValidationException.class, () -> service.voteSubmit(voteRecordRequest), "Duplicate vote!");
	}

	private Schedule getSchedule() {
		Schedule schedule = new Schedule();
		schedule.setId(new ObjectId("65c6cc805f61400459180785"));
		schedule.setDescription("Schedule 1");
		return schedule;
	}
	
	private List<AssociatedVotes> getVotes() {
		AssociatedVotes voteOne = new AssociatedVotes();
		voteOne.setCpf("87708545137");
		voteOne.setVote(true);
		
		AssociatedVotes voteTwo = new AssociatedVotes();
		voteTwo.setCpf("34499148200");
		voteTwo.setVote(false);
		
		AssociatedVotes voteThree = new AssociatedVotes();
		voteThree.setCpf("32523364131");
		voteThree.setVote(false);
		
		return List.of(voteOne, voteTwo, voteThree);
	}

}
