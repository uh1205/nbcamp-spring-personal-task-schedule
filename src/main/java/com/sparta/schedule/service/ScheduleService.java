package com.sparta.schedule.service;

import com.sparta.schedule.dto.schedule.CreateScheduleRequest;
import com.sparta.schedule.dto.schedule.UpdateScheduleRequest;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.entity.User;
import com.sparta.schedule.reporitory.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    /**
     * 새로운 일정 생성
     */
    public Schedule createSchedule(CreateScheduleRequest request, User user) {
        return scheduleRepository.save(new Schedule(request, user));
    }

    /**
     * 모든 일정 불러오기
     */
    public List<Schedule> getSchedules() {
        return scheduleRepository.findAllByOrderByCreatedDateDesc();
    }

    /**
     * 해당 일정 불러오기
     */
    public Schedule getSchedule(Long id) {
        return scheduleRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Schedule not found")
        );
    }

    /**
     * 해당 일정 수정
     */
    @Transactional
    public Schedule updateSchedule(Long id, UpdateScheduleRequest request, User user) {
        Schedule schedule = findScheduleAndVerifyUser(id, user);
        schedule.update(request);

        return schedule;
    }

    /**
     * 해당 일정 삭제
     */
    public Long deleteSchedule(Long id, User user) {
        Schedule schedule = findScheduleAndVerifyUser(id, user);
        scheduleRepository.delete(schedule);

        return id;
    }

    private Schedule findScheduleAndVerifyUser(Long id, User user) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Schedule not found"));
        schedule.checkUser(user);

        return schedule;
    }

}
