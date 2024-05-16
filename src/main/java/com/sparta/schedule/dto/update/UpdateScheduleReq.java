package com.sparta.schedule.dto.update;

import lombok.Getter;

@Getter
public class UpdateScheduleReq {

    private String title;
    private String content;
    private String manager;
    private String password;
}
