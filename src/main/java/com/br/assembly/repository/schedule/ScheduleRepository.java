package com.br.assembly.repository.schedule;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.br.assembly.model.schedule.Schedule;

@Repository
public interface ScheduleRepository extends MongoRepository<Schedule, ObjectId> {

}
