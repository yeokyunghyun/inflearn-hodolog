package com.example.blog.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.example.blog.domain.Post;
import com.example.blog.exception.PostNotFound;
import com.example.blog.repository.PostRepository;
import com.example.blog.request.PostCreate;
import com.example.blog.request.PostEdit;
import com.example.blog.response.PostResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * packageName    : com.example.blog.service
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

@AutoConfigureMockMvc
@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void clear() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    void test1() {
        // given
        PostCreate postCreate = PostCreate.builder()
            .title("제목입니다.")
            .content("내용입니다.")
            .build();

        // when
        postService.save(postCreate);
        // then

        assertEquals(1L, postRepository.count());
        Post post = postRepository.findAll().get(0);
        assertEquals("제목입니다.", post.getTitle());
        assertEquals("내용입니다.", post.getContent());
    }

    @Test
    @DisplayName("글 1개 조회0")
    void test2() {
        Post post = Post.builder()
            .title("제목입니다.")
            .content("내용입니다.")
            .build();

        postRepository.save(post);

        PostResponse saved = postService.get(post.getId());

        assertEquals(post.getId(), saved.getId());
    }
    @Test
    @DisplayName("글 1개 조회1")
    void test3() throws Exception {
        Post post = Post.builder()
            .title("제목입니다.")
            .content("내용입니다.")
            .build();

        postRepository.save(post);

        mockMvc.perform(get("/posts/{postId}", post.getId())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(post.getId()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("제목입니다."))
            .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("내용입니다."))
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("글 1페이지 조회")
    void test4() throws Exception {
//        sql -> select, limit, offset
        List<Post> requestPosts = IntStream.range(1, 31)
            .mapToObj(i -> {
                return Post.builder()
                    .title("foo" + i)
                    .content("bar" + i)
                    .build();
            })
            .collect(Collectors.toList());

        postRepository.saveAll(requestPosts);

        mockMvc.perform(MockMvcRequestBuilders.get("/posts?page=1&size=10")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()", Matchers.is(10)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("foo30"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].content").value("bar30"))
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("글 제목 수정")
    void test5() {
        //given
        Post post = Post.builder().title("호돌맨").content("반포자이").build();
        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder().title("호돌걸").content("반포자이").build();

        //when
        postService.edit(post.getId(), postEdit);
        //then
        Post changedPost = postRepository.findById(post.getId()).orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id=" + post.getId()));
        assertEquals("호돌걸", changedPost.getTitle());
        assertEquals("반포자이", changedPost.getContent());
    }

    @Test
    @DisplayName("글 내용 삭제")
    void test6() {
        // given
        Post post = Post.builder()
            .title("호돌맨")
            .content("반포자이")
            .build();
        postRepository.save(post);

        // when
        postService.delete(post.getId());
        // then
        assertEquals(0, postRepository.count());
    }

    @Test
    @DisplayName("글 1개 조회2")
    void test7() {
        // given
        Post post = Post.builder()
            .title("호돌맨")
            .content("반포자이")
            .build();
        postRepository.save(post);

        // expected
        PostNotFound e = Assertions.assertThrows(PostNotFound.class, () -> {
            postService.get(post.getId() + 1L);
        });

        assertEquals("존재하지 않는 ID입니다.", e.getMessage());
    }
}