-- Setores
MERGE INTO setores (nome, descricao) KEY(nome)
VALUES ('Injecao', 'Maquinas injetoras de plastico');

MERGE INTO setores (nome, descricao) KEY(nome)
VALUES ('Montagem', 'Linha de montagem final');

MERGE INTO setores (nome, descricao) KEY(nome)
VALUES ('Pintura', 'Cabine de pintura eletrostatica');

-- Maquinas
MERGE INTO maquinas (nome, modelo, numero_serie, setor_id) KEY(nome)
VALUES ('Injetora-01', 'XYZ-3000', 'SN-1001',
    (SELECT id FROM setores WHERE nome = 'Injecao'));

MERGE INTO maquinas (nome, modelo, numero_serie, setor_id) KEY(nome)
VALUES ('Injetora-02', 'XYZ-3000', 'SN-1002',
    (SELECT id FROM setores WHERE nome = 'Injecao'));

MERGE INTO maquinas (nome, modelo, numero_serie, setor_id) KEY(nome)
VALUES ('Injetora-03', 'ABC-2000', 'SN-1003',
    (SELECT id FROM setores WHERE nome = 'Injecao'));

MERGE INTO maquinas (nome, modelo, numero_serie, setor_id) KEY(nome)
VALUES ('Esteira-01', 'EM-500', 'SN-2001',
    (SELECT id FROM setores WHERE nome = 'Montagem'));

MERGE INTO maquinas (nome, modelo, numero_serie, setor_id) KEY(nome)
VALUES ('Esteira-02', 'EM-500', 'SN-2002',
    (SELECT id FROM setores WHERE nome = 'Montagem'));

MERGE INTO maquinas (nome, modelo, numero_serie, setor_id) KEY(nome)
VALUES ('Robo-Pintura', 'RP-100', 'SN-3001',
    (SELECT id FROM setores WHERE nome = 'Pintura'));
    