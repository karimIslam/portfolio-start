package com.example.portfolio.core

import com.example.portfolio.entity.SkillEntity

data class Project(
    val title: String,
    val description: String,
    val techStack: List<String>,
    val link: String? = null,
    val company: String? = null,
    val role: String? = null,
    val duration: String? = null
)

data class Profile(
    val name: String,
    val title: String,
    val summary: String,
    val projects: List<Project>,
    val skills: Map<String, List<SkillEntity>>
)
