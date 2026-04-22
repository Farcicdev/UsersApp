CREATE TABLE tb_endereco (
    id BIGINT PRIMARY KEY,
    rua VARCHAR(120) NOT NULL,
    numero VARCHAR(20) NOT NULL,
    complemento VARCHAR(100),
    bairro VARCHAR(100) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    estado VARCHAR(2) NOT NULL,
    cep VARCHAR(9) NOT NULL
);

CREATE TABLE tb_users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255),
    enderece_id BIGINT NOT NULL UNIQUE,
    CONSTRAINT fk_tb_users_endereco FOREIGN KEY (enderece_id) REFERENCES tb_endereco (id)
);
