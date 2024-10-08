package com.example.blog.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.example.blog.request
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
@Getter
@Setter
@Builder
public class PostSearch {

    @Builder.Default
    private int page = 1;
    @Builder.Default
    private int size = 10;
}
