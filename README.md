# API REST de Gerenciamento de Matrículas

API REST desenvolvida com Java e Spring Boot para gerenciamento de discentes e suas respectivas matrículas.

A aplicação disponibiliza operações CRUD para discentes, permitindo cadastrar, consultar, atualizar e excluir registros, além de associar matrículas aos discentes.

## Funcionalidades

- Cadastro de discentes
- Listagem de discentes
- Consulta de discente por ID
- Atualização de discente
- Exclusão de discente
- Associação de matrículas ao discente
- Validação dos dados de entrada
- Tratamento de exceções
- Documentação da API com Swagger/OpenAPI
- Monitoramento com Spring Boot Actuator
- Persistência com Spring Data JPA
- Banco H2 para desenvolvimento
- Execução em container Docker

## Tecnologias

- Java 21
- Spring Boot 4.0.2
- Spring Web MVC
- Spring Data JPA
- Spring Validation
- H2 Database
- Spring Boot Actuator
- Springdoc OpenAPI 2.8.8
- Lombok
- Maven
- Docker

## Requisitos

- Java 21+
- Maven
- Docker (opcional)

## Executando o projeto

Clone o repositório:

```bash
git clone https://github.com/bispobr/java-spring-matricula.git
cd java-spring-matricula
```

Execute a aplicação com Maven:

```bash
mvn spring-boot:run
```

A API estará disponível em:

```text
http://localhost:8080
```

## Swagger / OpenAPI

Com a aplicação em execução, acesse a documentação interativa:

```text
http://localhost:8080/swagger-ui/index.html
```

## Actuator

Endpoint de saúde da aplicação:

```text
http://localhost:8080/actuator/health
```

## API Endpoints

### Cadastrar discente

```http
POST /discente
Content-Type: application/json
```

Exemplo:

```json
{
  "nome": "João da Silva",
  "dataNascimento": "2000-02-10",
  "telefone": "61999999999",
  "matriculas": [
    {
      "CodigoMatricula": "MAT001",
      "nomeCurso": "Sistemas de Informação",
      "dataInicio": "2026-02-10"
    }
  ]
}
```

### Listar discentes

```http
GET /discente/discentes
```

Retorna todos os discentes cadastrados.

### Buscar discente por ID

```http
GET /discente/{id}
```

Retorna o discente correspondente ao identificador informado.

### Atualizar discente

```http
PUT /discente/{id}
Content-Type: application/json
```

Exemplo:

```json
{
  "nome": "João da Silva",
  "dataNascimento": "2000-02-10",
  "telefone": "61988888888",
  "matriculas": [
    {
      "CodigoMatricula": "MAT001",
      "nomeCurso": "Sistemas de Informação",
      "dataInicio": "2026-02-10"
    }
  ]
}
```

### Excluir discente

```http
DELETE /discente/{id}
```

Remove o discente correspondente ao identificador informado.

## Modelo simplificado

```text
Discente
├── id
├── nome
├── dataNascimento
├── telefone
└── matriculas
     ├── CodigoMatricula
     ├── nomeCurso
     └── dataInicio
```

## Fluxo da aplicação

```text
Cliente
   │
   ▼
API REST
   │
   ▼
Validação
   │
   ▼
Camada de serviço
   │
   ▼
Spring Data JPA
   │
   ▼
H2 Database
```

## Testes

Execute os testes automatizados com:

```bash
mvn test
```

## Docker

Gere o pacote da aplicação:

```bash
mvn clean package
```

Gere a imagem Docker:

```bash
docker build -t matricula .
```

Execute o container:

```bash
docker run -p 8080:8080 matricula
```

## Status

Projeto desenvolvido para praticar a construção de APIs REST com Spring Boot, operações CRUD, relacionamento entre entidades, validação, persistência com JPA, documentação OpenAPI, monitoramento e execução em containers.
