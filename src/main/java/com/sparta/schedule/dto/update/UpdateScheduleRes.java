package com.sparta.schedule.dto.update;

import com.sparta.schedule.entity.Schedule;
import lombok.Getter;

@Getter
public class UpdateScheduleRes {

    private Long id;
    private String title;
    private String content;
    private String manager;

    public UpdateScheduleRes(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.manager = schedule.getManager();
    }
}
