package com.sparta.schedule.service;

import com.sparta.schedule.dto.create.CreateScheduleReq;
import com.sparta.schedule.dto.create.CreateScheduleRes;
import com.sparta.schedule.entity.Schedule;
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
     * Create
     */
    public CreateScheduleRes createSchedule(CreateScheduleReq req) {
        Schedule schedule = scheduleRepository.save(new Schedule(req));
        return new CreateScheduleRes(schedule);
    }


    /**
     * Read
     */
    public CreateScheduleRes getScheduleById(Long id) {
        return new CreateScheduleRes(findSchedule(id));
    }

    public List<CreateScheduleRes> getScheduleList() {
        return scheduleRepository.findAllByOrderByCreatedDateDesc().stream()
                .map(CreateScheduleRes::new).toList();
    }


    /**
     * Update
     */
    @Transactional
    public Schedule updateSchedule(Long id, CreateScheduleReq req) {
        Schedule schedule = findSchedule(id);
        if (!schedule.getPassword().equals(req.getPassword())) {
            throw new IllegalArgumentException("Wrong password");
        }
        schedule.update(req);
        return schedule;
    }


    /**
     * Delete
     */
    public Long deleteSchedule(Long id, CreateScheduleReq req) {
        Schedule schedule = findSchedule(id);
        if (!schedule.getPassword().equals(req.getPassword())) {
            throw new IllegalArgumentException("Wrong password");
        }
        scheduleRepository.delete(schedule);
        return id;
    }

    private Schedule findSchedule(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Schedule not found"));
    }

}
