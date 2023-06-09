package com.malves.creditapplicationsystem.dto.request

import com.malves.creditapplicationsystem.entity.Credit
import com.malves.creditapplicationsystem.entity.Customer
import jakarta.validation.constraints.Future
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import java.math.BigDecimal
import java.time.LocalDate

data class CreditDto(
    @field:NotNull(message = "Invalid input") val credtiValue: BigDecimal,
    @field:Future val dayFirstOfInstallment: LocalDate,
    @field:Min(value = 1)
    @field:Max(value = 48) val numberOfInstallments: Int,
    @field:NotNull(message = "Invalid input") val customerId: Long
) {
    fun toEntity(): Credit = Credit(
        creditValue = this.credtiValue,
        dayFirstInstallment = this.dayFirstOfInstallment,
        numberOfInstallments = this.numberOfInstallments,
        customer = Customer(id = this.customerId)
    )
}
