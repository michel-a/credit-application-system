package com.malves.creditapplicationsystem.dto

import com.malves.creditapplicationsystem.entity.Credit
import java.math.BigDecimal
import java.util.UUID

data class CreditViewListDto(
    var creditCode: UUID,
    var creditValue: BigDecimal,
    var numberOfInstallments: Int
) {
    constructor(credit: Credit): this(
        creditCode = credit.creditCode,
        creditValue =credit.creditValue,
        numberOfInstallments = credit.numberOfInstallments
    )
}
