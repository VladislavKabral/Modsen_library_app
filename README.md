# Modsen Library

This project represents an API for a simple library. If you want to use this application, you have to register. 
For registration you have to send your email and password (don't worry, your password is stored encrypted), after 
that you will receive JWT-token, which you can use in your requests.

# Project features

There are all project features, which you can try, while you are using the API.

+ Getting all books from the library;
+ Getting a book by id;
+ Getting a book by name;
+ Getting a book by ISBN;
+ Saving a new book in the library;
+ Updating an existing book;
+ Deleting a book by id;
+ Getting all available books;
+ Issuance of the book;
+ Return of the book;
+ Registration in the service;
+ Authorization in the service.

# Project endpoints

There are only urls of endpoints and their descriptions. If you want to see more about that API you can use the documentation 
of this API, created by Swagger. For using this documentation you have to run the application. The address of the documentation
is 'http://localhost:8080/swagger-ui/index.html'.


+ *(GET)* **'/library/api/books'** - endpoint for getting all library's books;
+ *(GET)* **'/library/api/books/book?id={book's id}'** - endpoint for getting a book by id;
+ *(GET)* **'/library/api/books/book?name={book's name}'** - endpoint for getting book by name;
+ *(GET)* **'/library/api/books/book?isbn={book's isbn}'** - endpoint for getting book by ISBN;
+ *(POST)* **'/library/api/books'** - endpoint for saving new book;
+ *(PATCH)* **'/library/api/books/{book's id}'** - endpoint for updating an existing book;
+ *(DELETE)* **'/library/api/books/{book's id}'** - endpoint for deleting book by id;
+ *(GET)* **'/library/api/availableBooks'** - endpoint for getting all available books;
+ *(PATCH)* **'/librari/api/book?action=issue'** - endpoint for issuance of the book;
+ *(PATCH)* **'/librari/api/book?action=return'** - endpoint for return of the book;
+ *(POST)* **'/librari/api/auth/register'** - endpoint for registration in the service;
+ *(POST)* **'/librari/api/login'** - endpoint for authorization in the service.

# Project technologies

There are the main technologies used in the development.

+ **Spring Boot** for configuration the application;
+ **Spring Data Jpa** for work with database;
+ **Spring REST** for creation controllers;
+ **Spring Securty** for protection the API;
+ **Spring Validation** for validation the entities;
+ **ModelMapper** for converting entities;
+ **Liquibase** for configuration migration;
+ **PostgreSQL** as RDBMS;
+ **Swagger** for documentation the API;
+ **Log4j2** for logging;
+ **Lombok** to simplify writing classes (automatic method generation);
+ **BCrypt** for passwords encryption;
+ **JWT** for creation token, which uses in security;
+ **Jackson** for converting Java-objects to JSON-format.
