package com.malves.creditapplicationsystem.controller

import com.malves.creditapplicationsystem.dto.CreditDto
import com.malves.creditapplicationsystem.dto.CreditViewDto
import com.malves.creditapplicationsystem.dto.CreditViewListDto
import com.malves.creditapplicationsystem.entity.Credit
import com.malves.creditapplicationsystem.service.impl.CreditService
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

    @GetMapping
    fun findAllByCustomerId(@RequestParam(value = "customerId") customerId: Long): ResponseEntity<List<CreditViewListDto>> {
        val creditViewList: List<CreditViewListDto> = this.creditService.findAllByCustomer(customerId).stream()
            .map { credit: Credit -> CreditViewListDto(credit) }.collect(Collectors.toList()
        )
        return ResponseEntity.status(HttpStatus.OK).body(creditViewList)
    }

    @GetMapping("/{creditCode}")
    fun findByCreditCode(@RequestParam(value = "customerId") customerId: Long,
                         @PathVariable creditCode: UUID): ResponseEntity<CreditViewDto> {
        val credit: Credit = this.creditService.findByCreditCode(customerId, creditCode)
        return ResponseEntity.status(HttpStatus.OK).body(CreditViewDto(credit))
    }

    @PostMapping
    fun saveCredit(@RequestBody creditDto: CreditDto): ResponseEntity<String> {
        val credit: Credit = this.creditService.save(creditDto.toEntity())
        return ResponseEntity.status(HttpStatus.CREATED)
            .body( "Credit: ${credit.creditCode} - Customer: ${credit.customer?.email} saved!" )
    }
}