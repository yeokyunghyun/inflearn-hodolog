package com.example.blog.request;

import lombok.Builder;
import lombok.Data;

/**
 * packageName    : com.example.blog.request
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
@Data
@Builder
public class Signup {

    private String email;
    private String name;
    private String password;
}
