package com.calendar.api.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.calendar.api.entity.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

	@Query(value="SELECT NEXTVAL(my_sequence)", nativeQuery = true)
	Long getNextSequenceValue();
	
	
  @Query(value="SELECT id,title,start,end,allDay, description FROM events WHERE start >= STR_TO_DATE(:start, '%Y-%m-%dT%H:%i') AND start < STR_TO_DATE(:last, '%Y-%m-%dT%H:%i')", nativeQuery = true)
  List<Event> getEvent(@Param("start") String firstDayOfMonth, @Param("last") String startOfNextMonth);
	 
}
