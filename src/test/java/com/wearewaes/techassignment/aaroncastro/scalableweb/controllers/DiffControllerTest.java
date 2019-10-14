package com.wearewaes.techassignment.aaroncastro.scalableweb.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DiffControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void insertLeftWithEmptyBodyTest() throws Exception {
        final String body = " ";

        mockMvc.perform(
                    post("/v1/diff/{id}/left", "test-01")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.TEXT_PLAIN)
                    .content(body)
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void insertLeftWithNotBase64BodyTest() throws Exception {
        final String body = "%^$";

        mockMvc.perform(
                post("/v1/diff/{id}/left", "test-01")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(body)
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void insertLeftWithValidJSONBodyTest() throws Exception {
        final String body = "eyJtZXNzYWdlIjogIkhpIGZyb20gQ29zdGEgUmljYSEifQ==";

        mockMvc.perform(
                post("/v1/diff/{id}/left", "test-01")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(body)
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated());
    }

    @Test
    public void insertLeftWithDuplicatedJSONBodyTest() throws Exception {
        final String body = "eyJtZXNzYWdlIjogIkhpIGZyb20gQ29zdGEgUmljYSEifQ==";

        mockMvc.perform(
                post("/v1/diff/{id}/left", "duplicated-01")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(body)
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated());

        mockMvc.perform(
                post("/v1/diff/{id}/left", "duplicated-01")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(body)
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isConflict());
    }

    @Test
    public void insertRightWithEmptyBodyTest() throws Exception {
        final String body = " ";

        mockMvc.perform(
                post("/v1/diff/{id}/right", "test-01")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(body)
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void insertRightWithNotBase64BodyTest() throws Exception {
        final String body = "*&^%";

        mockMvc.perform(
                post("/v1/diff/{id}/right", "test-01")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(body)
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void insertRightWithValidJSONBodyTest() throws Exception {
        final String body = "eyJtZXNzYWdlIjogIkhpIGZyb20gQ29zdGEgUmljYSEifQ==";

        mockMvc.perform(
                post("/v1/diff/{id}/right", "test-01")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(body)
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated());
    }

    @Test
    public void insertRightWithDuplicatedJSONBodyTest() throws Exception {
        final String body = "eyJtZXNzYWdlIjogIkhpIGZyb20gQ29zdGEgUmljYSEifQ==";

        mockMvc.perform(
                post("/v1/diff/{id}/right", "duplicated-01")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(body)
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated());

        mockMvc.perform(
                post("/v1/diff/{id}/right", "duplicated-01")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(body)
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isConflict());
    }
}
