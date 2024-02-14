package com.br.assembly.dto.votingsession;

public record VoteResultResponse(long total, long quantityYes, long quantityNo) {
	
	@Override
	public String toString() {
		return "Total votes: "+ this.total + " Yes: " + this.quantityYes  + " No: " + this.quantityNo;
	}

}
