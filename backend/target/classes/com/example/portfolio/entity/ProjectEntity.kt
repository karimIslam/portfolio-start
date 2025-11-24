package com.example.portfolio

import jakarta.persistence.*

@Entity
@Table(name = "projects")
data class ProjectEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val title: String,
    
    @Column(length = 2000)
    val description: String,
    
    val company: String? = null,
    val role: String? = null,
    val duration: String? = null,
    
    @ElementCollection
    val techStack: List<String>,
    
    val link: String? = null
)
