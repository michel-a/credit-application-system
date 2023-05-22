package com.malves.creditapplicationsystem.dto

import com.malves.creditapplicationsystem.entity.Credit
import com.malves.creditapplicationsystem.enumeration.Status
import java.math.BigDecimal
import java.util.UUID

data class CreditViewDto(
    val creditCode: UUID,
    val creditValue: BigDecimal,
    val numberOfInstallment: Int,
    val status: Status,
    val emailCustomer: String?,
    val incomeCustomer: BigDecimal?
) {
    constructor(credit: Credit): this (
        creditCode = credit.creditCode,
        creditValue = credit.creditValue,
        numberOfInstallment = credit.numberOfInstallments,
        status = credit.status,
        emailCustomer = credit.customer?.email,
        incomeCustomer = credit.customer?.income
    )
}