package com.sparta.schedule.entity;

import com.sparta.schedule.dto.comment.CreateCommentRequest;
import com.sparta.schedule.dto.comment.UpdateCommentRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
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

    public Comment(CreateCommentRequest request, User user, Schedule schedule) {
        this.content = request.getContent();
        this.user = user;
        this.schedule = schedule;
    }

    public void update(UpdateCommentRequest request) {
        this.content = request.getContent();
    }
}
