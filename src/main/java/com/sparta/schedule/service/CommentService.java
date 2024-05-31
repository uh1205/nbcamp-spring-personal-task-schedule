package com.sparta.schedule.service;

import com.sparta.schedule.dto.comment.CommentRequest;
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
    public Comment createComment(Long scheduleId, CommentRequest request, User user) {
        Schedule schedule = scheduleService.getSchedule(scheduleId);
        Comment comment = commentRepository.save(new Comment(request, user, schedule));
        schedule.addComment(comment);

        return comment;
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
    public Comment getComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() ->
                new IllegalArgumentException("Comment with id " + commentId + " does not exist"));
    }

    /**
     * 해당 댓글 수정
     */
    @Transactional
    public Comment updateComment(Long commentId, CommentRequest request, User user) {
        Comment comment = findCommentAndVerifyUser(commentId, user);
        comment.update(request);

        return comment;
    }

    /**
     * 해당 댓글 삭제
     */
    public Long deleteComment(Long commentId, User user) {
        Comment comment = findCommentAndVerifyUser(commentId, user);
        commentRepository.delete(comment);

        return commentId;
    }

    // commentId 로 댓글을 찾고, 사용자가 해당 댓글의 작성자인지 검증
    private Comment findCommentAndVerifyUser(Long commentId, User user) {
        Comment comment = getComment(commentId);

        if (!Objects.equals(comment.getUser().getId(), user.getId())) {
            throw new IllegalArgumentException("Comment with id " + commentId +
                    " does not belong to user with id " + user.getId());
        }

        return comment;
    }

}
