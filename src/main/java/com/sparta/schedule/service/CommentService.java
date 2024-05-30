package com.sparta.schedule.service;

import com.sparta.schedule.dto.comment.*;
import com.sparta.schedule.entity.Comment;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.entity.User;
import com.sparta.schedule.reporitory.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final ScheduleService scheduleService;
    private final CommentRepository commentRepository;

    /**
     * 해당 일정에 새로운 댓글 추가
     */
    public Comment createComment(CreateCommentRequest request, User user) {
        Schedule schedule = scheduleService.getSchedule(request.getScheduleId());
        Comment comment = commentRepository.save(new Comment(request, user, schedule));
        schedule.addComment(comment);

        return comment;
    }

    /**
     * 해당 일정의 모든 댓글 불러오기
     */
    public List<Comment> getComments(Long scheduleId) {
        return commentRepository.findAllByScheduleIdOrderByCreatedDate(scheduleId);
    }

    /**
     * 해당 댓글 수정
     */
    @Transactional
    public Comment updateComment(Long id, UpdateCommentRequest request, User user) {
        Comment comment = findCommentAndVerifyUser(id, user);
        comment.update(request);

        return comment;
    }

    /**
     * 해당 댓글 삭제
     */
    public Long deleteComment(Long id, User user) {
        Comment comment = findCommentAndVerifyUser(id, user);
        commentRepository.delete(comment);

        return id;
    }

    private Comment findCommentAndVerifyUser(Long id, User user) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글을 찾을 수 없습니다.")
        );

        if (!Objects.equals(comment.getUser().getId(), user.getId())) {
            throw new IllegalArgumentException("해당 댓글의 작성자가 아닙니다.");
        }

        return comment;
    }

}
