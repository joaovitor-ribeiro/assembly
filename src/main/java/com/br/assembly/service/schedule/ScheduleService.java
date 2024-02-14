package com.br.assembly.service.schedule;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.assembly.dto.schedule.ScheduleRequest;
import com.br.assembly.dto.schedule.ScheduleResponse;
import com.br.assembly.exception.NotObjectFoundException;
import com.br.assembly.model.schedule.Schedule;
import com.br.assembly.repository.schedule.ScheduleRepository;

@Service
public class ScheduleService {
	
	private final ScheduleRepository repository;

	@Autowired
	public ScheduleService(ScheduleRepository repository) {
		super();
		this.repository = repository;
	}
	
	public ScheduleResponse listOne(ObjectId id) {
		Schedule schedule = repository.findById(id).orElseThrow( () -> new NotObjectFoundException("Schedule not found!") );
		return new ScheduleResponse(schedule);
	}
	
	public List<ScheduleResponse> listAll() {
		List<Schedule> schedules = repository.findAll();
		return schedules.stream().map(schedule -> new ScheduleResponse(schedule)).toList();
	}
	
	public ScheduleResponse create(ScheduleRequest scheduleRecord) {
		Schedule schedule = new Schedule(scheduleRecord);
		schedule = repository.insert(schedule);
		return new ScheduleResponse(schedule);
	}

}
