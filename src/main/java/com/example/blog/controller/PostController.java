package com.example.blog.controller;

import com.example.blog.request.PostCreate;
import com.example.blog.request.PostEdit;
import com.example.blog.request.PostSearch;
import com.example.blog.response.PostResponse;
import com.example.blog.service.PostService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName    : com.example.blog.controller
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
@RestController
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;
    // SSR -> jsp, thymeleaf, mustache, freemarker
        // html rendering
    // SPA -> vue
        // -> javascript + <-> API (JSON)

    @PostMapping("/posts")
    public Map<String, String> post(@RequestBody @Valid PostCreate request) {
        request.validation(request.getTitle(), request.getContent());

        postService.save(request);
        return Map.of();
    }

    /*
    * /posts -> 글 전체 조회 (검색 + 페이징)
    * /posts/{postId} -> 글 한개만 조회
    * */

    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable(name = "postId") Long id) {
        PostResponse postResponse = postService.get(id);
        return postResponse;
    }

    @GetMapping("/posts")
    public List<PostResponse> getList(@ModelAttribute PostSearch postSearch) {
        return postService.getList(postSearch);
    }

    @PatchMapping("/posts/{postId}")
    public void edit(@PathVariable Long postId, @RequestBody @Valid PostEdit request) {
        postService.edit(postId, request);
    }

    @DeleteMapping("/posts/{postId}")
    public void delete(@PathVariable Long postId) {
        postService.delete(postId);
    }
}
