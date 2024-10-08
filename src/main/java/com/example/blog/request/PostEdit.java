package com.example.blog.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * packageName    : com.example.blog.request
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
@Setter
@ToString
public class PostEdit {

    @NotBlank(message = "타이틀을 입력하세요.")
    private String title;

    @NotBlank(message = "콘텐츠를 입력하세요.")
    private String content;

    @Builder
    public PostEdit(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
