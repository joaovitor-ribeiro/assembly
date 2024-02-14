package com.br.assembly.service.votingsession;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import com.br.assembly.dto.votingsession.VoteRequest;
import com.br.assembly.dto.votingsession.VoteResultResponse;
import com.br.assembly.dto.votingsession.VotingSessionRequest;
import com.br.assembly.dto.votingsession.VotingSessionResponse;
import com.br.assembly.kafka.votingsession.ConsumerVotingSession;
import com.br.assembly.kafka.votingsession.ProducerVotingSession;
import com.br.assembly.model.schedule.Schedule;
import com.br.assembly.model.votingsession.AssociatedVotes;
import com.br.assembly.model.votingsession.VotingSession;
import com.br.assembly.repository.schedule.ScheduleRepository;
import com.br.assembly.repository.votingsession.VotingSessionRepository;
import com.br.assembly.validators.votingsession.VoteValidation;
import com.br.assembly.validators.votingsession.dto.VoteValidatorDTO;

@Service
public class VotingSessionService {
	
	private final VotingSessionRepository sessionRepository;
	private final ScheduleRepository      scheduleRepository;
	private final VoteValidation          voteValidation;
	private final ProducerVotingSession   producerVotingSession;
	private final ConsumerVotingSession   consumerVotingSession;
	
	@Autowired
	public VotingSessionService(VotingSessionRepository sessionRepository, 
								ScheduleRepository scheduleRepository, 
								VoteValidation voteValidation ) {
		super();
		this.sessionRepository     = sessionRepository;
		this.scheduleRepository    = scheduleRepository;
		this.voteValidation        = voteValidation;
		this.producerVotingSession = new ProducerVotingSession();
		this.consumerVotingSession = new ConsumerVotingSession();
	}

	public VotingSessionResponse openSession(VotingSessionRequest sessionRecord) {
		Schedule schedule = getSchedule(sessionRecord);
		
		VotingSession session        = new VotingSession(sessionRecord, schedule);
		VotingSession sessionCreated = sessionRepository.insert(session);
		
		return new VotingSessionResponse(sessionCreated);
	}

	public void voteSubmit(VoteRequest voteRecord) {
		VotingSession session = getSession(voteRecord.idSession());
		
		voteValidation.validation(new VoteValidatorDTO(session, voteRecord));
		
		AssociatedVotes vote = new AssociatedVotes(voteRecord);
		
		session.getVotes().add(vote);
		
		sessionRepository.save(session);
	}
	
	public VoteResultResponse resultVote(ObjectId idSession) {
		List<AssociatedVotes> votes = getSession(idSession).getVotes();
		
		long total    = votes.size();
		long countYes = votes.stream().filter(vote -> vote.isVote()).count();
		long countNo  = total - countYes;
		
		return new VoteResultResponse(total, countYes, countNo);
	}

	private VotingSession getSession(ObjectId id) {
		return sessionRepository.findById(id)
								.orElseThrow(() -> new NotFoundException("Session not found!"));
	}
	
	private Schedule getSchedule(VotingSessionRequest sessionRecord) {
		return scheduleRepository.findById(sessionRecord.idSchedule())
							     .orElseThrow(() -> new NotFoundException("Schedule not found!"));
	}
	
	
	@Scheduled(fixedDelay = 1000)
	private void sessionMonitoring() {
		List<VotingSession> list = sessionRepository.findAll();
		list.stream().filter(session -> session.isExpired() && !session.getProcessed()).forEach(session -> {
			
			VoteResultResponse response = resultVote(session.getId());
			producerVotingSession.sendProducerVotingSession(response);
			
			session.setProcessed(true);
			sessionRepository.save(session);
		});
	}
	
	@Scheduled(fixedDelay = 1000)
	private void sessionMonitoringConsumer() {
		consumerVotingSession.verify();
	}

}
