package com.example.blog.domain;

import lombok.Builder;
import lombok.Getter;

/**
 * packageName    : com.example.blog.domain
 * fileName       :
 * author         : njy
 * date           : 2024-10-07
 * description    : <<여기 설명>>
 * <pre>
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-10-07           njy            최초 생성
 * </pre>
 */
@Getter
public class PostEditor {

    private String title;
    private String content;

    @Builder
    public PostEditor(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
