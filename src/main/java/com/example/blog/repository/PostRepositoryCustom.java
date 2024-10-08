package com.example.blog.repository;

import com.example.blog.domain.Post;
import com.example.blog.request.PostSearch;
import java.util.List;

/**
 * packageName    : com.example.blog.repository
 * fileName       :
 * author         : njy
 * date           : 2024-10-04
 * description    : <<여기 설명>>
 * <pre>
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-10-04           njy            최초 생성
 * </pre>
 */
public interface PostRepositoryCustom {
    List<Post> getList(PostSearch postSearch);
}
