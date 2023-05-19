package com.malves.creditapplicationsystem.service.impl

import com.malves.creditapplicationsystem.entity.Customer
import com.malves.creditapplicationsystem.repository.CustomerRepository
import com.malves.creditapplicationsystem.service.ICustomerService
import org.springframework.stereotype.Service
import java.lang.RuntimeException

@Service
class CustomerService(private val customerRepository: CustomerRepository) : ICustomerService {
    override fun save(customer: Customer): Customer = this.customerRepository.save(customer)

    override fun findById(id: Long): Customer = this.customerRepository.findById(id).orElseThrow {
        throw RuntimeException("Id: $id not found.")
    }

    override fun delete(id: Long) = this.customerRepository.deleteById(id)
}