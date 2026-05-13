# Users API

![Java](https://img.shields.io/badge/Java-17-red)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.5-brightgreen)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue)
![JWT](https://img.shields.io/badge/Security-JWT-black)
![Docker](https://img.shields.io/badge/Docker-Compose-2496ED)

API REST para cadastro e gerenciamento de usuarios, construida com Spring Boot, Spring Security, JWT, PostgreSQL, Flyway e integracao externa com ViaCEP.

O projeto simula uma base real de autenticacao e gestao de usuarios: cria contas, autentica com token JWT, protege rotas por perfil, valida permissao por dono do recurso, consulta endereco automaticamente por CEP e mantem o banco versionado por migrations.

## Destaques tecnicos

- Autenticacao stateless com JWT Bearer.
- Senhas armazenadas com BCrypt.
- Invalidacao de tokens antigos apos alteracao de senha.
- Controle de acesso com roles `USER` e `ADMIN`.
- Autorizacao por dono do recurso: usuario comum so altera, troca senha ou exclui a propria conta.
- CRUD de usuarios com paginacao e ordenacao.
- Integracao com ViaCEP via OpenFeign para preencher endereco a partir do CEP.
- Validacao de entrada com Bean Validation.
- Tratamento global de excecoes com resposta JSON padronizada.
- Migrations com Flyway para evolucao do schema.
- Documentacao interativa com Swagger/OpenAPI.
- Ambiente reproduzivel com Docker Compose.

## Stack

| Camada | Tecnologias |
| --- | --- |
| Linguagem | Java 17 |
| Framework | Spring Boot 4.0.5 |
| Web/API | Spring Web MVC |
| Seguranca | Spring Security, JWT, BCrypt |
| Persistencia | Spring Data JPA, Hibernate |
| Banco | PostgreSQL 16 |
| Migrations | Flyway |
| Integracao HTTP | Spring Cloud OpenFeign |
| Documentacao | Springdoc OpenAPI / Swagger UI |
| Build | Maven Wrapper |
| Ambiente | Docker Compose |

## Funcionalidades

- Cadastro de usuario com endereco.
- Login com email e senha.
- Emissao de token JWT com expiracao.
- Listagem paginada de usuarios.
- Busca de usuario por ID.
- Atualizacao de cadastro.
- Alteracao de senha com confirmacao.
- Exclusao de usuario.
- Exclusao geral restrita a `ADMIN`.
- Busca de dados de endereco pelo CEP.
- Respostas de erro consistentes para validacao, conflito, acesso negado e recursos inexistentes.

## Regras de acesso

| Recurso | Acesso |
| --- | --- |
| `POST /users` | Publico |
| `POST /auth/login` | Publico |
| `GET /users` | Apenas `ADMIN` |
| `GET /users/{id}` | `USER` ou `ADMIN` |
| `PUT /users/{id}` | Dono da conta ou `ADMIN` |
| `PATCH /users/{id}/password` | Dono da conta ou `ADMIN` |
| `DELETE /users/{id}` | Dono da conta ou `ADMIN` |
| `DELETE /users/all` | Apenas `ADMIN` |

## Como rodar

### Pre-requisitos

- Java 17
- Docker e Docker Compose
- Maven nao precisa estar instalado, pois o projeto usa Maven Wrapper

### 1. Configure as variaveis de ambiente

Crie um arquivo `.env` na raiz do projeto:

```env
POSTGRES_DB=usersapp
POSTGRES_USER=admin
POSTGRES_PASSWORD=admin123
JWT_SECRET=users-api-jwt-secret-key-change-me-2026
```

### 2. Suba a aplicacao com Docker Compose

```bash
docker compose up
```

A API ficara disponivel em:

```text
http://localhost:8081
```

O PostgreSQL fica exposto em:

```text
localhost:5439
```

### 3. Rodar localmente sem container da aplicacao

Suba apenas o banco, se preferir executar a API pela IDE:

```bash
docker compose up postgres
```

Depois rode:

```bash
./mvnw spring-boot:run
```

## Swagger

Documentacao interativa:

```text
http://localhost:8081/swagger-ui/index.html
```

OpenAPI JSON:

```text
http://localhost:8081/v3/api-docs
```

Para testar rotas protegidas pelo Swagger:

1. Faca login em `POST /auth/login`.
2. Use o token retornado no botao `Authorize`.
3. Informe o valor no formato:

```text
Bearer SEU_TOKEN
```

## Exemplos de uso

### Criar usuario

```http
POST /users
Content-Type: application/json
```

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
Content-Type: application/json
```

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

### Listagem paginada

```http
GET /users?page=0&size=10&sort=name,asc
Authorization: Bearer SEU_TOKEN
```

### Alterar senha

```http
PATCH /users/1/password
Authorization: Bearer SEU_TOKEN
Content-Type: application/json
```

```json
{
  "currentPassword": "Senha123",
  "newPassword": "NovaSenha123",
  "confirmNewPassword": "NovaSenha123"
}
```

## Endpoints principais

| Metodo | Rota | Descricao |
| --- | --- | --- |
| `POST` | `/users` | Cria um novo usuario |
| `POST` | `/auth/login` | Autentica e retorna JWT |
| `GET` | `/users` | Lista usuarios com paginacao |
| `GET` | `/users/{id}` | Busca usuario por ID |
| `PUT` | `/users/{id}` | Atualiza dados cadastrais |
| `PATCH` | `/users/{id}/password` | Altera senha |
| `DELETE` | `/users/{id}` | Exclui usuario |
| `DELETE` | `/users/all` | Exclui todos os usuarios |

## Modelo de erro

As excecoes da API retornam um contrato padronizado:

```json
{
  "message": "CEP nao encontrado",
  "status": 404,
  "timestamp": "2026-04-23T12:00:00"
}
```

## Banco de dados

As migrations ficam em:

```text
src/main/resources/db/migration
```

Migrations atuais:

| Migration | Descricao |
| --- | --- |
| `V1__create_tb_users.sql` | Cria tabelas de usuarios e enderecos |
| `V2__insert_test_users.sql` | Insere dados iniciais |
| `V3__sync_identity_sequences.sql` | Sincroniza sequences |
| `V4__add_column_role.sql` | Adiciona perfil de acesso |
| `V5__add_password_changed_at.sql` | Registra data de alteracao de senha |

## Estrutura do projeto

```text
src/main/java/farcic/dev/users
|-- client       # Integracao com ViaCEP usando OpenFeign
|-- config       # Security, Swagger/OpenAPI e tratamento de excecoes
|-- controller   # Endpoints REST
|-- dto          # Contratos de entrada e saida
|-- entity       # Entidades JPA
|-- exeption     # Excecoes de dominio
|-- mapper       # Conversao entre entidades e DTOs
|-- repository   # Acesso ao banco com Spring Data JPA
`-- service      # Regras de negocio
```

## Decisoes de arquitetura

- A API usa JWT stateless para evitar sessao no servidor.
- O `SecurityFilter` valida o token a cada requisicao e popula o contexto do Spring Security.
- A data `passwordChangedAt` permite rejeitar tokens emitidos antes da troca de senha.
- A regra de permissao por dono do recurso fica na camada de service, proxima do caso de uso.
- O schema do banco e controlado por Flyway, evitando dependencia de `ddl-auto` para criacao automatica.
- O ViaCEP fica isolado em um client Feign, mantendo controller e service livres de detalhes HTTP.

## Validacoes implementadas

- Email obrigatorio e em formato valido.
- Nome obrigatorio com limite de tamanho.
- Senha com minimo de 8 caracteres, contendo letras e numeros.
- Confirmacao obrigatoria na troca de senha.
- CEP validado pela resposta do ViaCEP.
- Email unico no cadastro e na atualizacao.

## Comandos uteis

```bash
# Rodar a aplicacao
./mvnw spring-boot:run

# Executar testes
./mvnw test

# Subir aplicacao e banco
docker compose up

# Subir somente o banco
docker compose up postgres
```
