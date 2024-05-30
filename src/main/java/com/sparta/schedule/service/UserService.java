package com.sparta.schedule.service;

import com.sparta.schedule.dto.user.SignupRequest;
import com.sparta.schedule.entity.User;
import com.sparta.schedule.UserRoleEnum;
import com.sparta.schedule.reporitory.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User signup(SignupRequest request) {
        // username 중복 확인
        String username = request.getUsername();
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (request.isAdmin()) {
            if (!request.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        // 사용자 등록
        String nickname = request.getNickname();
        String password = passwordEncoder.encode(request.getPassword());

        User user = new User(nickname, username, password, role);
        userRepository.save(user);

        return user;
    }

}