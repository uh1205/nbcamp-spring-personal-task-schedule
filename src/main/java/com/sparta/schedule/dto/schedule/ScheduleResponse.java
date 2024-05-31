package com.sparta.schedule.dto.schedule;

import com.sparta.schedule.entity.Schedule;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ScheduleResponse {

    private final Long scheduleId;
    private final String title;
    private final String content;
    private final LocalDate createdAt;

    public ScheduleResponse(Schedule schedule) {
        this.scheduleId = schedule.getId();
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.createdAt = schedule.getCreatedDate();
    }
}
