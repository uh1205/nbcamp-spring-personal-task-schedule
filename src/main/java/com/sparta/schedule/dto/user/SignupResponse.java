package com.sparta.schedule.dto.user;

import com.sparta.schedule.entity.User;
import com.sparta.schedule.entity.UserRoleEnum;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SignupResponse {

    private final Long userId;
    private final String nickname;
    private final String username;
    private final String password;
    private final UserRoleEnum role;
    private final LocalDate createdAt;

    public SignupResponse(User user) {
        this.userId = user.getId();
        this.nickname = user.getNickname();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.role = user.getRole();
        this.createdAt = user.getCreatedDate();
    }

}
