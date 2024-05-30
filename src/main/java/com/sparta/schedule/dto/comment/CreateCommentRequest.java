package com.sparta.schedule.dto.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreateCommentRequest {

    @NotBlank
    private Long scheduleId;

    @NotBlank
    private String content;

}
