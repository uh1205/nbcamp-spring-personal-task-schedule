package com.sparta.schedule.controller;

import com.sparta.schedule.dto.create.CreateScheduleReq;
import com.sparta.schedule.dto.create.CreateScheduleRes;
import com.sparta.schedule.dto.update.UpdateScheduleRes;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    // C
    @PostMapping("/new")
    public CreateScheduleRes createSchedule(@RequestBody CreateScheduleReq req) {
        return scheduleService.createSchedule(req);
    }

    // R
    @GetMapping("/{id}")
    public CreateScheduleRes getScheduleById(@PathVariable Long id) {
        return scheduleService.getScheduleById(id);
    }

    @GetMapping("/schedules")
    public List<CreateScheduleRes> getScheduleList() {
        return scheduleService.getScheduleList();
    }

    // U
    @PutMapping("/{id}")
    public UpdateScheduleRes updateSchedule(@PathVariable Long id, @RequestBody CreateScheduleReq reqDto) {
        return new UpdateScheduleRes(scheduleService.updateSchedule(id, reqDto));
    }

    // D
    @DeleteMapping("/{id}")
    public Long deleteSchedule(@PathVariable Long id, @RequestBody CreateScheduleReq reqDto) {
        return scheduleService.deleteSchedule(id, reqDto);
    }
}

