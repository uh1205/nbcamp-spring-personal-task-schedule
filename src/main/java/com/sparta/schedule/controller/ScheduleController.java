package com.sparta.schedule.controller;

import com.sparta.schedule.dto.CommonResponse;
import com.sparta.schedule.dto.schedule.ScheduleRequest;
import com.sparta.schedule.dto.schedule.ScheduleResponse;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.security.UserDetailsImpl;
import com.sparta.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sparta.schedule.controller.ControllerUtils.*;

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
    ) throws IllegalArgumentException {
        // 예외 처리
        if (bindingResult.hasErrors()) {
            return getFieldErrorResponseEntity(bindingResult, "Failed to create schedule");
        }
        try {
            Schedule schedule = scheduleService.createSchedule(request, userDetails.getUser());
            ScheduleResponse response = new ScheduleResponse(schedule);
            return getResponseEntity(response, "Schedule created successfully");

        } catch (Exception e) {
            return getBadRequestResponseEntity(e);
        }
    }

    /**
     * 모든 일정 조회
     */
    @GetMapping
    public ResponseEntity<CommonResponse<?>> getSchedules() {
        List<ScheduleResponse> response = scheduleService.getSchedules()
                .stream().map(ScheduleResponse::new).toList();

        return getResponseEntity(response, "Retrieved all schedules successfully");
    }

    /**
     * 일정 조회
     */
    @GetMapping("/{scheduleId}")
    public ResponseEntity<CommonResponse<?>> getSchedule(
            @PathVariable Long scheduleId
    ) throws IllegalArgumentException {
        try {
            Schedule schedule = scheduleService.getSchedule(scheduleId);
            ScheduleResponse response = new ScheduleResponse(schedule);

            return getResponseEntity(response, "Retrieved schedule successfully");

        } catch (Exception e) {
            return getBadRequestResponseEntity(e);
        }
    }

    /**
     * 일정 수정
     */
    @PutMapping("/{scheduleId}")
    public ResponseEntity<CommonResponse<?>> updateSchedule(
            @PathVariable Long scheduleId,
            @Valid @RequestBody ScheduleRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            BindingResult bindingResult
    ) throws IllegalArgumentException {
        // 예외 처리
        if (bindingResult.hasErrors()) {
            return getFieldErrorResponseEntity(bindingResult, "Failed to update schedule");
        }
        try {
            Schedule schedule = scheduleService.updateSchedule(scheduleId, request, userDetails.getUser());
            ScheduleResponse response = new ScheduleResponse(schedule);

            return getResponseEntity(response, "Schedule updated successfully");

        } catch (Exception e) {
            return getBadRequestResponseEntity(e);
        }

    }

    /**
     * 일정 삭제
     */
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<CommonResponse<?>> deleteSchedule(
            @PathVariable Long scheduleId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) throws IllegalArgumentException {
        try {
            Long response = scheduleService.deleteSchedule(scheduleId, userDetails.getUser());

            return getResponseEntity(response, "Schedule deleted successfully");

        } catch (Exception e) {
            return getBadRequestResponseEntity(e);
        }

    }

}