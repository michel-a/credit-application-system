package com.malves.creditapplicationsystem.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.malves.creditapplicationsystem.dto.request.CustomerDto
import com.malves.creditapplicationsystem.dto.request.CustomerUpdateDto
import com.malves.creditapplicationsystem.entity.Address
import com.malves.creditapplicationsystem.entity.Customer
import com.malves.creditapplicationsystem.repository.CustomerRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.math.BigDecimal
import java.util.Random

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@ContextConfiguration
class CustomerResourceTest {

    @Autowired private lateinit var customerRepository: CustomerRepository
    @Autowired private lateinit var mockMvc: MockMvc
    @Autowired private lateinit var objectMapper: ObjectMapper

    companion object {
        const val URL: String = "/api/customers"
    }

    @BeforeEach fun setup() = customerRepository.deleteAll()

    @AfterEach fun tearDown() = customerRepository.deleteAll()

    @Test
    fun `should create a customer and return 201 status`() {
        // given
        val customerDto: CustomerDto = buildCustomerDto()
        val valueAsString: String = objectMapper.writeValueAsString(customerDto)

        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.post(URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(valueAsString))
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Carla"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Santos"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value("96690364020"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("carla@email.com"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.income").value("1000.0"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.zipCode").value("01212-010"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.street").value("Rua das Oliveiras"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
            .andDo(MockMvcResultHandlers.print()
        )
    }

    @Test
    fun `should not save a customer with same CPF and return 409 status`() {
        // given
        customerRepository.save(buildCustomerDto().toEntity())
        val customerDto: CustomerDto = buildCustomerDto()
        val valueAsString: String = objectMapper.writeValueAsString(customerDto)

        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.post(URL)
            .contentType(MediaType.APPLICATION_JSON).content(valueAsString))
            .andExpect(MockMvcResultMatchers.status().isConflict)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Conflict! Consult the documentation"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(409))
            .andExpect(MockMvcResultMatchers.jsonPath("$.exception")
                .value("class org.springframework.dao.DataIntegrityViolationException")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
            .andDo(MockMvcResultHandlers.print()
        )
    }

    @Test
    fun `should not save a customer with firstName empty and return 400 status`() {
        // given
        val customerDto: CustomerDto = buildCustomerDto(firstName = "")
        val valueAsString: String = objectMapper.writeValueAsString(customerDto)

        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.post(URL)
            .content(valueAsString)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(MockMvcResultMatchers.status().isBadRequest)
        .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Bad Request! Consult the documentation"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
        .andExpect(MockMvcResultMatchers.jsonPath("$.exception")
            .value("class org.springframework.web.bind.MethodArgumentNotValidException")
        )
        .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
            .andDo(MockMvcResultHandlers.print()
        )
    }

    @Test
    fun `should find customer by id and return 200 status`() {
        // given
        val customer: Customer = customerRepository.save(buildCustomerDto().toEntity())

        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.get("$URL/${customer.id}")
            .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk)
        .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Carla"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Santos"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value("96690364020"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("carla@email.com"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.income").value("1000.0"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.zipCode").value("01212-010"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.street").value("Rua das Oliveiras"))
//        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
        .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `should not find customer with invalid id and return 400 ssatus`() {
        // given
        val invalidId: Long = 2L

        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.get("$URL/$invalidId")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(MockMvcResultMatchers.status().isBadRequest)
        .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Bad Request! Consult the documentation"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
        .andExpect(MockMvcResultMatchers.jsonPath("$.exception")
            .value("class com.malves.creditapplicationsystem.exception.BusinessException")
        )
        .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
        .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `should delete customer by id and return 204 status`() {
        // given
        val customer: Customer = customerRepository.save(buildCustomerDto().toEntity())

        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.delete("$URL/${customer.id}")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNoContent)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `should not delete customer by id and return 400 status`() {
        // given
        val invalidId: Long = Random().nextLong()

        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.delete("$URL/${invalidId}")
            .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Bad Request! Consult the documentation"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
            .andExpect(MockMvcResultMatchers.jsonPath("$.exception")
                .value("class com.malves.creditapplicationsystem.exception.BusinessException")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `should update a customer and return 200 status`() {
        // given
        val customer: Customer = customerRepository.save(buildCustomerDto().toEntity())
        val customerUpdateDto: CustomerUpdateDto = builderCustomerUpdateDto()
        val valueAsString: String = objectMapper.writeValueAsString(customerUpdateDto)

        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.patch("$URL?customerId=${customer.id}")
            .contentType(MediaType.APPLICATION_JSON)
            .content(valueAsString))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("CarlaUpdate"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("SantosUpdate"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value("96690364020"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("carla@email.com"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.income").value("10000.0"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.zipCode").value("03232-030"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.street").value("Rua das Oliveiras Update"))
//            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `should not update a customer invalid id and return 400 status`() {
        // given
        val invalidId: Long = Random().nextLong()
        val customerUpdateDto: CustomerUpdateDto = builderCustomerUpdateDto()
        val valueAsString: String = objectMapper.writeValueAsString(customerUpdateDto)

        // when
        // then
        mockMvc.perform(
            MockMvcRequestBuilders.patch("$URL?customerId=$invalidId")
                .contentType(MediaType.APPLICATION_JSON)
                .content(valueAsString)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Bad Request! Consult the documentation"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
            .andExpect(MockMvcResultMatchers.jsonPath("$.exception")
                .value("class com.malves.creditapplicationsystem.exception.BusinessException")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
            .andDo(MockMvcResultHandlers.print())
    }

    private fun buildCustomerDto(
        firstName: String = "Carla",
        lastName: String = "Santos",
        cpf: String = "96690364020",
        email: String = "carla@email.com",
        password: String = "1234",
        zipCode: String = "01212-010",
        street: String = "Rua das Oliveiras",
        income: BigDecimal = BigDecimal.valueOf(1000.0)
    ) = CustomerDto(
        firstName = firstName,
        lastName = lastName,
        cpf = cpf,
        email = email,
        password = password,
        zipCode = zipCode,
        street = street,
        income = income
    )

    private fun builderCustomerUpdateDto(
        firstName: String = "CarlaUpdate",
        lastName: String = "SantosUpdate",
        income: BigDecimal = BigDecimal.valueOf(10000.0),
        zipCode: String = "03232-030",
        street: String = "Rua das Oliveiras Update"
    ) = CustomerUpdateDto(
        firstName = firstName,
        lastName = lastName,
        income = income,
        zipCode = zipCode,
        street = street
    )
}