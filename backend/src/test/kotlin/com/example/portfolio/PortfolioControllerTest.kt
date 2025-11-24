package com.example.portfolio.controller

import com.example.portfolio.service.PortfolioService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath

@WebMvcTest(PortfolioController::class)
class PortfolioControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var portfolioService: PortfolioService

    @Test
    fun `should return profile data`() {
        mockMvc.perform(get("/api/profile"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.name").exists())
            .andExpect(jsonPath("$.title").exists())
    }

    @Test
    fun `should initialize data`() {
        mockMvc.perform(get("/api/init"))
            .andExpect(status().isOk)
    }
}
