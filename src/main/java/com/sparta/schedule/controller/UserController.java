package com.sparta.schedule.controller;

import com.sparta.schedule.dto.CommonResponse;
import com.sparta.schedule.dto.user.SignupRequest;
import com.sparta.schedule.dto.user.SignupResponse;
import com.sparta.schedule.entity.User;
import com.sparta.schedule.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.sparta.schedule.controller.ControllerUtils.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    /**
     * 회원가입
     */
    @PostMapping
    public ResponseEntity<CommonResponse<?>> signup(
            @Valid @RequestBody SignupRequest request,
            BindingResult bindingResult
    ) throws IllegalArgumentException {
        // 예외 처리
        if (bindingResult.hasErrors()) {
            return getFieldErrorResponseEntity(bindingResult, "Failed to signup");
        }
        try {
            User user = userService.signup(request);
            SignupResponse response = new SignupResponse(user);

            return getResponseEntity(response, "Signup successful");

        } catch (Exception e) {
            return getBadRequestResponseEntity(e);
        }
    }

}
