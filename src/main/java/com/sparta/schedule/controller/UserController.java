package com.sparta.schedule.controller;

import com.sparta.schedule.dto.CommonResponse;
import com.sparta.schedule.dto.user.SignupRequest;
import com.sparta.schedule.dto.user.SignupResponse;
import com.sparta.schedule.entity.User;
import com.sparta.schedule.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j(topic = "UserController")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    /**
     * 회원가입
     */
    @PostMapping
    public ResponseEntity<CommonResponse<SignupResponse>> signup(
            @Valid @RequestBody SignupRequest request, BindingResult bindingResult
    ) {
        // 예외 처리
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (!fieldErrors.isEmpty()) {
            for (FieldError fieldError : fieldErrors) {
                log.error("{} 필드 : {}", fieldError.getField(), fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest()
                    .body(CommonResponse.<SignupResponse>builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .msg("회원가입 실패")
                            .data(null)
                            .build());
        }

        User user = userService.signup(request);
        SignupResponse response = new SignupResponse(user);

        return ResponseEntity.ok()
                .body(CommonResponse.<SignupResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .msg("회원가입 성공")
                        .data(response)
                        .build());
    }

}
