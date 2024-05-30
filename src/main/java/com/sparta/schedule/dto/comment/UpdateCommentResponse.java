package com.sparta.schedule.dto.comment;

import com.sparta.schedule.entity.Comment;
import lombok.Getter;

@Getter
public class UpdateCommentResponse {

    private final Long commentId;
    private final String content;

    public UpdateCommentResponse(Comment comment) {
        this.commentId = comment.getId();
        this.content = comment.getContent();
    }

}
