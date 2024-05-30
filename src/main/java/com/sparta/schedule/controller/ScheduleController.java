package com.sparta.schedule.controller;

import com.sparta.schedule.dto.CommonResponse;
import com.sparta.schedule.dto.schedule.*;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.security.UserDetailsImpl;
import com.sparta.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    /**
     * 새로운 일정 생성
     */
    @PostMapping
    public ResponseEntity<CommonResponse<CreateScheduleResponse>> createSchedule(
            @Valid @RequestBody CreateScheduleRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Schedule schedule = scheduleService.createSchedule(request, userDetails.getUser());
        CreateScheduleResponse response = new CreateScheduleResponse(schedule);

        return ResponseEntity.ok()
                .body(CommonResponse.<CreateScheduleResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .msg("일정 생성 성공")
                        .data(response)
                        .build());
    }

    /**
     * 모든 일정 불러오기
     */
    @GetMapping
    public ResponseEntity<CommonResponse<List<ReadScheduleResponse>>> getSchedules() {
        List<ReadScheduleResponse> response = scheduleService.getSchedules()
                .stream().map(ReadScheduleResponse::new).toList();

        return ResponseEntity.ok()
                .body(CommonResponse.<List<ReadScheduleResponse>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .msg("모든 일정 불러오기 성공")
                        .data(response)
                        .build());
    }

    /**
     * 해당 일정 불러오기
     */
    @GetMapping("/{scheduleId}")
    public ResponseEntity<CommonResponse<ReadScheduleResponse>> getSchedule(@PathVariable Long scheduleId) {
        Schedule schedule = scheduleService.getSchedule(scheduleId);
        ReadScheduleResponse response = new ReadScheduleResponse(schedule);

        return ResponseEntity.ok()
                .body(CommonResponse.<ReadScheduleResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .msg("일정 불러오기 성공")
                        .data(response)
                        .build());
    }

    /**
     * 해당 일정 수정
     */
    @PutMapping("/{scheduleId}")
    public ResponseEntity<CommonResponse<UpdateScheduleResponse>> updateSchedule(
            @PathVariable Long scheduleId,
            @RequestBody UpdateScheduleRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Schedule schedule = scheduleService.updateSchedule(scheduleId, request, userDetails.getUser());
        UpdateScheduleResponse response = new UpdateScheduleResponse(schedule);

        return ResponseEntity.ok()
                .body(CommonResponse.<UpdateScheduleResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .msg("일정 수정 성공")
                        .data(response)
                        .build());
    }

    /**
     * 해당 일정 삭제
     */
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<CommonResponse<Long>> deleteSchedule(
            @PathVariable Long scheduleId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Long response = scheduleService.deleteSchedule(scheduleId, userDetails.getUser());

        return ResponseEntity.ok()
                .body(CommonResponse.<Long>builder()
                        .statusCode(HttpStatus.OK.value())
                        .msg("일정 삭제 성공")
                        .data(response)
                        .build());
    }

}