package com.digital.bank.api.integration;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tools.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class BaseControllerTest {

    protected ResultActions performGet(
            MockMvc mockMvc,
            String endpoint,
            HttpStatus status
    ) {
        try {
            return mockMvc.perform(
                            MockMvcRequestBuilders.get(endpoint)
                                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(status.value()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected ResultActions performPost(
            MockMvc mockMvc,
            String endpoint,
            Object payload,
            HttpStatus status
    ){
        try {
            return mockMvc.perform(post(endpoint)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(new ObjectMapper().writeValueAsString(payload)))
                    .andExpect(status().is(status.value()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void expectedResultSize(int expectedSize, ResultActions result) {
        try {
            result.andExpect(jsonPath("$", hasSize(expectedSize)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
