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
@RequestMapping("/schedules/{scheduleId}/comments")
public class CommentController {

    private final CommentService commentService;

    /**
     * 새로운 댓글 생성 후 해당 일정에 추가
     */
    @PostMapping
    public ResponseEntity<CommonResponse<CommentResponse>> createComment(
            @PathVariable Long scheduleId,
            @RequestBody CommentRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Comment comment = commentService.createComment(scheduleId, request, userDetails.getUser());
        CommentResponse response = new CommentResponse(comment);

        return ResponseEntity.ok()
                .body(CommonResponse.<CommentResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .msg("댓글 추가 성공")
                        .data(response)
                        .build());
    }

    /**
     * 모든 댓글 조회
     */
    @GetMapping
    public ResponseEntity<CommonResponse<List<CommentResponse>>> getComments(
            @PathVariable Long scheduleId
    ) {
        List<CommentResponse> response = commentService.getComments(scheduleId)
                .stream().map(CommentResponse::new).toList();

        return ResponseEntity.ok()
                .body(CommonResponse.<List<CommentResponse>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .msg("모든 댓글 불러오기 성공")
                        .data(response)
                        .build());
    }

    /**
     * 댓글 조회
     */
    @GetMapping("/{commentId}")
    public ResponseEntity<CommonResponse<CommentResponse>> getComment(
            @PathVariable Long commentId
    ) {
        Comment comment = commentService.getComment(commentId);
        CommentResponse response = new CommentResponse(comment);

        return ResponseEntity.ok()
                .body(CommonResponse.<CommentResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .msg("댓글 불러오기 성공")
                        .data(response)
                        .build());
    }

    /**
     * 댓글 수정
     */
    @PutMapping("/{commentId}")
    public ResponseEntity<CommonResponse<CommentResponse>> updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Comment comment = commentService.updateComment(commentId, request, userDetails.getUser());
        CommentResponse response = new CommentResponse(comment);

        return ResponseEntity.ok()
                .body(CommonResponse.<CommentResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .msg("댓글 수정 성공")
                        .data(response)
                        .build());
    }

    /**
     * 댓글 삭제
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
