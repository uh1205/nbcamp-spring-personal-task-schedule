package com.sparta.schedule.controller;

import com.sparta.schedule.dto.CommonResponse;
import com.sparta.schedule.dto.comment.CommentRequest;
import com.sparta.schedule.dto.comment.CommentResponse;
import com.sparta.schedule.entity.Comment;
import com.sparta.schedule.security.UserDetailsImpl;
import com.sparta.schedule.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sparta.schedule.controller.ControllerUtils.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules/{scheduleId}/comments")
public class CommentController {

    private final CommentService commentService;

    /**
     * 새로운 댓글 생성 후 해당 일정에 추가
     */
    @PostMapping
    public ResponseEntity<CommonResponse<?>> createComment(
            @PathVariable Long scheduleId,
            @Valid @RequestBody CommentRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            BindingResult bindingResult
    ) throws IllegalArgumentException {
        // 예외 처리
        if (bindingResult.hasErrors()) {
            return getFieldErrorResponseEntity(bindingResult, "Failed to create comment");
        }
        try {
            Comment comment = commentService.createComment(scheduleId, request, userDetails.getUser());
            CommentResponse response = new CommentResponse(comment);

            return getResponseEntity(response, "Comment created successfully");

        } catch (Exception e) {
            return getBadRequestResponseEntity(e);
        }
    }

    /**
     * 모든 댓글 조회
     */
    @GetMapping
    public ResponseEntity<CommonResponse<?>> getComments(
            @PathVariable Long scheduleId
    ) {
        List<CommentResponse> response = commentService.getComments(scheduleId)
                .stream().map(CommentResponse::new).toList();

        return getResponseEntity(response, "Retrieved all comments successfully");
    }

    /**
     * 댓글 조회
     */
    @GetMapping("/{commentId}")
    public ResponseEntity<CommonResponse<?>> getComment(
            @PathVariable Long scheduleId,
            @PathVariable Long commentId
    ) throws IllegalArgumentException {
        try {
            Comment comment = commentService.getComment(scheduleId, commentId);
            CommentResponse response = new CommentResponse(comment);

            return getResponseEntity(response, "Retrieved comment successfully");

        } catch (Exception e) {
            return getBadRequestResponseEntity(e);
        }
    }

    /**
     * 댓글 수정
     */
    @PutMapping("/{commentId}")
    public ResponseEntity<CommonResponse<?>> updateComment(
            @PathVariable Long scheduleId,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            BindingResult bindingResult
    ) throws IllegalArgumentException {
        // 예외 처리
        if (bindingResult.hasErrors()) {
            return getFieldErrorResponseEntity(bindingResult, "Failed to update comment");
        }
        try {
            Comment comment = commentService.updateComment(scheduleId, commentId, request, userDetails.getUser());
            CommentResponse response = new CommentResponse(comment);

            return getResponseEntity(response, "Comment updated successfully");

        } catch (Exception e) {
            return getBadRequestResponseEntity(e);
        }
    }

    /**
     * 댓글 삭제
     */
    @DeleteMapping("/{commentId}")
    public ResponseEntity<CommonResponse<?>> deleteComment(
            @PathVariable Long scheduleId,
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) throws IllegalArgumentException {
        try {
            Long response = commentService.deleteComment(scheduleId, commentId, userDetails.getUser());

            return getResponseEntity(response, "Comment deleted successfully");

        } catch (Exception e) {
            return getBadRequestResponseEntity(e);
        }
    }

}
