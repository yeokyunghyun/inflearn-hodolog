package com.example.blog.controller;

import com.example.blog.domain.Post;
import com.example.blog.repository.PostRepository;
import com.example.blog.request.PostCreate;
import com.example.blog.request.PostEdit;
import com.example.blog.request.PostSearch;
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

@AutoConfigureMockMvc
@SpringBootTest
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PostRepository postRepository;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void deleteRepository() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("/posts 요청 시 Hello World 출력")
    void test() throws Exception {
        //given
        PostCreate request = PostCreate.builder()
            .title("제목입니다.")
            .content("내용입니다.")
            .build();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("{}"))
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("/posts 요청 시 title값은 필수이다.")
    void test2() throws Exception {
        //given
        PostCreate request = PostCreate.builder()
            .content("내용입니다.")
            .build();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(MockMvcRequestBuilders.post("/posts").contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("400"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("잘못된 요청입니다."))
            .andExpect(MockMvcResultMatchers.jsonPath("$.validation.title").value("타이틀을 입력해주세요.")).andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("/posts 요청 시 title값은 필수이다.")
    void test3() throws Exception {
        //given
        PostCreate request = PostCreate.builder()
            .title("제목입니다.")
            .content("내용입니다.")
            .build();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        Assertions.assertEquals(1L, postRepository.count());
    }

    @Test
    @DisplayName("글 여러개 조회")
    void test4() throws Exception {
        Post post1 = Post.builder().title("foo1").content("bar1").build();
        Post post2 = Post.builder().title("foo2").content("bar2").build();
        postRepository.save(post1);
        postRepository.save(post2);


        mockMvc.perform(MockMvcRequestBuilders.get("/posts")
                .param("page", "1")  // 쿼리 파라미터로 전달
                .param("size", "10")  // 쿼리 파라미터로 전달
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()", Matchers.is(2)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(post2.getId()))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("foo2"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].content").value("bar2"))
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void test5() throws Exception{
        List<Post> requestPosts = IntStream.range(1, 31)
            .mapToObj(i -> Post.builder()
                .title("호돌맨 제목" + i)
                .content("반포자이" + i)
                .build())
            .collect(Collectors.toList());
        postRepository.saveAll(requestPosts);

        mockMvc.perform(MockMvcRequestBuilders.get("/posts?page=1&sort=id,desc&size=5")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("글 제목 수정")
    void test6() throws Exception{
        // given
        Post post = Post.builder()
            .title("호돌맨")
            .content("반포자이")
            .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
            .title("호돌걸")
            .content("반포자이")
            .build();

        mockMvc.perform(MockMvcRequestBuilders.patch("/posts/{postId}", post.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(postEdit)))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("게시글 삭제")
    void test7() throws Exception{
        // given
        Post post = Post.builder()
            .title("호돌맨")
            .content("반포자이")
            .build();


        postRepository.save(post);

        // expected
        mockMvc.perform(MockMvcRequestBuilders.delete("/posts/{postId}", post.getId())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("존재하지 않는 게시글 조회")
    void test8() throws Exception{
        // expected
        mockMvc.perform(MockMvcRequestBuilders.delete("/posts/{postId}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("글 제목에는 '바보'라는 말이 들어가면 안됨")
    void test9() throws Exception{
        // given
        PostCreate request = PostCreate.builder()
            .title("나는 바보입니다.")
            .content("반포자이")
            .build();

        String json = objectMapper.writeValueAsString(request);
        // expected
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andDo(MockMvcResultHandlers.print());
    }
}

// GET /post/{postId} -> 단건 조회
// POST /posts -> 게시글 등록
// 클라이언트 입장 어떤 API 있는 지 모름
// Spring RestDocs
// 운영코드에 -> 영향 x
// 코드 수정 -> 문서를 수정 x  =>  코드(기능) <!-> 문서
// Test 케이스 실행 -> 문서 자동 생성