package com.sparta.schedule.dto.create;

import lombok.Getter;

@Getter
public class CreateScheduleReq {

    private String title;
    private String content;
    private String manager;
    private String password;
}
