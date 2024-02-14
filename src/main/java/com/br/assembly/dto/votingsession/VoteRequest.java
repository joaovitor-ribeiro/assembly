package com.br.assembly.dto.votingsession;

import org.bson.types.ObjectId;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VoteRequest(@NotNull ObjectId idSession, @NotBlank String cpf, @NotNull Boolean vote) {

}
