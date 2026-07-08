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

MERGE INTO usuarios (login, senha, nome, tipo, setor_id) KEY(login)
VALUES ('joao', '123', 'Joao Operador', 'OPERADOR', (SELECT id FROM setores WHERE nome = 'Injecao'));

MERGE INTO usuarios (login, senha, nome, tipo, setor_id) KEY(login)
VALUES ('maria', '123', 'Maria Tecnica', 'TECNICO', (SELECT id FROM setores WHERE nome = 'Injecao'));

MERGE INTO usuarios (login, senha, nome, tipo, setor_id) KEY(login)
VALUES ('pedro', '123', 'Pedro Lider', 'LIDER', (SELECT id FROM setores WHERE nome = 'Injecao'));

MERGE INTO usuarios (login, senha, nome, tipo, setor_id) KEY(login)
VALUES ('carlos', '123', 'Carlos Tecnico', 'TECNICO', (SELECT id FROM setores WHERE nome = 'Montagem'));

MERGE INTO usuarios (login, senha, nome, tipo, setor_id) KEY(login)
VALUES ('lucia', '123', 'Lucia Lider', 'LIDER', (SELECT id FROM setores WHERE nome = 'Montagem'));

MERGE INTO usuarios (login, senha, nome, tipo, setor_id) KEY(login)
VALUES ('roberto', '123', 'Roberto Especialista', 'ESPECIALISTA', NULL);

MERGE INTO usuarios (login, senha, nome, tipo, setor_id) KEY(login)
VALUES ('fernanda', '123', 'Fernanda Especialista', 'ESPECIALISTA', NULL);

MERGE INTO usuarios (login, senha, nome, tipo, setor_id) KEY(login)
VALUES ('ana', '123', 'Ana Visualizadora', 'VISUALIZADOR', NULL);

-- Chamados (só insere se tabela vazia)

INSERT INTO chamados
(titulo, descricao, motivo_falha, status, maquina_id, tecnico_id,
 data_abertura, data_conclusao, inicio_reparo, tempo_reparo_acumulado,
 tempo_locomocao_segundos, alerta30_min_enviado)
SELECT 'Motor com alta temperatura durante produção', 'ELETRICA', 'CONCLUIDO',
       (SELECT id FROM maquinas WHERE nome = 'Injetora-01'),
       (SELECT id FROM usuarios WHERE login = 'maria'),
       '2025-06-01 08:00:00', '2025-06-01 08:25:00', '2025-06-01 08:10:00', 900, 600, true
WHERE NOT EXISTS (SELECT 1 FROM chamados);

INSERT INTO chamados
(titulo, descricao, motivo_falha, status, maquina_id, tecnico_id,
 data_abertura, data_conclusao, inicio_reparo, tempo_reparo_acumulado,
 tempo_locomocao_segundos, alerta30_min_enviado)
SELECT 'Vibração anormal', 'Injetora-02 com vibração fora do padrão', 'MECANICA', 'CONCLUIDO',
       (SELECT id FROM maquinas WHERE nome = 'Injetora-02'),
       (SELECT id FROM usuarios WHERE login = 'maria'),
       '2025-06-05 09:00:00', '2025-06-05 09:45:00', '2025-06-05 09:15:00', 1800, 900, true
WHERE NOT EXISTS (SELECT 1 FROM chamados);

INSERT INTO chamados
(titulo, descricao, motivo_falha, status, maquina_id, tecnico_id,
 data_abertura, data_conclusao, inicio_reparo, tempo_reparo_acumulado,
 tempo_locomocao_segundos, alerta30_min_enviado)
SELECT 'Falha no sensor', 'Sensor de temperatura com leitura errada', 'ELETRONICA', 'CONCLUIDO',
       (SELECT id FROM maquinas WHERE nome = 'Injetora-01'),
       (SELECT id FROM usuarios WHERE login = 'maria'),
       '2025-06-10 10:00:00', '2025-06-10 10:30:00', '2025-06-10 10:05:00', 1500, 300, false
WHERE NOT EXISTS (SELECT 1 FROM chamados);

INSERT INTO chamados
(titulo, descricao, motivo_falha, status, maquina_id, tecnico_id,
 data_abertura, data_conclusao, inicio_reparo, tempo_reparo_acumulado,
 tempo_locomocao_segundos, alerta30_min_enviado)
SELECT 'Esteira parada', 'Esteira-01 parou de girar', 'MECANICA', 'EM_ANDAMENTO',
       (SELECT id FROM maquinas WHERE nome = 'Esteira-01'),
       (SELECT id FROM usuarios WHERE login = 'carlos'),
       '2025-06-15 14:00:00', NULL, '2025-06-15 14:10:00', 600, NULL, false
WHERE NOT EXISTS (SELECT 1 FROM chamados);

INSERT INTO chamados
(titulo, descricao, motivo_falha, status, maquina_id, tecnico_id,
 data_abertura, data_conclusao, inicio_reparo, tempo_reparo_acumulado,
 tempo_locomocao_segundos, alerta30_min_enviado)
SELECT 'Vazamento de óleo', 'Pintura com vazamento', 'HIDRAULICA', 'ABERTO',
       (SELECT id FROM maquinas WHERE nome = 'Robo-Pintura'),
       NULL,
       '2025-06-20 11:00:00', NULL, NULL, NULL, NULL, false
WHERE NOT EXISTS (SELECT 1 FROM chamados);