# 🍕 Backend - Pizzaria Delivery API

API REST desenvolvida com **Spring Boot** para gerenciar o backend de uma pizzaria, incluindo autenticação com JWT, cadastro de clientes, pedidos com pizzas e bebidas, gerenciamento de endereços e finalização de compras.

### Pré-requisitos

- Java 17+
- Maven
- MySQL
- IDE (IntelliJ, VSCode ou Eclipse)


### Estrutura de pastas


src/main/java/com/pizzaria/
│
├── controllers/       # Endpoints (ex: AuthController, ClienteController, PedidoController)
├── dtos/              # DTOs de entrada e saída
├── models/            # Entidades JPA (Cliente, Endereco, Pedido, Produto, etc)
├── repositories/      # Interfaces que estendem JpaRepository
├── services/          # Lógica de negócio
├── security/          # JWT, filtros e autenticação
└── config/            # CORS, SecurityConfig, ModelMapper, etc
