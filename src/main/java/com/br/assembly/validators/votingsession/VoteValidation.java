package com.br.assembly.validators.votingsession;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.br.assembly.validators.votingsession.dto.VoteValidatorDTO;

@Component
public class VoteValidation {
	
	private List<VoteValidator> validators;
	
	@Autowired
	public VoteValidation(List<VoteValidator> validators) {
		this.validators = validators;
	}
	
	public void validation(VoteValidatorDTO dto) {
		validators.forEach(v -> v.validation(dto));
	}

}
