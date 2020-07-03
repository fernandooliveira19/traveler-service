# traveler-service
Micro serviço responsável em gerenciar cadastros de viajantes

### Especificações técnicas
Micro serviço desenvolvido utilizando as técnicas de TDD, clean code, testes unitários e integração.
Tecnologias utilizadas:
* JAVA 1.8
* SpringBoot 2.3.1.RELEASE
* JUnit 5
* JPA
* Hibernate
* Lombok
* API REST
* Swagger 2.0
* Maven
* MySql
* GIT Flow

##### Estrutura de pacotes
* **resource**: contém classes dos endpoints
* **dto**: contém classes que representam os dados retornados pelos endpoints
* **service**: contém interfaces com assinaturas dos métodos de regras de negócios
* **service.impl**: contém classes com implentações dos métodos de negócio
* **repository**: contém interfaces que realizam acesso à base de dados
* **domain**: contém classes que representam o modelo de dados
* **exception**: contém classes que implementam as excessões verificadas da aplicação



