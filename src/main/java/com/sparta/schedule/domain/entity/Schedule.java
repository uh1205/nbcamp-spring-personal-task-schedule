package com.sparta.schedule.domain.entity;

import com.sparta.schedule.domain.Timestamped;
import com.sparta.schedule.dto.schedule.ScheduleRequest;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Schedule extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Comment> comments = new ArrayList<>();

    /**
     * 생성자
     */
    public Schedule(ScheduleRequest request, User user) {
        this.title = request.getTitle();
        this.content = request.getContent();
        this.user = user;
    }

    protected Schedule() {
    }

    /**
     * 연관관계 편의 메서드
     */
    public void setUser(User user) {
        this.user = user;
        user.addSchedule(this);
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.setSchedule(this);
    }

    /**
     * 검증 메서드
     */
    public void verify(User user) {
        if (!this.user.equals(user)) {
            throw new IllegalArgumentException("User does not belong to this schedule");
        }
    }

    /**
     * 수정 메서드
     */
    public void update(ScheduleRequest request) {
        this.title = request.getTitle();
        this.content = request.getContent();
    }

}
