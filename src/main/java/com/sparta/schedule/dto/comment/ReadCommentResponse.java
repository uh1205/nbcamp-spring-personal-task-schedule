package com.sparta.schedule.dto.comment;

import com.sparta.schedule.entity.Comment;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ReadCommentResponse {

    private final Long commentId;
    private final String nickname;
    private final String content;
    private final LocalDate createdAt;

    public ReadCommentResponse(Comment comment) {
        this.commentId = comment.getId();
        this.nickname = comment.getUser().getNickname();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedDate();
    }
}
