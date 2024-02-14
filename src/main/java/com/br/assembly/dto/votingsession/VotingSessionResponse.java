package com.br.assembly.dto.votingsession;

import com.br.assembly.model.votingsession.VotingSession;

public record VotingSessionResponse(String id) {

	public VotingSessionResponse(VotingSession sessionCreated) {
		this( String.valueOf(sessionCreated.getId()) );
	}

}
