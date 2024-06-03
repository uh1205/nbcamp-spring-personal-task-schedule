package com.sparta.schedule.service;

import com.sparta.schedule.dto.user.SignupRequest;
import com.sparta.schedule.domain.entity.User;
import com.sparta.schedule.domain.UserRoleEnum;
import com.sparta.schedule.reporitory.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    /**
     * 회원가입
     */
    @Transactional
    public User signup(SignupRequest request) {
        // username 중복 확인
        String username = request.getUsername();
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username " + username + " already exists");
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (request.isAdmin()) {
            if (!request.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("Admin token does not match");
            }
            role = UserRoleEnum.ADMIN;
        }

        // 사용자 등록
        String nickname = request.getNickname();
//        String password = request.getPassword();
        String password = passwordEncoder.encode(request.getPassword());

        User user = new User(nickname, username, password, role);
        userRepository.save(user);

        return user;
    }

}