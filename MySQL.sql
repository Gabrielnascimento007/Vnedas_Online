DROP DATABASE IF EXISTS meu_banco_de_dados;  -- Para evitar erro se o banco já existir
CREATE DATABASE meu_banco_de_dados;
USE meu_banco_de_dados;

-- Comandos para criar tabelas
CREATE TABLE CLIENTE (
    CLIENTE_ID INT AUTO_INCREMENT PRIMARY KEY,
    NOME VARCHAR(100),
    EMAIL VARCHAR(100),
    TELEFONE VARCHAR(15),
    ENDERECO VARCHAR(255)
);


CREATE TABLE PEDIDO (
    PEDIDO_ID INT PRIMARY KEY AUTO_INCREMENT,
    DATA DATE NOT NULL,
    VALOR_TOTAL DECIMAL(10, 2) NOT NULL,
    CLIENTE_ID INT,
    FOREIGN KEY (CLIENTE_ID) REFERENCES CLIENTE(CLIENTE_ID) ON DELETE CASCADE
);

CREATE TABLE PRODUTO (
    PRODUTO_ID INT PRIMARY KEY AUTO_INCREMENT,
    NOME VARCHAR(100) NOT NULL,
    DESCRICAO TEXT,
    PRECO DECIMAL(10, 2) NOT NULL,
    CATEGORIA VARCHAR(100)
);

CREATE TABLE ITEM_PEDIDO (
    ITEM_PEDIDO_ID INT PRIMARY KEY AUTO_INCREMENT,
    PEDIDO_ID INT,
    PRODUTO_ID INT,
    QUANTIDADE INT NOT NULL,
    VALOR_UNITARIO DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (PEDIDO_ID) REFERENCES PEDIDO(PEDIDO_ID) ON DELETE CASCADE,
    FOREIGN KEY (PRODUTO_ID) REFERENCES PRODUTO(PRODUTO_ID) ON DELETE CASCADE
);

ALTER TABLE PRODUTO ADD COLUMN QUANTIDADE_ESTOQUE INT NOT NULL;
GRANT ALL PRIVILEGES ON meu_banco_de_dados.* TO 'root'@'localhost';
FLUSH PRIVILEGES;