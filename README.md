# API REST para gerenciamento de matrículas 
## Descrição

Esta aplicação é uma API REST, desenvolvida para o cadastro de discentes e as suas respectivas matrículas por meio de campos pre definidos. Ela oferece suporte às operações básicas de um CRUD, incluindo:
- **Listagem de discentes cadastrados, com possibilidade de busca por ID, listagem geral** 
- **Cadastro** Dos discentes com a sua matrícula.
- **Atualização dos dados cadastrais do discente ** 
- **Exclusão de um discente e de seus dados associados** 

## Tecnologias Utilizadas

- **Java + Spring Boot** – Framework principal para o desenvolvimento da aplicação
- **Lombok (@Slf4j)** – Facilita a geração e o gerenciamento de logs
- **Tratamento de Exceções** - @RestControllerAdvice Centraliza o tratamento de erros da aplicação
- **Swagger** – Documentação interativa da API
- **Spring Boot Actuator** – Monitoramento e verificação da saúde da aplicação
- **H2 database** – Banco de dados relacional em memória
- **Docker** – Criação, empacotamento e execução da aplicação em contêineres.


## Requisitos

- Java 25
- Maven


## Executando o Projeto

1. Clone o repositório:

```bash
git https://github.com/bispobr/java-spring-matricula.git
```

## Como usar

1. Inicie a aplicação
2. A API está acessível através do endereço http://localhost:8080
3. A documentação da API está acessível através do Link http://localhost:8080/swagger-ui/index.html#/
4. O endpoint de saúde e métricas do Actuator está acessível através do Link http://localhost:8080/actuator/health

## Como Rodar em um Container (Opcional)

1. Construa o projeto

```bash
mvn clean package 
```

2. Gere a Imagem Docker, com o Docker  instalado execute:


```bash
docker build -t matricula . 
```

3. Execute o Container

```bash
docker run -p 8080:8080 matricula
```

## API Endpoints
API contem os seguintes endpoints:

```http request
POST /discente - Cadastra um novo discente
Content-Type: application/json

{
  "nome": "string",
  "dataNascimento": "3029-02-10",
  "telefone": "string",
  "matriculas": [
    {
      "CodigoMatricula": "string",
      "nomeCurso": "string",
      "dataInicio": "3029-02-10"
    }
  ]
}
```

```http request
GET /discente/discentes -  Lista todos os discentes
```

```http request
GET /discente/{id} -  Lista discentes por id
```

```http request
PUT /discente/{id} - Atualizar um discente existente
Content-Type: application/json

{
  "nome": "string",
  "dataNascimento": "3028-08-15",
  "telefone": "string",
  "matriculas": [
    {
      "CodigoMatricula": "string",
      "nomeCurso": "string",
      "dataInicio": "3028-11-11"
    }
  ]
}
```
```http request
DELETE /discente/{id} - Remover discente de id especificado.
```



