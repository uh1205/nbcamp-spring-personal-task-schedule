package com.sparta.schedule.domain.entity;

import com.sparta.schedule.domain.Timestamped;
import com.sparta.schedule.dto.comment.CommentRequest;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Comment extends Timestamped {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    /**
     * 생성자
     */
    public static Comment createComment(CommentRequest request, Schedule schedule, User user) {
        Comment comment = new Comment(request.getContent());
        comment.setSchedule(schedule);
        comment.setUser(user);

        return comment;
    }

    private Comment(String content) {
        this.content = content;
    }

    protected Comment() {
    }

    /**
     * 연관관계 편의 메서드
     */
    public void setUser(User user) {
        this.user = user;
        user.addComment(this);
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
        schedule.addComment(this);
    }

    /**
     * 검증 메서드
     */
    public void verify(Long scheduleId) {
        if (!this.schedule.getId().equals(scheduleId)) {
            throw new IllegalArgumentException("Schedule does not belong to this comment");
        }
    }

    public void verify(User user) {
        if (!this.user.equals(user)) {
            throw new IllegalArgumentException("User does not belong to this comment");
        }
    }

    /**
     * 수정 메서드
     */
    public void update(CommentRequest request) {
        this.content = request.getContent();
    }

}
