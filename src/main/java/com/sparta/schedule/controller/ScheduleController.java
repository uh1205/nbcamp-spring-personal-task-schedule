package com.sparta.schedule.controller;

import com.sparta.schedule.dto.CommonResponse;
import com.sparta.schedule.dto.schedule.ScheduleRequest;
import com.sparta.schedule.dto.schedule.ScheduleResponse;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.security.UserDetailsImpl;
import com.sparta.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j(topic = "ScheduleController")
@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    /**
     * 새로운 일정 생성
     */
    @PostMapping
    public ResponseEntity<CommonResponse<?>> createSchedule(
            @Valid @RequestBody ScheduleRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            BindingResult bindingResult
    ) {
        // 예외 처리
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (!fieldErrors.isEmpty()) {
            for (FieldError fieldError : fieldErrors) {
                log.error("{} 필드 : {}", fieldError.getField(), fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest()
                    .body(CommonResponse.<List<FieldError>>builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .msg("일정 생성 실패")
                            .data(fieldErrors)
                            .build());
        }

        Schedule schedule = scheduleService.createSchedule(request, userDetails.getUser());
        ScheduleResponse response = new ScheduleResponse(schedule);

        return ResponseEntity.ok()
                .body(CommonResponse.<ScheduleResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .msg("일정 생성 성공")
                        .data(response)
                        .build());
    }

    /**
     * 모든 일정 조회
     */
    @GetMapping
    public ResponseEntity<CommonResponse<List<ScheduleResponse>>> getSchedules() {
        List<ScheduleResponse> response = scheduleService.getSchedules()
                .stream().map(ScheduleResponse::new).toList();

        return ResponseEntity.ok()
                .body(CommonResponse.<List<ScheduleResponse>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .msg("모든 일정 불러오기 성공")
                        .data(response)
                        .build());
    }

    /**
     * 일정 조회
     */
    @GetMapping("/{scheduleId}")
    public ResponseEntity<CommonResponse<ScheduleResponse>> getSchedule(
            @PathVariable Long scheduleId
    ) {
        Schedule schedule = scheduleService.getSchedule(scheduleId);
        ScheduleResponse response = new ScheduleResponse(schedule);

        return ResponseEntity.ok()
                .body(CommonResponse.<ScheduleResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .msg("일정 불러오기 성공")
                        .data(response)
                        .build());
    }

    /**
     * 일정 수정
     */
    @PutMapping("/{scheduleId}")
    public ResponseEntity<CommonResponse<ScheduleResponse>> updateSchedule(
            @PathVariable Long scheduleId,
            @Valid @RequestBody ScheduleRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            BindingResult bindingResult
    ) {
        Schedule schedule = scheduleService.updateSchedule(scheduleId, request, userDetails.getUser());
        ScheduleResponse response = new ScheduleResponse(schedule);

        return ResponseEntity.ok()
                .body(CommonResponse.<ScheduleResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .msg("일정 수정 성공")
                        .data(response)
                        .build());
    }

    /**
     * 일정 삭제
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