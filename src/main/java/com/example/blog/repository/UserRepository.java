package com.example.blog.repository;

import com.example.blog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName    : com.example.blog.repository
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
 */public interface UserRepository extends JpaRepository<User, Long> {
}
