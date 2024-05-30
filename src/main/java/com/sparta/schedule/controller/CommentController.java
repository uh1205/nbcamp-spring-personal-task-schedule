package com.sparta.schedule.controller;

import com.sparta.schedule.dto.CommonResponse;
import com.sparta.schedule.dto.comment.*;
import com.sparta.schedule.entity.Comment;
import com.sparta.schedule.security.UserDetailsImpl;
import com.sparta.schedule.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    /**
     * 해당 일정에 새로운 댓글 추가
     */
    @PostMapping
    public ResponseEntity<CommonResponse<CreateCommentResponse>> createComment(
            @RequestBody CreateCommentRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Comment comment = commentService.createComment(request, userDetails.getUser());
        CreateCommentResponse response = new CreateCommentResponse(comment);

        return ResponseEntity.ok()
                .body(CommonResponse.<CreateCommentResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .msg("댓글 추가 성공")
                        .data(response)
                        .build());
    }

    /**
     * 해당 일정의 모든 댓글 불러오기
     */
    @GetMapping
    public ResponseEntity<CommonResponse<List<ReadCommentResponse>> getComments(Long scheduleId) {
        List<ReadCommentResponse> response = commentService.getComments(scheduleId)
                .stream().map(ReadCommentResponse::new).toList();

        return ResponseEntity.ok()
                .body(CommonResponse.<List<ReadCommentResponse>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .msg("모든 댓글 불러오기 성공")
                        .data(response)
                        .build());
    }

    /**
     * 해당 댓글 수정
     */
    @PutMapping("/{commentId}")
    public ResponseEntity<CommonResponse<UpdateCommentResponse>> updateComment(
            @PathVariable Long commentId,
            @RequestBody UpdateCommentRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Comment comment = commentService.updateComment(commentId, request, userDetails.getUser());
        UpdateCommentResponse response = new UpdateCommentResponse(comment);

        return ResponseEntity.ok()
                .body(CommonResponse.<UpdateCommentResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .msg("댓글 수정 성공")
                        .data(response)
                        .build());
    }

    /**
     * 해당 댓글 삭제
     */
    @DeleteMapping("/{commentId}")
    public ResponseEntity<CommonResponse<Long>> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Long response = commentService.deleteComment(commentId, userDetails.getUser());

        return ResponseEntity.ok()
                .body(CommonResponse.<Long>builder()
                        .statusCode(HttpStatus.OK.value())
                        .msg("댓글 삭제 성공")
                        .data(response)
                        .build());
    }

}
