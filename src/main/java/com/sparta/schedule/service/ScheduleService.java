package com.sparta.schedule.service;

import com.sparta.schedule.dto.create.CreateScheduleReq;
import com.sparta.schedule.dto.create.CreateScheduleRes;
import com.sparta.schedule.dto.read.ReadScheduleRes;
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
    public ReadScheduleRes getScheduleById(Long id) {
        return new ReadScheduleRes(findSchedule(id));
    }

    public List<ReadScheduleRes> getScheduleList() {
        return scheduleRepository.findAllByOrderByCreatedDateDesc().stream()
                .map(ReadScheduleRes::new).toList();
    }


    /**
     * Update
     */
    @Transactional
    public Schedule updateSchedule(Long id, CreateScheduleReq req) {
        Schedule schedule = findSchedule(id);
        checkPassword(req, schedule);
        schedule.update(req);
        return schedule;
    }

    /**
     * Delete
     */
    public Long deleteSchedule(Long id, CreateScheduleReq req) {
        Schedule schedule = findSchedule(id);
        checkPassword(req, schedule);
        scheduleRepository.delete(schedule);
        return id;
    }

    private Schedule findSchedule(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Schedule not found"));
    }

    private static void checkPassword(CreateScheduleReq req, Schedule schedule) {
        if (!schedule.getPassword().equals(req.getPassword())) {
            throw new IllegalArgumentException("Wrong password");
        }
    }

}
