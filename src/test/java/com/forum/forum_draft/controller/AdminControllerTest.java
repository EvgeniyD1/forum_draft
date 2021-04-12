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
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AdminController adminController;

    @Test
    @WithUserDetails("user-9")
    void unauthorisedAdminTest() throws Exception {
        this.mockMvc.perform(get("/admin"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("ADMIN")
    void authorisedAdminTest() throws Exception {
        this.mockMvc.perform(get("/admin"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='user-data']/tbody/tr").nodeCount(11))
                .andExpect(xpath("//*[@id='user-data']/tbody/tr[@data-id=2]/th")
                        .string("2"))
                .andExpect(xpath("//*[@id='user-data']/tbody/tr[@data-id=2]/td[1]/a")
                        .string("user-1"))
                .andExpect(xpath("//*[@id='user-data']/tbody/tr[@data-id=2]/td[2]")
                        .string("user-1@email.com"))
                .andExpect(xpath("//*[@id='user-data']/tbody/tr[@data-id=2]/td[3]")
                        .string("user-1-phone"))
                .andExpect(xpath("//*[@id='user-data']/tbody/tr[@data-id=2]/td[4]")
                        .string("ADMIN USER "));
    }

    @Test
    @WithUserDetails("ADMIN")
    void adminSearchTest() throws Exception {
        this.mockMvc.perform(get("/admin?search=user-10"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='user-data']/tbody/tr").nodeCount(1))
                .andExpect(xpath("//*[@id='user-data']/tbody/tr/th").string("11"));
    }

    /*${user.roles?seq_contains(role)?string("checked", "")} как сделать рабочим checked="checked"*/
//    @Test
//    @WithUserDetails("ADMIN")
//    void adminUserCheckTest() throws Exception {
//        this.mockMvc.perform(get("/admin/11"))
//                .andDo(print())
//                .andExpect(authenticated())
//                .andExpect(xpath("//*[@id='username']").string("user-10"));
//    }

    @Test
    @WithUserDetails("ADMIN")
    void userUpdateRoleTest() throws Exception {
        this.mockMvc.perform(get("/admin"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='user-data']/tbody/tr[@data-id=11]/th")
                        .string("11"))
                .andExpect(xpath("//*[@id='user-data']/tbody/tr[@data-id=11]/td[1]/a")
                        .string("user-10"))
                .andExpect(xpath("//*[@id='user-data']/tbody/tr[@data-id=11]/td[4]")
                        .string("USER "));

        this.mockMvc.perform(
                post("/admin")
                        .param("ADMIN", "checked")
                        .param("USER", "checked")
                        .param("userId", "11")
                        .with(csrf())
        )
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin"));

        this.mockMvc.perform(get("/admin"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='user-data']/tbody/tr[@data-id=11]/th")
                        .string("11"))
                .andExpect(xpath("//*[@id='user-data']/tbody/tr[@data-id=11]/td[1]/a")
                        .string("user-10"))
                .andExpect(xpath("//*[@id='user-data']/tbody/tr[@data-id=11]/td[4]")
                        .string("ADMIN USER "));
    }
}
