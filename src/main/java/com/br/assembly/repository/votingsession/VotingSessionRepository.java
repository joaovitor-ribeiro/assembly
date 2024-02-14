package com.br.assembly.repository.votingsession;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.br.assembly.model.votingsession.VotingSession;

@Repository
public interface VotingSessionRepository extends MongoRepository<VotingSession, ObjectId> {

}
