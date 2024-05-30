package com.sparta.schedule.dto.schedule;

import com.sparta.schedule.entity.Schedule;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CreateScheduleResponse {

    private final Long scheduleId;
    private final String title;
    private final String content;
    private final LocalDate createdAt;

    public CreateScheduleResponse(Schedule schedule) {
        this.scheduleId = schedule.getId();
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.createdAt = schedule.getCreatedDate();
    }

}
