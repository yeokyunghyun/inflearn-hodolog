package com.example.blog.service;

import static org.junit.jupiter.api.Assertions.*;

import com.example.blog.crypt.PasswordEncoder;
import com.example.blog.domain.User;
import com.example.blog.exception.InvalidRequest;
import com.example.blog.repository.UserRepository;
import com.example.blog.request.Signup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * packageName    : com.example.blog.service
 * fileName       :
 * author         : njy
 * date           : 2024-10-17
 * description    : <<여기 설명>>
 * <pre>
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-10-17           njy            최초 생성
 * </pre>
 */
@SpringBootTest
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void clear() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입")
    public void test1() {
        PasswordEncoder encoder = new PasswordEncoder();

        Signup signup = Signup.builder().name("yeo").email("hodolman").password("1234").build();

        Long userId = authService.signup(signup);

        User user = userRepository.findById(userId).orElseThrow(InvalidRequest::new);
        Assertions.assertTrue(encoder.matches(signup.getPassword(), user.getPassword()));
    }
}