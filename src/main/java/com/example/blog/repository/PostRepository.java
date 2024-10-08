package com.example.blog.repository;

import com.example.blog.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName    : com.example.blog.repository
 * fileName       :
 * author         : njy
 * date           : 2024-10-02
 * description    : <<여기 설명>>
 * <pre>
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-10-02           njy            최초 생성
 * </pre>
 */
public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
}
