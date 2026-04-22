SELECT setval(
    pg_get_serial_sequence('tb_endereco', 'id'),
    COALESCE((SELECT MAX(id) FROM tb_endereco), 1)
);

SELECT setval(
    pg_get_serial_sequence('tb_users', 'id'),
    COALESCE((SELECT MAX(id) FROM tb_users), 1)
);
