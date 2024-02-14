package com.br.assembly.dto.votingsession;

import org.bson.types.ObjectId;

import jakarta.validation.constraints.NotNull;

public record VotingSessionRequest(@NotNull ObjectId idSchedule, Integer duration) {
	
}
