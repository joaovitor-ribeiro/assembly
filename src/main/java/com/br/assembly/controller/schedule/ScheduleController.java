package com.br.assembly.controller.schedule;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.br.assembly.dto.schedule.ScheduleRequest;
import com.br.assembly.dto.schedule.ScheduleResponse;
import com.br.assembly.service.schedule.ScheduleService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@RestController
@RequestMapping ("assembly/v1/schedule")
@CrossOrigin
@Tag(name = "Schedule", description = "Schedule control")
@ApiResponse(responseCode = "500", description = "Internal Error")
public class ScheduleController {
	
	private final ScheduleService scheduleService;
	
	@Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }
	
	@ResponseStatus(code = HttpStatus.OK)
	@RequestMapping(method = RequestMethod.GET, path = "/list/{id}")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful operation") })
	public ResponseEntity<ScheduleResponse> listOne(@PathVariable ObjectId id) throws URISyntaxException {
		
		ScheduleResponse response = scheduleService.listOne(id);
		
		return ResponseEntity.ok(response);
	}
	
	@ResponseStatus(code = HttpStatus.OK)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful operation") })
	@RequestMapping(method = RequestMethod.GET, path = "/list/all")
	public ResponseEntity<List<ScheduleResponse>> listAll() throws URISyntaxException {
		
		List<ScheduleResponse> response = scheduleService.listAll();
		
		return ResponseEntity.ok(response);
	}
	
	@ResponseStatus(code = HttpStatus.CREATED)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Schedule successfully created") })
	@RequestMapping(method = RequestMethod.POST, path = "/create")
	public ResponseEntity<ScheduleResponse> create(@RequestBody @Valid ScheduleRequest scheduleRecord) throws URISyntaxException {
		
		ScheduleResponse response = scheduleService.create(scheduleRecord);
		
		return ResponseEntity.created(new URI(response.id())).body(response);
	}

}
