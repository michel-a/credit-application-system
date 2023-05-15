package com.malves.creditapplicationsystem.repository

import com.malves.creditapplicationsystem.entity.Customer
import org.springframework.data.jpa.repository.JpaRepository

interface CustomerRepository: JpaRepository<Customer, Long> {
}