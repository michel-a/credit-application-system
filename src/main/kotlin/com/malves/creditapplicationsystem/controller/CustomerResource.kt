package com.malves.creditapplicationsystem.controller

import com.malves.creditapplicationsystem.dto.request.CustomerDto
import com.malves.creditapplicationsystem.dto.request.CustomerUpdateDto
import com.malves.creditapplicationsystem.dto.response.CustomerViewDto
import com.malves.creditapplicationsystem.entity.Customer
import com.malves.creditapplicationsystem.service.impl.CustomerService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/customers")
class CustomerResource(
    private val customerService: CustomerService
) {
    @Operation(summary = "Displays a Customer.", description = "Displays a Customer via an id.")
    @GetMapping("/{id}")
    fun findById(@Parameter(description = "Customer id to display.", required = true)
                 @PathVariable id: Long): ResponseEntity<CustomerViewDto> {
        val customer: Customer = this.customerService.findById(id)
        return ResponseEntity.status(HttpStatus.OK).body(CustomerViewDto(customer))
    }

    @Operation(summary = "Add a new Customer.", description = "Adds a new customer by performing attributes validation.")
    @PostMapping
    fun saveCustomer(@RequestBody @Valid customerDto: CustomerDto): ResponseEntity<CustomerViewDto> {
        val savedCustomer: Customer = this.customerService.save(customerDto.toEntity())
        return ResponseEntity.status(HttpStatus.CREATED).body(CustomerViewDto(savedCustomer))
    }

    @Operation(summary = "Update a Customer.", description = "Updates a Customer's data through the entered id.")
    @PatchMapping
    fun updateCustomer(@Parameter(description = "Customer id to update.", required = true)
                       @RequestParam(value = "customerId") id: Long,
                       @RequestBody @Valid customerUpdateDto: CustomerUpdateDto
    ): ResponseEntity<CustomerViewDto> {
        val customer: Customer = this.customerService.findById(id)
        val customerToUpdate: Customer = customerUpdateDto.toEntity(customer)
        val customerUpdated: Customer = this.customerService.save(customerToUpdate)
        return ResponseEntity.status(HttpStatus.OK).body(CustomerViewDto(customerUpdated))
    }

    @Operation(summary = "Delete a Customer.", description = "Deletes a Customer through the informed id.")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCustomer(@Parameter(description = "Customer id to delete.", required = true)
                       @PathVariable id: Long) = this.customerService.delete(id)

}