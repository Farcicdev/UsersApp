INSERT INTO tb_endereco (id, rua, numero, complemento, bairro, cidade, estado, cep)
VALUES
    (1, 'Rua das Flores', '100', 'Apto 12', 'Centro', 'Sao Paulo', 'SP', '01001-000'),
    (2, 'Avenida Brasil', '2500', NULL, 'Jardim America', 'Rio de Janeiro', 'RJ', '20040-001');

INSERT INTO tb_users (name, email, password, enderece_id)
VALUES
    ('Joao Silva', 'joao.silva@example.com', '123456', 1),
    ('Maria Santos', 'maria.santos@example.com', '123456', 2);
