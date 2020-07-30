# traveler-service
Micro serviço responsável em gerenciar cadastros de viajantes

### Especificações técnicas
Micro serviço desenvolvido utilizando as técnicas de TDD, clean code, testes unitários, testes de integração.
Tecnologias utilizadas:
* JAVA 1.8
* SpringBoot 2.3.1.RELEASE
* JPA
* Hibernate
* Lombok
* API REST
* Swagger 2.0
* Maven
* MySql
* GIT Flow
* JUnit 5
* Mockito
* WebMvcTest

##### Estrutura de pacotes
* **config**: contém classes que implementam configurações do projeto
* **controller**: contém classes dos endpoints
* **controller.exception**: contém classes que tratam as excessões dos endpoints
* **domain**: contém classes que representam o modelo de dados
* **dto**: contém classes que representam os dados retornados pelos endpoints
* **model**: contém classes que são utilizadas como modelo padronizado
* **repository**: contém interfaces que realizam acesso à base de dados
* **service**: contém interfaces com assinaturas dos métodos de regras de negócios
* **service.impl**: contém classes com implentações dos métodos de negócio
* **service.exception**: contém classes que implementam as excessões verificadas da aplicação


##### Documentação API
* {context}/traveler-service/swagger-ui.html
* {context}/traveler-service/v2/api-docs




