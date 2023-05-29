package com.malves.creditapplicationsystem.controller

import com.malves.creditapplicationsystem.dto.request.CreditDto
import com.malves.creditapplicationsystem.dto.response.CreditViewDto
import com.malves.creditapplicationsystem.dto.response.CreditViewListDto
import com.malves.creditapplicationsystem.entity.Credit
import com.malves.creditapplicationsystem.service.impl.CreditService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID
import java.util.stream.Collectors

@RestController
@RequestMapping("/api/credits")
class CreditResource(private val creditService: CreditService) {

    @Operation(summary = "Displays all Credits of a Customer.", description = "Displays all Credits of a Customer through the informed id.")
    @GetMapping
    fun findAllByCustomerId(@Parameter(description = "Customer id to search.", required = true)
                            @RequestParam(value = "customerId") customerId: Long): ResponseEntity<List<CreditViewListDto>> {
        val creditViewList: List<CreditViewListDto> = this.creditService.findAllByCustomer(customerId).stream()
            .map { credit: Credit -> CreditViewListDto(credit) }.collect(Collectors.toList()
        )
        return ResponseEntity.status(HttpStatus.OK).body(creditViewList)
    }

    @Operation(summary = "Display a Credit.", description = "Displays credit terms through the entered credit code and Customer id.")
    @GetMapping("/{creditCode}")
    fun findByCreditCode(@Parameter(description = "Customer id to search.", required = true)
                         @RequestParam(value = "customerId") customerId: Long,
                         @PathVariable creditCode: UUID): ResponseEntity<CreditViewDto> {
        val credit: Credit = this.creditService.findByCreditCode(customerId, creditCode)
        return ResponseEntity.status(HttpStatus.OK).body(CreditViewDto(credit))
    }

    @Operation(summary = "Save a credit.", description = "Adds a new Credit.")
    @PostMapping
    fun saveCredit(@RequestBody @Valid creditDto: CreditDto): ResponseEntity<String> {
        val credit: Credit = this.creditService.save(creditDto.toEntity())
        return ResponseEntity.status(HttpStatus.CREATED)
            .body( "Credit: ${credit.creditCode} - Customer: ${credit.customer?.email} saved!" )
    }
}