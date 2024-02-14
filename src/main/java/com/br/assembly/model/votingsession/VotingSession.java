package com.br.assembly.model.votingsession;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.br.assembly.dto.votingsession.VotingSessionRequest;
import com.br.assembly.model.schedule.Schedule;
import com.br.assembly.utils.EmptyUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document
@Getter
@Setter
@NoArgsConstructor
public class VotingSession {
	
	@Id
	private ObjectId id;
	
	private Schedule  schedule;
	
	private Instant   startTime;
	
	private Instant   endSession;
	
	private List<AssociatedVotes> votes;
	
	private Boolean processed;
	
	public VotingSession(VotingSessionRequest session, Schedule schedule) {
		this.schedule   = schedule;
		this.startTime  = Instant.now();
		this.endSession = EmptyUtils.isEmpty(session.duration()) ? 
								Instant.now().plusSeconds(60) : 
								Instant.now().plusSeconds(session.duration()); 
		this.votes      = new ArrayList<AssociatedVotes>();
		this.processed  = false;
	}
	
	public boolean isExpired() {
		return Instant.now().isAfter(this.endSession);
	}

}
