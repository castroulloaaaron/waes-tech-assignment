package com.wearewaes.techassignment.aaroncastro.scalableweb.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.wearewaes.techassignment.aaroncastro.scalableweb.models.persistence.DiffResultContainer.ResultTypes.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
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
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
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
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
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
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        mockMvc.perform(
                post("/v1/diff/{id}/left", "duplicated-01")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(body)
        )
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
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
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
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
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
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
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
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
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        mockMvc.perform(
                post("/v1/diff/{id}/right", "duplicated-01")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(body)
        )
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void askResultWithoutLeftSidePreviouslyPersistedTest() throws Exception {
        final String body = "eyJtZXNzYWdlIjogIkhpIGZyb20gQ29zdGEgUmljYSEifQ==";

        mockMvc.perform(
                post("/v1/diff/{id}/right", "missing-left")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(body)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        mockMvc.perform(
                get("/v1/diff/{id}", "missing-left")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void askResultWithoutRightSidePreviouslyPersistedTest() throws Exception {
        final String body = "eyJtZXNzYWdlIjogIkhpIGZyb20gQ29zdGEgUmljYSEifQ==";

        mockMvc.perform(
                post("/v1/diff/{id}/left", "missing-right")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(body)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        mockMvc.perform(
                get("/v1/diff/{id}", "missing-right")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void askResultWithoutAnySidePreviouslyPersistedTest() throws Exception {
        mockMvc.perform(
                get("/v1/diff/{id}", "missing-both-sides")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void askResultWithEqualsSidesTest() throws Exception {
        final String body = "eyJtZXNzYWdlIjogIkhpIGZyb20gQ29zdGEgUmljYSEifQ==";

        mockMvc.perform(
                post("/v1/diff/{id}/left", "equal-sides")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(body)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        mockMvc.perform(
                post("/v1/diff/{id}/right", "equal-sides")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(body)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        mockMvc.perform(
                get("/v1/diff/{id}", "equal-sides")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.resultStatus").value(EQUALS.toString()))
                .andExpect(jsonPath("$.itemResultsContainer").isArray())
                .andExpect(jsonPath("$.itemResultsContainer").isEmpty());
    }

    @Test
    public void askResultWithNonEqualSizeTest() throws Exception {
        final String leftBody = "eyJtZXNzYWdlIjogIkhpIGZyb20gQ29zdGEgUmljYSEiLCAiaW50ZWdlciI6IDEsICJhcnJheSI6IFsxLDIsM10sICJvYmplY3QiOiB7ICJhcnJheSI6IFsgeyJib29sZWFuIjogdHJ1ZX0sIHsibnVsbCI6IG51bGx9XX19";
        final String rightBody = "eyJtZXNzYWdlIjogIkhpIGZyb20gQ29zdGEgUmljYSEifQ==";

        mockMvc.perform(
                post("/v1/diff/{id}/left", "non-equal-sides")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(leftBody)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        mockMvc.perform(
                post("/v1/diff/{id}/right", "non-equal-sides")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(rightBody)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        mockMvc.perform(
                get("/v1/diff/{id}", "non-equal-sides")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.resultStatus").value(NOT_EQUAL.toString()))
                .andExpect(jsonPath("$.itemResultsContainer").isArray())
                .andExpect(jsonPath("$.itemResultsContainer").isEmpty());
    }

    @Test
    public void askResultWithEqualSizeDifferentContentTest() throws Exception {
        final String leftBody  = "eyJtZXNzYWdlIjogIkhpIGZyb20gQ29zdGEgUmljYSEiLCAiaW50ZWdlciI6IDEsICJhcnJheSI6IFsxLjksMi44OSwzLjc2NV0sICJvYmplY3QiOiB7ICJhcnJheSI6IFsgeyJib29sZWFuIjogdHJ1ZX0sIHsibnVsbCI6IG51bGx9XX19";
        final String rightBody = "eyJtZXNzYWdlIjogIkhpIE5ldGhlcmxhbmRzIDpEISEiLCAiaW50ZWdlciI6IDIsICJhcnJheSI6IFsxLjksMi44OSwzLjEyM10sICJvYmplY3QiOiB7ICJhcnJheSI6IFsgeyJib29sZWFuIjogdHJ1ZX0sIHsibnVsbCI6IG51bGx9XX19";

        mockMvc.perform(
                post("/v1/diff/{id}/left", "equal-sides-different-content")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(leftBody)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        mockMvc.perform(
                post("/v1/diff/{id}/right", "equal-sides-different-content")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(rightBody)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        mockMvc.perform(
                get("/v1/diff/{id}", "equal-sides-different-content")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.resultStatus").value(EQUAL_SIZE_NOT_EQUAL_CONTENT.toString()))
                .andExpect(jsonPath("$.itemResultsContainer").isArray())
                .andExpect(jsonPath("$.itemResultsContainer").isNotEmpty())
                .andExpect(jsonPath("$.itemResultsContainer[0].offset").isNumber())
                .andExpect(jsonPath("$.itemResultsContainer[0].length").isNumber())
                .andExpect(jsonPath("$.itemResultsContainer[1].offset").isNumber())
                .andExpect(jsonPath("$.itemResultsContainer[1].length").isNumber())
                .andExpect(jsonPath("$.itemResultsContainer[2].offset").isNumber())
                .andExpect(jsonPath("$.itemResultsContainer[2].length").isNumber());
    }
}
