package com.malves.creditapplicationsystem.repository

import com.malves.creditapplicationsystem.entity.Credit
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface CreditRepository: JpaRepository<Credit, Long> {

    fun findByCreditCode(creditCode: UUID): Credit?

    @Query(value = "SELECT * FROM credit WHERE customer_id = ?1", nativeQuery = true)
    fun findAllByCustomerId(customerId: Long): List<Credit>
}