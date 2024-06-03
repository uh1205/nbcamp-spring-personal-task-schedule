package com.sparta.schedule.service;

import com.sparta.schedule.domain.entity.Schedule;
import com.sparta.schedule.domain.entity.User;
import com.sparta.schedule.dto.schedule.ScheduleRequest;
import com.sparta.schedule.reporitory.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    /**
     * 새로운 일정 생성
     */
    @Transactional
    public Schedule createSchedule(ScheduleRequest request, User user) {
        return scheduleRepository.save(new Schedule(request, user));
    }

    /**
     * 모든 일정 조회
     */
    public List<Schedule> getSchedules() {
        return scheduleRepository.findAllByOrderByCreatedDateDesc();
    }

    /**
     * 해당 일정 조회
     */
    public Schedule getSchedule(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).orElseThrow(() ->
                new IllegalArgumentException("Schedule with id " + scheduleId + " does not exist")
        );
    }
    
    /**
     * 해당 일정 수정
     */
    @Transactional
    public Schedule updateSchedule(Long scheduleId, ScheduleRequest request, User user) {
        Schedule schedule = getSchedule(scheduleId);
        schedule.verify(user);
        schedule.update(request);

        return schedule;
    }

    /**
     * 해당 일정 삭제
     */
    @Transactional
    public Long deleteSchedule(Long scheduleId, User user) {
        Schedule schedule = getSchedule(scheduleId);
        schedule.verify(user);
        scheduleRepository.delete(schedule);

        return scheduleId;
    }

}
