package com.example.blog.service;

import com.example.blog.crypt.PasswordEncoder;
import com.example.blog.domain.User;
import com.example.blog.repository.UserRepository;
import com.example.blog.request.Signup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public Long signup(Signup signup) {

        PasswordEncoder encoder = new PasswordEncoder();

        String encryptedPassword = encoder.encrypt(signup.getPassword());
        User user = User.builder()
            .name(signup.getName())
            .email(signup.getEmail())
            .password(encryptedPassword)
            .build();

        userRepository.save(user);
        return user.getId();
    }
}
