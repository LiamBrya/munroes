package com.com.munroes.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.net.URI;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class MunroControllerTest {

    @Autowired
    private MunroControllerExceptionAdapter exceptionAdapter;
    @Autowired
    private MunroQuerySpecificationMapper queryMapper;
    @Autowired
    private MunroController controller;

    private MockMvc mockMvc;

    @BeforeEach
    public void initialiseMockMvc() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(this.controller)
                .setControllerAdvice(this.exceptionAdapter, this.queryMapper)
                .build();
    }


    @Test
    void basicGetReturnsOk() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get(URI.create("/api/munroes")))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void limitedRequestReturnsLimitedElements() throws Exception {
        final int limit = 10;

        this.mockMvc.perform(MockMvcRequestBuilders
                .get(URI.create("/api/munroes?limit=" + limit)))
                    .andExpect(jsonPath("$", hasSize(limit)));
    }

    @Test
    void munroFormatMatchesExpectations() throws Exception {
        // Relies on the tallest munro in Scotland being Ben Nevis.
        // This is unlikely to change any time soon.
        this.mockMvc.perform(MockMvcRequestBuilders
                .get(URI.create("/api/munroes?limit=1&sort=height-DES")))
                    .andExpect(jsonPath("$[0].*", hasSize(4)))
                    .andExpect(jsonPath("$[0].name", is("Ben Nevis")))
                    .andExpect(jsonPath("$[0].category", is("Munro")))
                    .andExpect(jsonPath("$[0].gridReference", is("NN166712")))
                    .andExpect(jsonPath("$[0].height", is(1344.53D)));
    }

    @Test
    void negativeLimitReturnsBadRequest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get(URI.create("/api/munroes?limit=-1")))
                    .andExpect(status().isBadRequest());
    }

    @Test
    void negativeMinHeightReturnsBadRequest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get(URI.create("/api/munroes?minHeight=-1")))
                    .andExpect(status().isBadRequest());
    }

    @Test
    void negativeMaxHeightReturnsBadRequest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get(URI.create("/api/munroes?maxHeight=-1")))
                    .andExpect(status().isBadRequest());
    }

    @Test
    void illegalMinMaxHeightCombinationReturnsBadRequest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get(URI.create("/api/munroes?minHeight=100&maxHeight=10")))
                    .andExpect(status().isBadRequest());
    }

    @Test
    void unknownSortOrderReturnsBadRequest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get(URI.create("/api/munroes?sort=height-UP")))
                    .andExpect(status().isBadRequest());
    }

    @Test
    void unknownSortPropertyReturnsOk() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get(URI.create("/api/munroes?sort=flavour-ASC")))
                    .andExpect(status().isOk());
    }

    @Test
    void unknownTypeReturnsBadRequest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get(URI.create("/api/munroes?type=BIGHILL")))
                    .andExpect(status().isBadRequest());
    }
}
