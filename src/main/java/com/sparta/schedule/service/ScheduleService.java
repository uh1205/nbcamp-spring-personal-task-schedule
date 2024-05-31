package com.sparta.schedule.service;

import com.sparta.schedule.dto.schedule.ScheduleRequest;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.entity.User;
import com.sparta.schedule.reporitory.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    /**
     * 새로운 일정 생성
     */
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
                new IllegalArgumentException("Schedule with id " + scheduleId + " does not exist"));
    }
    
    /**
     * 해당 일정 수정
     */
    @Transactional
    public Schedule updateSchedule(Long scheduleId, ScheduleRequest request, User user) {
        Schedule schedule = findScheduleAndVerifyUser(scheduleId, user);
        schedule.update(request);

        return schedule;
    }

    /**
     * 해당 일정 삭제
     */
    public Long deleteSchedule(Long scheduleId, User user) {
        Schedule schedule = findScheduleAndVerifyUser(scheduleId, user);
        scheduleRepository.delete(schedule);

        return scheduleId;
    }

    private Schedule findScheduleAndVerifyUser(Long scheduleId, User user) {
        Schedule schedule = getSchedule(scheduleId);

        if (!Objects.equals(schedule.getUser().getId(), user.getId())) {
            throw new IllegalArgumentException("Schedule with id " + scheduleId +
                    " does not belong to user with id " + user.getId());
        }

        return schedule;
    }

}
