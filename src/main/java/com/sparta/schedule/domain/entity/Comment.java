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
    public Comment(CommentRequest request, User user, Schedule schedule) {
        this.content = request.getContent();
        this.user = user;
        this.schedule = schedule;
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
     * 수정 메서드
     */
    public void update(CommentRequest request) {
        this.content = request.getContent();
    }

}
