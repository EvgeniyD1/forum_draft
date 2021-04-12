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
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-table-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-table-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserController userController;

    @Test
    void userProfileTest() throws Exception {
        this.mockMvc.perform(get("/user/1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithUserDetails("ADMIN")
    void userProfile2Test() throws Exception {
        this.mockMvc.perform(get("/user/1"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='username']/div[2]").string("ADMIN"))
                .andExpect(xpath("//*[@id='email']/div[2]").string("admin@email.com"))
                .andExpect(xpath("//*[@id='phone-number']/div[2]").string("ADMIN-1-phone"))
                .andExpect(xpath("//*[@id='subscribers-count']").string("3"))
                .andExpect(xpath("//*[@id='subscriptions-count']").string("10"))
                .andExpect(xpath("//*[@id='user-messages']/div").nodeCount(4))
                .andExpect(xpath("//*[@data-id=1]/div/h5/a").string("admin-topic-1"))
                .andExpect(xpath("//*[@data-id=1]/div/p").string("text-admin-1"))
                .andExpect(xpath("//*[@data-id=1]/div/small[2]/a").string("ADMIN"))
                .andExpect(xpath("//*[@data-id=1]/div/small[3]/a").string("#tag-1"));
    }

    @Test
    @WithUserDetails("ADMIN")
    void userProfile3Test() throws Exception {
        this.mockMvc.perform(get("/user/10"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='user-messages']/p").string("No message"));
    }

    @Test
    @WithUserDetails("ADMIN")
    void userSubscribersTest() throws Exception {
        this.mockMvc.perform(get("/user/subscribers/1"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='sub-list']/li").nodeCount(3))
                .andExpect(xpath("//*[@id='sub-list']/li[@data-id=2]/a").string("user-1"))
                .andExpect(xpath("//*[@id='sub-list']/li[@data-id=3]/a").string("user-2"))
                .andExpect(xpath("//*[@id='sub-list']/li[@data-id=4]/a").string("user-3"));
    }

    @Test
    @WithUserDetails("user-10")
    void userSubscribeTest() throws Exception {
        this.mockMvc.perform(get("/user/subscribe/1"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/1"));
        this.mockMvc.perform(get("/user/1"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='subscribers-count']").string("4"));
        this.mockMvc.perform(get("/user/11"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='subscriptions-count']").string("1"));
    }

    @Test
    @WithUserDetails("ADMIN")
    void userUnsubscribeTest() throws Exception {
        this.mockMvc.perform(get("/user/unsubscribe/2"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/2"));
        this.mockMvc.perform(get("/user/1"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='subscriptions-count']").string("9"));
        this.mockMvc.perform(get("/user/2"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='subscribers-count']").string("1"));
        this.mockMvc.perform(get("/user/subscribe/2"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/2"));
    }

    @Test
    @WithUserDetails("ADMIN")
    void userSubscriptionsTest() throws Exception {
        this.mockMvc.perform(get("/user/subscriptions/1"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='sub-list']/li").nodeCount(10));
    }

    @Test
    @WithUserDetails("ADMIN")
    void userEditUnauthorisedTest() throws Exception {
        this.mockMvc.perform(get("/user/userPageEdit/10"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='user-editor']/b").string("Not authorised"));
    }

    @Test
    @WithUserDetails("ADMIN")
    void updateUserTest() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file",
                "", /*OriginalName.txt*/
                MediaType.TEXT_PLAIN_VALUE,
                "file".getBytes());
        MockHttpServletRequestBuilder multipart = multipart("/user")
                .file(file)
                .param("username", "NewAdmin")
                .param("email", "NewEmail@email.com")
                .param("phoneNumber", "NewPhone")
                /*во прикол kekw userId в hidden*/
                .param("userId", "1")
                .with(csrf());

        MockMvc mockMvc
                = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(multipart)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        this.mockMvc.perform(get("/user/1"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='username']/div[2]").string("NewAdmin"))
                .andExpect(xpath("//*[@id='email']/div[2]").string("NewEmail@email.com"))
                .andExpect(xpath("//*[@id='phone-number']/div[2]").string("NewPhone"));
    }

}
