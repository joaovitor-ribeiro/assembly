package com.br.assembly.dto.schedule;

import com.br.assembly.model.schedule.Schedule;

public record ScheduleResponse(String id, String description) {
	
	public ScheduleResponse(Schedule schedule) {
		this(String.valueOf(schedule.getId()), schedule.getDescription());
	}
	
}