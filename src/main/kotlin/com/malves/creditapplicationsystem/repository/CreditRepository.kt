package com.malves.creditapplicationsystem.repository

import com.malves.creditapplicationsystem.entity.Credit
import org.springframework.data.jpa.repository.JpaRepository

interface CreditRepository: JpaRepository<Credit, Long> {
}