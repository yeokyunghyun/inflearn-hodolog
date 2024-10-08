package com.example.blog.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.blog.domain.Post;
import com.example.blog.repository.PostRepository;
import com.example.blog.request.PostCreate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * packageName    : com.example.blog.controller
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

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api.hodolman.com", uriPort = 443)
@ExtendWith(RestDocumentationExtension.class)
public class PostControllerDocTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ObjectMapper objectMapper;

/*    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(documentationConfiguration(restDocumentation))
            .build();
    }*/

    @Test
    @DisplayName("글 단건 조회 테스트")
    void test1() throws Exception {

        Post post = Post.builder()
            .title("제목1")
            .content("내용 1")
            .build();

        postRepository.save(post);

        this.mockMvc.perform(RestDocumentationRequestBuilders
                .get("/posts/{postId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("post-inquiry", RequestDocumentation.pathParameters(
                RequestDocumentation.parameterWithName("postId").description("게시글 ID")),

                PayloadDocumentation.responseFields(
                    PayloadDocumentation.fieldWithPath("id").description("게시글 ID"),
                    PayloadDocumentation.fieldWithPath("title").description("제목"),
                    PayloadDocumentation.fieldWithPath("content").description("내용")
                )
            ));
    }

    @Test
    @DisplayName("글 등록")
    void test2() throws Exception {
        // given
        PostCreate request = PostCreate.builder().
            title("제목").
            content("내용").
            build();

        String json = objectMapper.writeValueAsString(request);

        this.mockMvc.perform(RestDocumentationRequestBuilders
                .post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("post-create",
                PayloadDocumentation.requestFields(
                    PayloadDocumentation.fieldWithPath("title").description("제목"),
                    PayloadDocumentation.fieldWithPath("content").description("내용")
                )
            ));
    }
}