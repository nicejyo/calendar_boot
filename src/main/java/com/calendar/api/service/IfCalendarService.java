package com.calendar.api.service;

import java.util.List;

import com.calendar.api.dto.EventDto;
import com.calendar.api.entity.Event;

public interface IfCalendarService {
	Long getId();

	List<Event> getEvent(String day);

	void save(EventDto eventDto);

	void put(EventDto eventDto);

	void delete(Long id); 
	
	
}
