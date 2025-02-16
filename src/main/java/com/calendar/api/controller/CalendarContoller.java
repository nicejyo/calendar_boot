package com.calendar.api.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.calendar.api.dto.EventDto;
import com.calendar.api.entity.Event;
import com.calendar.api.service.IfCalendarService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CalendarContoller {
	
	private final IfCalendarService calendarService;
	
	
	//입력할 ID 값을 가져온다.
	@GetMapping("/id")
	public Long getId() {
		return calendarService.getId();
	}

	@GetMapping("/event/{day}")
	public ResponseEntity<List<Event>> getEvents(@PathVariable("day") String day) {
        
        List<Event> event = calendarService.getEvent(day);
		
		if(event == null)
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND)  ;
		
		return new ResponseEntity<>(event, HttpStatus.OK)  ;
	}

	@PostMapping("/save")
	public ResponseEntity<String> save(@RequestBody EventDto eventDto){
		
		calendarService.save(eventDto);
		
		return new ResponseEntity<>("CREATED", HttpStatus.CREATED)  ;
	}

	@PutMapping("/put")
	public ResponseEntity<String> putStr(@RequestBody EventDto eventDto){

		calendarService.put(eventDto);
		
		return new ResponseEntity<>("MODIFYED", HttpStatus.OK)  ;
	}

	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") Long id){
		
		calendarService.delete(id);
		
		return new ResponseEntity<>("DELETED", HttpStatus.OK)  ;
	}
}
