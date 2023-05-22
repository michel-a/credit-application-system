package com.malves.creditapplicationsystem.dto.request

import com.malves.creditapplicationsystem.entity.Address
import com.malves.creditapplicationsystem.entity.Customer
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.hibernate.validator.constraints.br.CPF
import java.math.BigDecimal

data class CustomerDto(
    @field:NotEmpty(message = "Invalid input")
    @field:Size(min = 3, max = 50, message = "First name must be between 3 and 50 characters")
    val firstName: String,

    @field:NotEmpty(message = "Invalid input")
    @field:Size(min = 3, max = 50, message = "Last name must be between 3 and 50 characters")
    val lastName: String,

    @field:NotEmpty(message = "Invalid input")
    @field:CPF(message = "Invalid CPF") val cpf: String,

    @field:NotNull(message = "Invalid input")val income: BigDecimal,

    @field:NotEmpty(message = "Invalid input")
    @field:Email(message = "Invalid email") val email: String,

    @field:NotEmpty(message = "Invalid input") val password: String,
    @field:NotEmpty(message = "Invalid input") val zipCode: String,
    @field:NotEmpty(message = "Invalid input") val street: String
) {
    fun toEntity(): Customer = Customer (
        firstName = this.firstName,
        lastName = this.lastName,
        cpf = this.cpf,
        income = this.income,
        email = this.email,
        password = this.password,
        address = Address(
            zipCode = this.zipCode,
            street = this.street
        )
    )
}
