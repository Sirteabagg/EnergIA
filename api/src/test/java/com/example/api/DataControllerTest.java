package com.example.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DataControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetSummary() throws Exception {
        mockMvc.perform(get("/data/summary")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.buildings").isNumber())
                .andExpect(jsonPath("$.sensors").isNumber())
                .andExpect(jsonPath("$.anomalies").isNumber());
    }

    @Test
    public void testGetBuilding() throws Exception {
        mockMvc.perform(get("/buildings/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.building_name").isString())
                .andExpect(jsonPath("$.measures").isArray());
    }
}