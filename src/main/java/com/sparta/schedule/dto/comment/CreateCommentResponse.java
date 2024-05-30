package com.sparta.schedule.dto.comment;

import com.sparta.schedule.entity.Comment;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CreateCommentResponse {

    private final Long commentId;
    private final String content;
    private final Long userId;
    private final Long scheduleId;
    private final LocalDate createdAt;

    public CreateCommentResponse(Comment comment) {
        this.commentId = comment.getId();
        this.content = comment.getContent();
        this.userId = comment.getUser().getId();
        this.scheduleId = comment.getSchedule().getId();
        this.createdAt = comment.getCreatedDate();
    }
}
