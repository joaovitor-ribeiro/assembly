package com.br.assembly.model.votingsession;

import com.br.assembly.dto.votingsession.VoteRequest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AssociatedVotes {
	
	private String   cpf;
	
	private boolean  vote;
	
	public AssociatedVotes(VoteRequest voteRecord) {
		this.cpf  = voteRecord.cpf();
		this.vote = voteRecord.vote();
	}
	
}
