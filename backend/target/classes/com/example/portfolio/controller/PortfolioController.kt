package com.example.portfolio.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.CrossOrigin
import com.example.portfolio.service.PortfolioService
import com.example.portfolio.core.Profile

@RestController
@CrossOrigin(origins = ["*"])
class PortfolioController(
    val portfolioService: PortfolioService
) {

    @GetMapping("/api/profile")
    fun getProfile(): Profile {
        return portfolioService.getProfile()
    }
    
    @GetMapping("/api/init")
    fun initData(): String {
        return portfolioService.initializeData()
    }
}
