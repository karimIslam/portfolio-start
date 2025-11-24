package com.example.portfolio.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.CrossOrigin
import com.example.portfolio.repository.ProjectRepository
import com.example.portfolio.repository.SkillRepository
import com.example.portfolio.entity.ProjectEntity
import com.example.portfolio.entity.SkillEntity
import com.example.portfolio.core.Project
import com.example.portfolio.core.Profile

@RestController
@CrossOrigin(origins = ["*"])
class PortfolioController(
    val projectRepository: ProjectRepository,
    val skillRepository: SkillRepository
) {

    @GetMapping("/api/profile")
    fun getProfile(): Profile {
        val projects = projectRepository.findAll().map { 
            Project(
                title = it.title, 
                description = it.description, 
                techStack = it.techStack, 
                link = it.link,
                company = it.company,
                role = it.role,
                duration = it.duration
            )
        }
        
        val skills = skillRepository.findAll().groupBy { it.category }
        
        return Profile(
            name = "Karim Ibrahim",
            title = "Senior Backend Engineer",
            summary = "Senior Backend Engineer with 6 years of experience in Kotlin, Java, Spring Boot, and distributed systems. Passionate about building scalable, automated infrastructure with AWS, Terraform, and CI/CD systems.",
            projects = projects,
            skills = skills
        )
    }
    
    @GetMapping("/api/init")
    fun initData(): String {
        if (projectRepository.count() == 0L) {
            projectRepository.saveAll(listOf(
                ProjectEntity(
                    title = "ChargeBig - Mahle GMBH",
                    company = "Brightskies",
                    role = "Backend Engineer | DevOps & Cloud Engineer",
                    duration = "Jan 2022 - Present",
                    description = "Played a key role in evolving a highly available EV charging backend, supporting thousands of concurrent sessions under high traffic. Contributed to the redesign of an Axon Framework legacy system using DDD with Spring Boot (Kotlin). Built scalable, reusable AWS infrastructure with Terraform and Docker.",
                    techStack = listOf("Kotlin", "Spring Boot", "MySQL", "Axon", "AWS", "Terraform", "Docker"),
                    link = "https://chargebig.com"
                ),
                ProjectEntity(
                    title = "Kazyon",
                    company = "Brightskies",
                    role = "Backend Engineer | Android | DevOps Engineer",
                    duration = "Jan 2020 - Jan 2022",
                    description = "Developed and integrated Kazyon's e-commerce and delivery management system. Developed backend services using Spring Boot (Java), PostgreSQL, Apache NiFi, RabbitMQ. Automated infrastructure deployment using Jenkins and Docker.",
                    techStack = listOf("Java", "Spring Boot", "PostgreSQL", "Apache NiFi", "RabbitMQ", "Jenkins", "Docker")
                ),
                ProjectEntity(
                    title = "Dematic - Kion Group",
                    company = "Brightskies",
                    role = "Full Stack Engineer",
                    duration = "Jul 2019 - Jan 2020",
                    description = "Implemented a warehousing management system for SMYK. Built backend services in Java (Spring EE) and PostgreSQL. Optimized GWT-based interfaces.",
                    techStack = listOf("Java", "Spring EE", "PostgreSQL", "RabbitMQ", "GWT")
                )
            ))
        }
        
        if (skillRepository.count() == 0L) {
            skillRepository.saveAll(listOf(
                SkillEntity(name = "Java", category = "Languages"),
                SkillEntity(name = "Kotlin", category = "Languages"),
                SkillEntity(name = "Python", category = "Languages"),
                SkillEntity(name = "Go", category = "Languages"),
                SkillEntity(name = "JavaScript (Node.js)", category = "Languages"),
                
                SkillEntity(name = "Spring Boot", category = "Frameworks"),
                SkillEntity(name = "Axon Framework", category = "Frameworks"),
                SkillEntity(name = "TensorFlow", category = "Frameworks"),
                
                SkillEntity(name = "Terraform", category = "CI/CD"),
                SkillEntity(name = "Docker", category = "CI/CD"),
                SkillEntity(name = "Jenkins", category = "CI/CD"),
                SkillEntity(name = "GitHub Workflows", category = "CI/CD"),
                
                SkillEntity(name = "AWS", category = "Cloud"),
                
                SkillEntity(name = "MySQL", category = "Databases"),
                SkillEntity(name = "PostgreSQL", category = "Databases"),
                SkillEntity(name = "MongoDB", category = "Databases"),
                SkillEntity(name = "Redis", category = "Middleware"),
                SkillEntity(name = "RabbitMQ", category = "Middleware"),
                SkillEntity(name = "Apache NiFi", category = "Middleware")
            ))
        }
        
        return "Data initialized"
    }
}
