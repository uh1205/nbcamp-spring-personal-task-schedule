package com.sparta.schedule.reporitory;

import com.sparta.schedule.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByScheduleIdOrderByCreatedDate(Long schedule_id);

}
