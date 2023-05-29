# credit-application-system

## API para Sistema de Avaliação de Créditos

Essa API foi desenvolvida seguindo as aulas do Bootcamp TQI Kotlin - Backend Developer da DIO.
O projeto foi criado com a linguagem de programação Kotlin 1.7.22, JDK 17,  Spring Boot 3.0.6, Banco de Dados em memória H2, Gradle 8.1.1 e Flyway 9.5.1  

### Cliente (Customer):

* #### Cadastrar:
- - Request: firstName, lastName, cpf, income, email, password, zipCode e street
- - Response: String
* #### Editar cadastro:
- - Request: id, firstName, lastName, income, zipCode, street
- - Response: firstName, lastName, income, cpf, email, income, zipCode, street
* #### Visualizar perfil:
- - Request: id
- - Response: firstName, lastName, income, cpf, email, income, zipCode, street
* #### Deletar cadastro:
- - Request: id
- - Response: sem retorno

### Solicitação de Empréstimo (Credit):

* #### Cadastrar:
- - Request: creditValue, dayFirstOfInstallment, numberOfInstallments e customerId
- - Response: String
* #### Listar todas as solicitações de emprestimo de um cliente:
- - Request: customerId
- - Response: creditCode, creditValue, numberOfInstallment
* #### Visualizar um emprestimo:
- - Request: customerId e creditCode
- - Response: creditCode, creditValue, numberOfInstallment, status, emailCustomer e incomeCustomer

### Instruções de uso

No terminal/console:

1. Faça um clone do projeto na sua máquina: `git clone https://github.com/michel-a/credit-application-system.git`
2. Entre na pasta raiz do projeto: `cd` 
3. Execute o comando: `gradlew bootrun`


Endereço para acessar a página do swaagger:

**http://localhost:8090/swagger-ui/index.html**