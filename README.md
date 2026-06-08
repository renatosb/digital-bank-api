# Digital Bank API

Uma API REST simplificada para um banco digital com foco em transferências de fundos, consulta de movimentações financeiras.

## Requisitos

- Java 17 ou superior
- Maven 3.6+
- Git
- Spring Boot 4.0.6 (https://start.spring.io/)
    - Banco em memória H2
    - Flyway para migrations do banco dados

## Requisitos Não Funcionais

- Teste unitário
- Teste de Integração
- Nofiticação via Log
- Swagger

## Como Rodar o Projeto

### 1. Clonar o Repositório

```bash
git clone https://github.com/renatosb/digital-bank-api
cd digital-bank-api
```
### 2. Rodando o projeto

 - Com o Maven instalado(https://maven.apache.org/download.cgi)

````bash
# 1. Limpar e instalar dependências
mvn clean install

# 2. Executar a aplicação
mvn spring-boot:run

# 3. Abrir Swagger
http://localhost:8080/swagger-ui.html
