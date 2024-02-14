//package com.br.assembly.validators.votingsession.validations;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.HttpStatusCodeException;
//import org.springframework.web.client.RestTemplate;
//
//import com.br.assembly.exception.ValidationException;
//import com.br.assembly.validators.votingsession.VoteValidator;
//import com.br.assembly.validators.votingsession.dto.VoteValidatorDTO;
//
//@Component
//public class CpfValidations implements VoteValidator {
//
//	private final String ABLE_TO_VOTE = "ABLE_TO_VOTE";
//	
//	private final RestTemplate restTemplate;
//	
//	@Value("${validation.cpf.url}")
//    private String url;
//	
//	@Autowired
//	public CpfValidations(RestTemplate restTemplate) {
//		this.restTemplate = restTemplate;
//	}
//	
//	@Override
//	public void validation(VoteValidatorDTO dto) {
//		try {
//			String url = this.url + dto.getCpf();
//			
//			ResponseEntity<?> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
//
//			if (response != null && response.getBody() != null && response.getBody().equals(ABLE_TO_VOTE)) {
//				return;
//			}
//			
//			throw new ValidationException("Unauthorized CPF!");
//			
//		} catch (HttpStatusCodeException ex) {
//			if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
//				throw new ValidationException("Invalid CPF!");
//			}
//		}
//		
//		throw new ValidationException("CPF validator integration error!");
//	}
//	
//}
