# Users API

API REST para cadastro e gerenciamento de usuarios com:

- Spring Boot 4
- Spring Security + JWT
- Spring Data JPA
- PostgreSQL
- Flyway
- OpenFeign
- Swagger / OpenAPI
- Docker Compose

## Funcionalidades

- cadastro de usuario
- login com JWT
- listagem de usuarios
- busca por id
- atualizacao de cadastro
- alteracao de senha
- exclusao de usuario
- exclusao de todos os usuarios
- busca de endereco por CEP usando ViaCEP
- controle de acesso com roles `USER` e `ADMIN`

## Stack

- Java 17
- Spring Boot `4.0.5`
- PostgreSQL `16`
- Maven
- Docker Compose

## Como rodar

### 1. Variaveis de ambiente

Crie um arquivo `.env` na raiz:

```env
POSTGRES_DB=usersapp
POSTGRES_USER=admin
POSTGRES_PASSWORD=admin123
JWT_SECRET=users-api-jwt-secret-key-change-me-2026
```

### 2. Subir com Docker

```bash
docker compose up
```

Isso sobe:

- app
- postgres

### 3. Rodar local pelo IntelliJ

Se quiser rodar fora do Docker, o banco fica exposto em:

```text
localhost:5439
```

A aplicacao usa por padrao:

```text
http://localhost:8081
```

## Banco e migrations

As migrations ficam em:

```text
src/main/resources/db/migration
```

Migrations atuais:

- `V1__create_tb_users.sql`
- `V2__insert_test_users.sql`
- `V3__sync_identity_sequences.sql`
- `V4__add_column_role.sql`

## Swagger

Documentacao:

```text
http://localhost:8081/swagger-ui/index.html
```

JSON OpenAPI:

```text
http://localhost:8081/v3/api-docs
```

O Swagger esta configurado com JWT Bearer. Para testar rotas protegidas:

1. faca login em `/auth/login`
2. copie o token
3. clique em `Authorize`
4. informe:

```text
Bearer SEU_TOKEN
```

## Autenticacao

### Criar usuario

```http
POST /users
```

Exemplo de body:

```json
{
  "name": "Joao Silva",
  "email": "joao.silva@example.com",
  "password": "Senha123",
  "endereco": {
    "numero": "100",
    "complemento": "Apto 12",
    "cep": "01001-000"
  }
}
```

### Login

```http
POST /auth/login
```

Body:

```json
{
  "email": "joao.silva@example.com",
  "password": "Senha123"
}
```

Resposta:

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

Depois disso, envie o token no header:

```text
Authorization: Bearer SEU_TOKEN
```

## Endpoints principais

### Publicos

- `POST /users`
- `POST /auth/login`

### Protegidos

- `GET /users`
- `GET /users/{id}`
- `PUT /users/{id}`
- `PATCH /users/{id}/password`
- `DELETE /users/{id}`
- `DELETE /users/all`

## Roles

- `USER`
- `ADMIN`

Regras atuais:

- `USER` pode alterar a propria conta
- `USER` pode alterar a propria senha
- `USER` pode deletar a propria conta
- `ADMIN` pode deletar todos os usuarios
- `ADMIN` pode acessar listagem protegida

## Validacoes

Exemplos de validacao implementados:

- email obrigatorio e valido
- senha com minimo de caracteres
- senha com letras e numeros
- confirmacao de nova senha
- CEP com formato valido
- email duplicado

## ViaCEP

O endereco e montado a partir do CEP usando a API:

[ViaCEP](https://viacep.com.br)

## Criar admin em desenvolvimento

O endpoint publico sempre cria usuario com role `USER`.

Para promover um usuario para admin em ambiente local:

```sql
UPDATE tb_users
SET role = 'ADMIN'
WHERE email = 'seu-email@email.com';
```

Depois faca login novamente para gerar um token com as permissoes atualizadas.

## Build

Build sem testes:

```bash
mvn clean package -DskipTests
```

## Proximos passos

- padronizar respostas de erro em JSON
- adicionar paginacao
- criar endpoint `/users/me`
- testes unitarios e de integracao
- fluxo de confirmacao de email
