package com.sparta.schedule.entity;

import com.sparta.schedule.dto.schedule.CreateScheduleRequest;
import com.sparta.schedule.dto.schedule.UpdateScheduleRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
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

    @OneToMany(mappedBy = "schedule")
    private final List<Comment> comments = new ArrayList<>();



    public Schedule(CreateScheduleRequest request, User user) {
        this.title = request.getTitle();
        this.content = request.getContent();
        this.user = user;
    }

    public void checkUser(User user) {
        if (!user.equals(this.user)) {
            throw new IllegalArgumentException("User does not belong to this schedule");
        }
    }

    public void update(UpdateScheduleRequest request) {
        this.title = request.getTitle();
        this.content = request.getContent();
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }
}
