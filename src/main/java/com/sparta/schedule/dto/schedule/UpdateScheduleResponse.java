package com.sparta.schedule.dto.schedule;

import com.sparta.schedule.entity.Schedule;
import lombok.Getter;

@Getter
public class UpdateScheduleResponse {

    private final Long scheduleId;
    private final String title;
    private final String content;

    public UpdateScheduleResponse(Schedule schedule) {
        this.scheduleId = schedule.getId();
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
    }

}
