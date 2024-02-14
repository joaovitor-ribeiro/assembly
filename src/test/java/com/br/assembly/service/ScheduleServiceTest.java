package com.br.assembly.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.br.assembly.dto.schedule.ScheduleRequest;
import com.br.assembly.dto.schedule.ScheduleResponse;
import com.br.assembly.exception.NotObjectFoundException;
import com.br.assembly.model.schedule.Schedule;
import com.br.assembly.repository.schedule.ScheduleRepository;
import com.br.assembly.service.schedule.ScheduleService;

public class ScheduleServiceTest {
	
	@Mock
	private ScheduleRepository repository;
	
	private ScheduleService service;
	
	@BeforeEach
	public void beforeEach() {
		MockitoAnnotations.openMocks(this);
		this.service = new ScheduleService(repository);
	}
	
	@Test
	@DisplayName("Create schedule success")
	public void createSchedule() {
		Schedule schedule = getSchedules().get(0);
		ObjectId id       = schedule.getId();
		
		Mockito.when(repository.insert(Mockito.any(Schedule.class))).thenReturn(schedule);
		
		ScheduleRequest request   = new ScheduleRequest(schedule.getDescription());
		ScheduleResponse response = this.service.create(request);
		
		assertNotNull(response);
		assertEquals(response.id(), id.toHexString());
		Mockito.verify(repository).insert(Mockito.any(Schedule.class));
	}
	
	@Test
	@DisplayName("List one schedule")
	public void schedule() {
		Schedule schedule = getSchedules().get(0);
		ObjectId id       = schedule.getId();
		
		Mockito.when(repository.findById(id)).thenReturn(Optional.of(schedule));
		
		ScheduleResponse response = service.listOne(id);
		
		assertNotNull(response);
		assertEquals(response.id(), id.toHexString());
	}
	
	@Test
	@DisplayName("Schedule not found")
	public void listOneSchedule() {
		Schedule schedule = getSchedules().get(0);
		ObjectId id       = schedule.getId();
		
		Mockito.when(repository.findById(id)).thenThrow(new NotObjectFoundException("Schedule not found!") );
		
		assertThrows(NotObjectFoundException.class, () -> service.listOne(id), "Schedule not found!");
	}
	
	@Test
	@DisplayName("List all schedule")
	public void listAllSchedules() {
		List<Schedule> schedules = getSchedules();
		
		Mockito.when(repository.findAll()).thenReturn(schedules);
		
		service.listAll();
		
		assertNotNull(schedules);
		assertEquals(2, schedules.size());
	}
	
	
	private static List<Schedule> getSchedules() {
		Schedule scheduleOne = new Schedule();
		scheduleOne.setId(new ObjectId("65c6cc805f61400459180785"));
		scheduleOne.setDescription("Schedule 1");
		
		Schedule scheduleTwo = new Schedule();
		scheduleTwo.setId(new ObjectId("65c6cc805f61400459180788"));
		scheduleTwo.setDescription("Schedule 2");
		
		return List.of( scheduleOne, scheduleTwo);
	}
	
}
