package com.calendar.api.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import com.calendar.api.dto.EventDto;
import com.calendar.api.entity.Event;
import com.calendar.api.repository.EventRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CalendarService implements IfCalendarService {
	
	private final EventRepository eventRepository;

	@Override
	public Long getId() {
		return eventRepository.getNextSequenceValue();
	}

	@Override
	public List<Event> getEvent(String firstDayOfMonth, String startOfNextMonth) {
		return eventRepository.getEvent(firstDayOfMonth, startOfNextMonth);
	}

	@Override
	public void save(EventDto eventDto) {

        // 변환할 문자열
        String start_ = eventDto.getStart();
        String end_ = eventDto.getEnd();
        
        LocalDateTime start = strToDate(start_);
        
        LocalDateTime end = null;
        if(end_ == null || end_.length() == 0) {
        	end = start.plusMinutes(59);
        }else {
        	end = strToDate(end_);
        }
        
		Event event = new Event();
		event.setId(eventDto.getId());
		event.setTitle(eventDto.getTitle());
		event.setStart(start);
		event.setEnd(end);
		event.setAllDay(eventDto.getAllDay());
				
		eventRepository.save(event);
	}

	@Override
	@Transactional
	public void put(EventDto eventDto) {

        // 변환할 문자열
        String start_ = eventDto.getStart();
        String end_ = eventDto.getEnd();
        
        LocalDateTime start = strToDate(start_);
        
        LocalDateTime end = null;
        if(end_ == null || end_.length() == 0) {
        	end = start.plusMinutes(59);
        }else {
        	end = strToDate(end_);
        }
/*        
        if(start_.length() > 10) {
        	OffsetDateTime start__ = OffsetDateTime.parse(start_);
        	OffsetDateTime end__ = OffsetDateTime.parse(end_);
            start = start__.toLocalDateTime();
            end = end__.toLocalDateTime();
        }else {
            // 문자열 형식에 맞는 DateTimeFormatter 생성
        	DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            
            // 문자열을 LocalDate로 변환
            LocalDate _start = LocalDate.parse(start_, dateFormatter);
            LocalDate _end = LocalDate.parse(end_, dateFormatter);

            start = _start.atStartOfDay();		
            end = _end.atStartOfDay();		
        }
*/        
		Event event_ = new Event();
		event_.setId(eventDto.getId());
		event_.setTitle(eventDto.getTitle());
		event_.setStart(start);
		event_.setEnd(end);
		event_.setAllDay(eventDto.getAllDay());
		
		// 엔티티 조회
		Event event = eventRepository.findById(event_.getId())
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        // 엔티티 값 변경
		event.setTitle(event_.getTitle());
		event.setStart(event_.getStart());
		event.setEnd(event_.getEnd());
		event.setAllDay(event_.getAllDay());

        // 변경 사항은 @Transactional에 의해 자동으로 감지되고 저장됨
	}

	@Override
	public void delete(Long id) {
		eventRepository.deleteById(id);
	}

	
	private LocalDateTime strToDate(String src) {
		LocalDateTime result = null;
		
		if(src == null || src.length() == 0) return result;
        
        if(src.length() > 10) {
        	OffsetDateTime src_ = OffsetDateTime.parse(src);
        	result = src_.toLocalDateTime();
        }else {
            // 문자열 형식에 맞는 DateTimeFormatter 생성
        	DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            
            // 문자열을 LocalDate로 변환
            LocalDate src_ = LocalDate.parse(src, dateFormatter);

            result = src_.atStartOfDay();		
        }
		
		
		return result;
	}
}
