package com.sparta.schedule.entity;

import com.sparta.schedule.dto.create.CreateScheduleReq;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Schedule extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String manager;

    @Column(nullable = false)
    private String password;

    public Schedule(CreateScheduleReq reqDto) {
        this.title = reqDto.getTitle();
        this.content = reqDto.getContent();
        this.manager = reqDto.getManager();
        this.password = reqDto.getPassword();
    }

    public void update(CreateScheduleReq reqDto) {
        this.title = reqDto.getTitle();
        this.content = reqDto.getContent();
        this.manager = reqDto.getManager();
        this.password = reqDto.getPassword();
    }
}
