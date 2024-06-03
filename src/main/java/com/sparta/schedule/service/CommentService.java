package com.sparta.schedule.service;

import com.sparta.schedule.dto.comment.CommentRequest;
import com.sparta.schedule.domain.entity.Comment;
import com.sparta.schedule.domain.entity.Schedule;
import com.sparta.schedule.domain.entity.User;
import com.sparta.schedule.reporitory.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final ScheduleService scheduleService;
    private final CommentRepository commentRepository;

    /**
     * 해당 일정에 새로운 댓글 추가
     */
    @Transactional
    public Comment createComment(Long scheduleId, CommentRequest request, User user) {
        Schedule schedule = scheduleService.getSchedule(scheduleId);
        Comment comment = new Comment(request, user, schedule);

        return commentRepository.save(comment);
    }

    /**
     * 해당 일정의 모든 댓글 조회
     */
    public List<Comment> getComments(Long scheduleId) {
        return commentRepository.findAllByScheduleIdOrderByCreatedDate(scheduleId);
    }

    /**
     * 해당 댓글 조회
     */
    public Comment getComment(Long scheduleId, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new IllegalArgumentException("Comment with id " + commentId + " does not exist"));

        if (!Objects.equals(scheduleId, comment.getSchedule().getId())) {
            throw new IllegalArgumentException("Comment with id " + commentId +
                    " does not belong to schedule with id " + scheduleId);
        }

        return comment;
    }

    /**
     * 해당 댓글 수정
     */
    @Transactional
    public Comment updateComment(Long scheduleId, Long commentId, CommentRequest request, User user) {
        Comment comment = findCommentAndVerifyUser(scheduleId, commentId, user);
        comment.update(request);

        return comment;
    }

    /**
     * 해당 댓글 삭제
     */
    @Transactional
    public Long deleteComment(Long scheduleId, Long commentId, User user) {
        Comment comment = findCommentAndVerifyUser(scheduleId, commentId, user);
        commentRepository.delete(comment);

        return commentId;
    }

    private Comment findCommentAndVerifyUser(Long scheduleId, Long commentId, User user) {
        Comment comment = getComment(scheduleId, commentId);

        if (!Objects.equals(comment.getUser().getId(), user.getId())) {
            throw new IllegalArgumentException("Comment with id " + commentId +
                    " does not belong to user with id " + user.getId());
        }

        return comment;
    }

}
