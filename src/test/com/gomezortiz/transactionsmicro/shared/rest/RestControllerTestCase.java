package com.gomezortiz.transactionsmicro.shared.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;

@Slf4j
@WebMvcTest
@ActiveProfiles("test")
public abstract class RestControllerTestCase {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    protected void assertResponse(
            String endpoint,
            HttpMethod method,
            String requestBody,
            HttpStatus expectedStatusCode,
            String expectedResponse
    ) throws Exception {
        ResultMatcher response = expectedResponse.isEmpty()
                ? content().string("")
                : content().json(expectedResponse);

        mockMvc.perform(request(method, "/api" + endpoint)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(expectedStatusCode.value()))
                .andExpect(response);
    }

    protected String bodyAsJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}