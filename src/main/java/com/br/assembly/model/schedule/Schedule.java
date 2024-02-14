package com.br.assembly.model.schedule;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.br.assembly.dto.schedule.ScheduleRequest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document( collection = "schedule" )
@Getter
@Setter
@NoArgsConstructor
public class Schedule {
	
	@Id
	private ObjectId id;
	
	private String  description;
	
	public Schedule(ScheduleRequest scheduleResquest) {
		super();
		this.description = scheduleResquest.description();
	}

}
