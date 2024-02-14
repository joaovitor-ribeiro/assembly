package com.br.assembly.validators.votingsession.validations;

import org.springframework.stereotype.Component;

import com.br.assembly.exception.ValidationException;
import com.br.assembly.validators.votingsession.VoteValidator;
import com.br.assembly.validators.votingsession.dto.VoteValidatorDTO;

@Component
public class DuplicateVote implements VoteValidator {

	@Override
	public void validation(VoteValidatorDTO dto) {
		boolean present = dto.getSession().getVotes().stream()
													 .filter(vote -> vote.getCpf().equals(dto.getCpf()))
													 .findFirst()
													 .isPresent();
		if ( present ) {
			throw new ValidationException("Duplicate vote!");
		}
	}

}
