package com.example.portfolio.repository

import com.example.portfolio.entity.SkillEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SkillRepository : JpaRepository<SkillEntity, Long>
