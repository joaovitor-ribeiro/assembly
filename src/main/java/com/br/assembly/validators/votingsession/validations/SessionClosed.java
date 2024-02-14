package com.br.assembly.validators.votingsession.validations;

import org.springframework.stereotype.Component;

import com.br.assembly.exception.ValidationException;
import com.br.assembly.validators.votingsession.VoteValidator;
import com.br.assembly.validators.votingsession.dto.VoteValidatorDTO;

@Component
public class SessionClosed implements VoteValidator {

	@Override
	public void validation(VoteValidatorDTO dto) {
		if ( dto.getSession().isExpired() ) {
			throw new ValidationException("Session is closed!");
		}
	}

}
