CREATE TABLE usuarios(cod_usuario INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	nome_completo varchar(150) not null,
	nome_usuario varchar(45) not null,
	email varchar(45) not null,
	senha varchar(45) not null);


CREATE TABLE restaurantes(cod_restaurante INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	nome_restaurante varchar(45) not null,
	foto_restaurante varchar(250),
	cod_usuario INTEGER NOT NULL REFERENCES usuarios(cod_usuario) ON DELETE CASCADE);


CREATE TABLE categoria(cod_categoria INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	desc_categoria varchar(200) not null);

CREATE TABLE produtos(cod_produto INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	nome_produto varchar(200) not null,
	foto_produto varchar(250),
	valor float not null,
	desc_produto varchar(450) not null,
	cod_categoria INTEGER NOT NULL REFERENCES categoria(cod_categoria) ON DELETE CASCADE);

CREATE TABLE restaurantes_has_produtos(
	cod_restaurante INTEGER NOT NULL REFERENCES restaurantes(cod_restaurante) ON DELETE CASCADE,
	cod_produto INTEGER NOT NULL REFERENCES produtos(cod_produto) ON DELETE CASCADE);