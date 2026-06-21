-- Criação da tabela de ingredientes
CREATE TABLE IF NOT EXISTS ingredientes (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL UNIQUE,
    descricao VARCHAR(500),
    preco_por_unidade DECIMAL(10, 2) NOT NULL,
    unidade_medida VARCHAR(50) NOT NULL
);

-- Criação da tabela de itens de estoque
CREATE TABLE IF NOT EXISTS itens_estoque (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    ingrediente_id BIGINT NOT NULL UNIQUE,
    quantidade INT NOT NULL,
    quantidade_minima INT NOT NULL,
    FOREIGN KEY (ingrediente_id) REFERENCES ingredientes(id)
);

-- Inserção de ingredientes iniciais
INSERT INTO ingredientes (nome, descricao, preco_por_unidade, unidade_medida) VALUES
('Massa', 'Massa de pizza fresca', 5.00, 'kg'),
('Molho de Tomate', 'Molho de tomate para pizza', 8.00, 'litro'),
('Queijo Mozzarella', 'Queijo mozzarella ralado', 25.00, 'kg'),
('Calabresa', 'Calabresa fatiada', 30.00, 'kg'),
('Pepperoni', 'Pepperoni importado', 40.00, 'kg'),
('Cebola', 'Cebola fresca', 3.50, 'kg'),
('Alho', 'Alho descascado', 15.00, 'kg'),
('Orégano', 'Orégano seco', 20.00, 'kg'),
('Azeitona', 'Azeitona preta', 18.00, 'kg'),
('Pimentão', 'Pimentão vermelho', 4.00, 'kg');

-- Inserção de itens de estoque iniciais
INSERT INTO itens_estoque (ingrediente_id, quantidade, quantidade_minima) VALUES
(1, 50, 10),   -- Massa: 50kg, mínimo 10kg
(2, 30, 5),    -- Molho: 30L, mínimo 5L
(3, 100, 20),  -- Queijo: 100kg, mínimo 20kg
(4, 80, 15),   -- Calabresa: 80kg, mínimo 15kg
(5, 40, 10),   -- Pepperoni: 40kg, mínimo 10kg
(6, 60, 10),   -- Cebola: 60kg, mínimo 10kg
(7, 25, 5),    -- Alho: 25kg, mínimo 5kg
(8, 10, 2),    -- Orégano: 10kg, mínimo 2kg
(9, 35, 5),    -- Azeitona: 35kg, mínimo 5kg
(10, 45, 8);   -- Pimentão: 45kg, mínimo 8kg
