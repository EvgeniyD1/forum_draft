package com.forum.forum_draft.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-table-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-table-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class TopicControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TopicController topicController;

    @Test
    void accessTest() throws Exception {
        this.mockMvc.perform(get("/topic/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void topicTest() throws Exception {
        this.mockMvc.perform(get("/topic/2"))
                .andDo(print())
                .andExpect(xpath("//*[@id='message']/div[1]/h5").string("admin-topic-1"))
                .andExpect(xpath("//*[@id='message']/div[1]/p").string("text-admin-2"))
                .andExpect(xpath("//*[@id='message']/div[2]/small[2]/a").string("ADMIN"))
                .andExpect(xpath("//*[@id='message']/div[2]/small[3]/a").string("#tag-1"));
    }

    @Test
    void commentsTest() throws Exception {
        this.mockMvc.perform(get("/topic/2"))
                .andDo(print())
                .andExpect(xpath("//*[@id='comment-list']/div").nodeCount(2))
                .andExpect(xpath("//*[@id='comment-list']/div[@data-id=6]/div[1]/p")
                        .string("cool"))
                .andExpect(xpath("//*[@id='comment-list']/div[@data-id=6]/div[2]/small[3]/a")
                        .string("user-4"))
                .andExpect(xpath("//*[@id='comment-list']/div[@data-id=6]/div[2]/small[4]")
                        .string("answer to id5"));
    }

    @Test
    void noCommentsTest() throws Exception {
        this.mockMvc.perform(get("/topic/12"))
                .andDo(print())
                .andExpect(xpath("//*[@id='comment-list']/p").string("No comments"));
    }

    @Test
    @WithUserDetails("ADMIN")
    void addCommentTest() throws Exception {
        this.mockMvc.perform(
                post("/topic/addComment")
                        .param("text", "newComment")
                        .param("messageId", "2")
                        .with(csrf())
        )
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/topic/2"));

        this.mockMvc.perform(get("/topic/2"))
                .andDo(print())
                .andExpect(xpath("//*[@id='comment-list']/div").nodeCount(3))
                .andExpect(xpath("//*[@id='comment-list']/div[1]/div[2]/small[1]").string("id7"));
    }

    @Test
    @WithUserDetails("ADMIN")
    void updateCommentTest() throws Exception {
        this.mockMvc.perform(
                post("/topic/updateComment")
                        .param("text", "updateComment")
                        .param("messageId", "2")
                        .param("commentId", "5")
                        .with(csrf())
        )
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/topic/2"));

        this.mockMvc.perform(get("/topic/2"))
                .andDo(print())
                .andExpect(xpath("//*[@id='comment-list']/div").nodeCount(2))
                .andExpect(xpath("//*[@id='comment-list']/div[@data-id=5]/div[1]/p")
                        .string("updateComment"));
    }

    @Test
    @WithUserDetails("ADMIN")
    void commentCommentTest() throws Exception {
        this.mockMvc.perform(
                post("/topic/commentingComment")
                        .param("text", "commentComment")
                        .param("messageId", "2")
                        .param("parentId", "6")
                        .with(csrf())
        )
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/topic/2"));

        this.mockMvc.perform(get("/topic/2"))
                .andDo(print())
                .andExpect(xpath("//*[@id='comment-list']/div").nodeCount(3))
                .andExpect(xpath("//*[@id='comment-list']/div[@data-id=7]/div[1]/p")
                        .string("commentComment"))
                .andExpect(xpath("//*[@id='comment-list']/div[@data-id=7]/div[2]/small[4]")
                        .string("answer to id6"));
    }


}
