CREATE TABLE oportunidade(
    id serial PRIMARY KEY,
    nome_prospecto VARCHAR(80) NOT NULL,
    descricao VARCHAR(200) NOT NULL,
    valor DECIMAL(10,2) NOT NULL
);