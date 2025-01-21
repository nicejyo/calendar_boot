package com.calendar.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventDto {
    private Long id;
    private String title;
    private String start;
    private String end;
    private Boolean allDay;
}
