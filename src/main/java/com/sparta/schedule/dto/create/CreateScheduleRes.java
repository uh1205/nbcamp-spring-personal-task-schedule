package com.sparta.schedule.dto.create;

import com.sparta.schedule.entity.Schedule;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CreateScheduleRes {

    private Long id;
    private String title;
    private String content;
    private String manager;
    private LocalDate createDate;

    public CreateScheduleRes(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.manager = schedule.getManager();
        this.createDate = schedule.getCreatedDate();
    }
}
