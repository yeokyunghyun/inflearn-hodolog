package com.example.blog.repository;

import com.example.blog.domain.Post;
import com.example.blog.domain.QPost;
import com.example.blog.request.PostSearch;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

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
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> getList(PostSearch postSearch) {
        return jpaQueryFactory
            .selectFrom(QPost.post)
            .limit(postSearch.getSize())
            .offset((long)(postSearch.getPage() - 1) * 10)
            .orderBy(QPost.post.id.desc())
            .fetch();
    }
}
