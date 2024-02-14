package com.br.assembly.validators.votingsession.dto;

import com.br.assembly.dto.votingsession.VoteRequest;
import com.br.assembly.model.votingsession.VotingSession;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoteValidatorDTO {
	
	private VotingSession session;
	private String        cpf;
	
	public VoteValidatorDTO(VotingSession session, VoteRequest voteRecord) {
		this.session = session;
		this.cpf     = voteRecord.cpf();
	}

}
