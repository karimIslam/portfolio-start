package com.example.portfolio

import jakarta.persistence.*

@Entity
@Table(name = "skills")
data class SkillEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val name: String,
    val category: String, // e.g., "Language", "Framework", "Cloud"
    val proficiency: Int? = null // 1-5 or 1-100
)
