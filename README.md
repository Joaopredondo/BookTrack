# BookTrack Application

## Descrição

O **BookTrack** é uma aplicação de gestão de biblioteca desenvolvida em Java usando Spring Boot. A aplicação permite o cadastro de livros, usuários, e o gerenciamento de empréstimos. Além disso, oferece um sistema de recomendação de livros com base nos empréstimos anteriores de um usuário.

## Funcionalidades

- **Cadastro de Usuários**: Criar, ler, atualizar e deletar usuários.
- **Gerenciamento de Livros**: Criar, ler, atualizar e deletar livros.
- **Gestão de Empréstimos**: Criar, atualizar e deletar empréstimos de livros.
- **Recomendação de Livros**: Sistema de recomendação baseado nas categorias dos livros que o usuário já leu.

## Requisitos

- **Java 17** ou superior
- **Maven 3.8.1** ou superior
- **Docker** e **Docker Compose** (para o banco de dados PostgreSQL)

## Configuração do Banco de Dados

A aplicação usa PostgreSQL como banco de dados. Você pode configurar o banco de dados usando Docker ou manualmente.

### Usando Docker

1. **Configure o banco de dados** usando o Docker Compose:

   ```yaml
   services:
     db:
       image: postgres:latest
       environment:
         POSTGRES_DB: biblioteca
         POSTGRES_USER: usuario
         POSTGRES_PASSWORD: senha
       ports:
         - "5432:5432"
       volumes:
         - postgres_data:/var/lib/postgresql/data
   volumes:
     postgres_data:

Execute o Docker Compose para iniciar o banco de dados:
Para iniciar o banco de dados, use o comando docker-compose up -d.

Após configurar o banco de dados, instale as dependências da aplicação usando o comando mvn clean install. Para executar a aplicação, utilize o comando mvn spring-boot:run. A aplicação estará disponível no navegador em http://localhost:8080.

## Endpoints da API:

## Usuários:

 - GET /api/users: Retorna a lista de todos os usuários.
   
 - GET /api/users/{id}: Retorna os detalhes de um usuário específico.

 - POST /api/users: Cria um novo usuário. 
  
  Exemplo de corpo da requisição:
  {
    "name": "John Doe",
    "email": "johndoe@example.com",
    "phone": "+123456789"
  }
  
  - PUT /api/users/{id}: Atualiza um usuário existente.
    
  Exemplo de corpo da requisição:
  {
    "name": "John Doe",
    "email": "johndoe@example.com",
    "phone": "+123456789"
  }
  
  - DELETE /api/users/{id}: Deleta um usuário.

## Livros:

- GET /api/books: Retorna a lista de todos os livros.

- GET /api/books/{id}: Retorna os detalhes de um livro específico.

- POST /api/books: Cria um novo livro. 

Exemplo de corpo da requisição:
{
  "title": "Clean Code",
  "author": "Robert C. Martin",
  "isbn": "9780132350884",
  "publicationDate": "2008-08-01",
  "category": "Software Engineering"
}

- PUT /api/books/{id}: Atualiza um livro existente. 

Exemplo de corpo da requisição:
{
  "title": "Clean Code",
  "author": "Robert C. Martin",
  "isbn": "9780132350884",
  "publicationDate": "2008-08-01",
  "category": "Software Engineering"
}
- DELETE /api/books/{id}: Deleta um livro.

## Empréstimos:
- GET /api/loans: Retorna a lista de todos os empréstimos.

- GET /api/loans/{id}: Retorna os detalhes de um empréstimo específico.

- POST /api/loans: Cria um novo empréstimo. 
Exemplo de corpo da requisição:
{
  "user": { "id": 1 },
  "book": { "id": 1 },
  "loanDate": "2024-08-18",
  "returnDate": "2024-08-25",
  "status": "ACTIVE"
}

- PUT /api/loans/{id}: Atualiza um empréstimo existente. 
Exemplo de corpo da requisição:
{
  "returnDate": "2024-08-25",
  "status": "RETURNED"
}

- DELETE /api/loans/{id}: Deleta um empréstimo.

- Recomendações:
GET /api/recommendations/{userId}: Retorna uma lista de recomendações de livros para um usuário específico com base nas categorias dos livros já emprestados por ele.

-- Collection em formato Json, se encontra na raiz do projeto.
