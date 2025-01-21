package com.calendar.api.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "events")
public class Event {
	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)  //AUTO_INCREMENT에 매핑
    private Long id;
    private String title;
    private LocalDateTime start;
    private LocalDateTime end;
    
    @Column(name = "allDay")
    private Boolean allDay;
    private String description;
}
