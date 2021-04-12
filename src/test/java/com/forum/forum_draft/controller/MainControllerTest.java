package com.forum.forum_draft.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-table-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-table-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MainController mainController;

    @Test
    @WithUserDetails("ADMIN")
    void mainPageTest() throws Exception {
        this.mockMvc.perform(get("/main"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='navbarSupportedContent']/ul[2]/li[1]/a")
                        .string("ADMIN"));
    }

    @Test
    void messagesPageDefaultTest() throws Exception {
        this.mockMvc.perform(get("/main"))
                .andDo(print())
                .andExpect(xpath("//div[@id='message-list']/div").nodeCount(9));
    }

    @Test
    void messagesPage0Size6Test() throws Exception {
        this.mockMvc.perform(get("/main?page=0&size=6"))
                .andDo(print())
                .andExpect(xpath("//div[@id='message-list']/div").nodeCount(6));
    }

    @Test
    void messagesPage1Size6Test() throws Exception {
        this.mockMvc.perform(get("/main?page=1&size=6"))
                .andDo(print())
                .andExpect(xpath("//div[@id='message-list']/div").nodeCount(6));
    }

    @Test
    void messagesPage0Size9Test() throws Exception {
        this.mockMvc.perform(get("/main?page=0&size=9"))
                .andDo(print())
                .andExpect(xpath("//div[@id='message-list']/div").nodeCount(9));
    }

    @Test
    void messagesPage1Size9Test() throws Exception {
        this.mockMvc.perform(get("/main?page=1&size=9"))
                .andDo(print())
                .andExpect(xpath("//div[@id='message-list']/div").nodeCount(3));
    }

    @Test
    void messagesPage0Size12Test() throws Exception {
        this.mockMvc.perform(get("/main?page=0&size=12"))
                .andDo(print())
                .andExpect(xpath("//div[@id='message-list']/div").nodeCount(12));
    }

    @Test
    void messagesPage1Size12Test() throws Exception {
        this.mockMvc.perform(get("/main?page=1&size=12"))
                .andDo(print())
                .andExpect(xpath("//*[@id='message-list']/div/p").string("No message"));
    }

    @Test
    void messagesPage0Size15Test() throws Exception {
        this.mockMvc.perform(get("/main?page=0&size=15"))
                .andDo(print())
                .andExpect(xpath("//div[@id='message-list']/div").nodeCount(12));
    }

    @Test
    void messagesPage1Size15Test() throws Exception {
        this.mockMvc.perform(get("/main?page=1&size=15"))
                .andDo(print())
                .andExpect(xpath("//div[@id='message-list']/div/p").string("No message"));
    }

    @Test
    void messagesSearchTest() throws Exception {
        this.mockMvc.perform(get("/main").param("search", "tag-1"))
                .andDo(print())
                .andExpect(xpath("//*[@id='message-list']/div").nodeCount(4))
                .andExpect(xpath("//*[@id='message-list']/div[@data-id=1]").exists())
                .andExpect(xpath("//*[@id='message-list']/div[@data-id=2]").exists())
                .andExpect(xpath("//*[@id='message-list']/div[@data-id=3]").exists())
                .andExpect(xpath("//*[@id='message-list']/div[@data-id=4]").exists());
    }

    @Test
    @WithUserDetails("ADMIN")
    void addMessageToListTest() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file",
                "", /*OriginalName.txt*/
                MediaType.TEXT_PLAIN_VALUE,
                "file".getBytes());
        MockHttpServletRequestBuilder multipart = multipart("/main")
                .file(file)
                .param("topicName", "topic")
                .param("text", "news")
                .param("tag", "new tag")
                .with(csrf());

        MockMvc mockMvc
                = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(multipart)
                .andExpect(status().is3xxRedirection());

        this.mockMvc.perform(get("/main"))
                .andDo(print())
                .andExpect(xpath("//*[@id='message-list']/div").nodeCount(9))
                .andExpect(xpath("//*[@id='message-list']/div[@data-id=13]").exists())
                .andExpect(xpath("//*[@id='message-list']/div[@data-id=13]/div/div/h5/a")
                        .string("topic"))
                .andExpect(xpath("//*[@id='message-list']/div[@data-id=13]/div/div/p")
                        .string("news"))
                .andExpect(xpath("//*[@id='message-list']/div[@data-id=13]/div/div/small[@id='tag']/a")
                        .string("#new tag"))
                .andExpect(xpath("//*[@id='message-list']/div[@data-id=13]/div/div/small[@id='author']/a")
                        .string("ADMIN"))
                .andExpect(xpath("//*[@id='message-list']/div[@data-id=13]/div/div/a")
                        .string("update"));
    }

    @Test
    @WithUserDetails("user-1")
    void mainMyNewsTest() throws Exception {
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='navbarSupportedContent']/ul[1]/li[3]/a")
                        .string("My News"));
    }

    @Test
    @WithUserDetails("ADMIN")
    void myNews1Test() throws Exception {
        this.mockMvc.perform(get("/main/sub/1"))
                .andDo(print())
                .andExpect(xpath("//div[@id='message-list']/div").nodeCount(8));
    }

    @Test
    @WithUserDetails("user-3")
    void myNews2Test() throws Exception {
        this.mockMvc.perform(get("/main/sub/4"))
                .andDo(print())
                .andExpect(xpath("//div[@id='message-list']/div").nodeCount(4));
    }

    @Test
    @WithUserDetails("user-4")
    void myNews3Test() throws Exception {
        this.mockMvc.perform(get("/main/sub/5"))
                .andDo(print())
                .andExpect(xpath("//div[@id='message-list']/div/p").string("No message"));
    }

    @Test
    @WithUserDetails("ADMIN")
    void mainMessageGetUpdateTest() throws Exception {
        this.mockMvc.perform(get("/main/update/1"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='topicName']").string("admin-topic-1"))
                .andExpect(xpath("//*[@id='text']").string("text-admin-1"));
                /*КАК тестировать input value*/
                /*.andExpect(xpath("//*[@id='tag'").string("tag-1"));*/
    }

    @Test
    @WithUserDetails("ADMIN")
    void mainMessageUpdateTest() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file",
                "", /*OriginalName.txt*/
                MediaType.TEXT_PLAIN_VALUE,
                "file".getBytes());
        MockHttpServletRequestBuilder multipart = multipart("/main/update/1")
                .file(file)
                .param("topicName", "new topic")
                .param("text", "new news")
                .param("tag", "new tag")
                .with(csrf());

        MockMvc mockMvc
                = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(multipart)
                .andExpect(status().is3xxRedirection());

        this.mockMvc.perform(get("/main?page=0&size=15"))
                .andDo(print())
                .andExpect(xpath("//*[@id='message-list']/div").nodeCount(12))
                .andExpect(xpath("//*[@id='message-list']/div[@data-id=1]").exists())
                .andExpect(xpath("//*[@id='message-list']/div[@data-id=1]/div/div/h5/a")
                        .string("new topic"))
                .andExpect(xpath("//*[@id='message-list']/div[@data-id=1]/div/div/p")
                        .string("new news"))
                .andExpect(xpath("//*[@id='message-list']/div[@data-id=1]/div/div/small[@id='tag']/a")
                        .string("#new tag"))
                .andExpect(xpath("//*[@id='message-list']/div[@data-id=1]/div/div/small[@id='author']/a")
                        .string("ADMIN"))
                .andExpect(xpath("//*[@id='message-list']/div[@data-id=1]/div/div/a")
                        .string("update"));
    }

    @Test
    @WithUserDetails("user-1")
    void mainMessageGetUpdateNotAuthorisedTest() throws Exception {
        this.mockMvc.perform(get("/main/update/1"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='not-authorised']").string("Not authorised"));

    }

}
