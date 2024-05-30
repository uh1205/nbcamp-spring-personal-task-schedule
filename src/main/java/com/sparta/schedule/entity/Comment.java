package com.sparta.schedule.entity;

import com.sparta.schedule.Timestamped;
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

    public void checkUser(User user) {
        if (!user.equals(this.user)) {
            throw new IllegalArgumentException("User does not belong to this comment");
        }
    }

    public void update(UpdateCommentRequest request) {
        this.content = request.getContent();
    }
}
