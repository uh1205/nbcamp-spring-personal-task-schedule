package com.sparta.schedule.dto.read;

import com.sparta.schedule.entity.Schedule;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ReadScheduleRes {

    private Long id;
    private String title;
    private String content;
    private String manager;
    private LocalDate createDate;

    public ReadScheduleRes(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.manager = schedule.getManager();
        this.createDate = schedule.getCreatedDate();
    }
}
