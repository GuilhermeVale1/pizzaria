# 🍕 Backend - Pizzaria Delivery API

API REST desenvolvida com **Spring Boot** para gerenciar o backend de uma pizzaria, incluindo autenticação com JWT, cadastro de clientes, pedidos com pizzas e bebidas, gerenciamento de endereços e finalização de compras.

### Pré-requisitos

- Java 17+
- Maven
- MySQL
- IDE (IntelliJ, VSCode ou Eclipse)

### Arquitetura software
![image](https://github.com/user-attachments/assets/69a69fbe-501f-4d50-8ab8-9245831edb33)


### Banco de dados

1. **Abrir MySQL Workbench**  
   Conecte-se ao servidor MySQL local (geralmente `localhost` na porta padrão `3306`).  

2. ** Criar o banco de dados pelo phpmyAdmin **
   Para criar o banco de dados pelo phpmyAdmin, basta criar um banco de dados chamado pizzaria.

3. **Criar o banco de dados**  
   Na aba de query (SQL Editor), execute o comando abaixo para criar o banco:  
   ```sql
   CREATE DATABASE pizzaria;
   USE pizzaria;
### Estrutura de pastas


### 🗂️ Estrutura de Pastas

```plaintext
src/
└── main/
    └── java/
        └── com/
            └── pizzaria/
                ├── controllers/      
                ├── dtos/             
                ├── models/           
                ├── repositories/      
                ├── services/         
                ├── security/        
                └── config/           














