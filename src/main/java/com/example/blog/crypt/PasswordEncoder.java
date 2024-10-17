package com.example.blog.crypt;

import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

/**
 * packageName    : com.example.blog.crypt
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

public class PasswordEncoder {

    SCryptPasswordEncoder encoder = new SCryptPasswordEncoder(
        8,
        8,
        1,
        32,
        16
    );

    public String encrypt(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}
