package com.br.assembly.kafka.votingsession;

import java.util.HashMap;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import com.br.assembly.dto.votingsession.VoteResultResponse;
import com.br.assembly.kafka.KafkaService;

public class ConsumerVotingSession {
	
	private static final String KAFKATOPIC = "VOTE_RESULT_SESSION";
	
	private final KafkaService<VoteResultResponse> service;
	
	public ConsumerVotingSession() {
		this.service = new KafkaService<VoteResultResponse>(ConsumerVotingSession.class.getSimpleName(), 
															KAFKATOPIC, 
															this::parse,
															VoteResultResponse.class,
															new HashMap<String, String>());
			
	}
	
    public void verify() {
    	this.service.run();
    }
    
    private void parse(ConsumerRecord<String, VoteResultResponse> record) {
    	VoteResultResponse value = record.value();
    	if (value != null) {
    		System.out.println(value.toString());
    	}
	}

}
