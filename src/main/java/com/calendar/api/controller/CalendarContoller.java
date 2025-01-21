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
        // 문자열 형식에 맞는 DateTimeFormatter 생성
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // 문자열을 LocalDate로 변환
        LocalDate day_ = LocalDate.parse(day, dateFormatter);
        

        // LocalDate를 LocalDateTime으로 변환 (시간은 기본값인 00:00:00으로 설정)
        LocalDateTime _day = day_.atStartOfDay();
        LocalDateTime firstDayOfMonth = _day.withDayOfMonth(1);
        LocalDateTime startOfNextMonth = firstDayOfMonth.plusMonths(1);
        
        List<Event> event = calendarService.getEvent(firstDayOfMonth.toString(), startOfNextMonth.toString());
		
		if(event == null)
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND)  ;
		
		return new ResponseEntity<>(event, HttpStatus.OK)  ;
	}

	@PostMapping("/save")
	public ResponseEntity<String> save(@RequestBody EventDto eventDto){
        // 문자열 형식에 맞는 DateTimeFormatter 생성
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // 변환할 문자열
        String start_ = eventDto.getStart();
        String end_ = eventDto.getEnd();

    	System.out.printf("start_save == %s\n", start_);

        // 문자열을 LocalDate로 변환
        LocalDate _start = LocalDate.parse(start_, dateFormatter);
        LocalDate _end = LocalDate.parse(end_, dateFormatter);

        // LocalDate를 LocalDateTime으로 변환 (시간은 기본값인 00:00:00으로 설정)
        LocalDateTime start = _start.atStartOfDay();		
        LocalDateTime end = _end.atStartOfDay();		
        
		Event event = new Event();
		
		event.setId(eventDto.getId());
		event.setTitle(eventDto.getTitle());
		event.setStart(start);
		event.setEnd(end);
		event.setAllDay(eventDto.getAllDay());
		
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
