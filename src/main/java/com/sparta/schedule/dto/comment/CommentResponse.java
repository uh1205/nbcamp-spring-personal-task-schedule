package com.sparta.schedule.dto.comment;

import com.sparta.schedule.domain.entity.Comment;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CommentResponse {

    private final Long commentId;
    private final Long scheduleId;
    private final Long userId;
    private final String content;
    private final LocalDate createdAt;

    public CommentResponse(Comment comment) {
        this.commentId = comment.getId();
        this.scheduleId = comment.getSchedule().getId();
        this.userId = comment.getUser().getId();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedDate();
    }
}
