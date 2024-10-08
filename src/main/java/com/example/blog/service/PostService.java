package com.example.blog.service;

import com.example.blog.domain.Post;
import com.example.blog.exception.PostNotFound;
import com.example.blog.repository.PostRepository;
import com.example.blog.request.PostCreate;
import com.example.blog.request.PostEdit;
import com.example.blog.request.PostSearch;
import com.example.blog.response.PostResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * packageName    : com.example.blog.service
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

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void save(PostCreate request) {
        Post post = Post.builder()
            .title(request.getTitle())
            .content(request.getContent())
            .build();
        postRepository.save(post);
    }

    public PostResponse get(Long id) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFound::new);
        System.out.println("hello1");
        PostResponse postResponse = PostResponse.builder().id(post.getId()).title(post.getTitle()).content(post.getContent()).build();

        /*
        * Controller -> WebPostService -> Repository
        * Controller -> PostService -> Repository
        * */
        return postResponse;
    }

    public List<PostResponse> getList(PostSearch postSearch) {
//        Pageable page = PageRequest.of(pageable, 5);

        return postRepository.getList(postSearch).stream()
            .map(PostResponse::new)
            .collect(Collectors.toList());
    }

    @Transactional
    public void edit(Long id, PostEdit postEdit) {
        Post post = postRepository.findById(id)
            .orElseThrow(() -> new PostNotFound());

        post.edit(
            postEdit.getTitle() != null ? postEdit.getTitle() : post.getTitle(),
            postEdit.getContent() != null ? postEdit.getContent() : post.getContent());
    }

    public void delete(long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFound());
        postRepository.delete(post);
    }
}