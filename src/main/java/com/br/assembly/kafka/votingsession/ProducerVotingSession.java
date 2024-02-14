package com.br.assembly.kafka.votingsession;

import java.util.UUID;

import com.br.assembly.dto.votingsession.VoteResultResponse;
import com.br.assembly.kafka.KafkaDispatcher;

public class ProducerVotingSession {
	
	private static final String KAFKATOPIC = "VOTE_RESULT_SESSION";
	
	private final KafkaDispatcher<VoteResultResponse> votingSession;
	
	public ProducerVotingSession() {
		 this.votingSession = new KafkaDispatcher<VoteResultResponse>();
	}
	
	public <T> void sendProducerVotingSession(VoteResultResponse value) {
		this.votingSession.send(KAFKATOPIC, UUID.randomUUID().toString(), value);
	}

}
