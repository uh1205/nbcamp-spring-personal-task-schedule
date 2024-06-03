package com.sparta.schedule.reporitory;

import com.sparta.schedule.domain.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findAllByOrderByCreatedDateDesc();

}
