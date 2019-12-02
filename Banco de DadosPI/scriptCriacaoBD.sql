CREATE TABLE usuarios(cod_usuario int not null auto_increment,
	nome_completo varchar(150) not null,
	nome_usuario varchar(45) not null,
	email varchar(45) not null,
	senha varchar(45) not null,
	PRIMARY KEY(cod_usuario));


CREATE TABLE restaurantes(cod_restaurante int not null auto_increment,
	cod_usuario int not null,
	nome_restaurante varchar(45) not null,
	foto_restaurante varchar(250),
	PRIMARY KEY(cod_restaurante),
	FOREIGN KEY(cod_usuario) REFERENCES usuarios(cod_usuario) ON DELETE CASCADE);


CREATE TABLE categoria(cod_categoria int not null auto_increment,
	desc_categoria varchar(200) not null,
	PRIMARY KEY(cod_categoria));

CREATE TABLE produtos(cod_produto int not null auto_increment,
	cod_categoria int not null,
	nome_produto varchar(200) not null,
	foto_produto varchar(250),
	valor float not null,
	desc_produto varchar(450) not null,
	PRIMARY KEY(cod_produto),
	FOREIGN KEY(cod_categoria) REFERENCES categoria(cod_categoria) ON DELETE CASCADE);

CREATE TABLE restaurantes_has_produtos(cod_restaurante int not null,
	cod_produto int not null,
	FOREIGN KEY(cod_restaurante) REFERENCES restaurantes(cod_restaurante) ON DELETE CASCADE,
	FOREIGN KEY(cod_produto) REFERENCES produtos(cod_produto) ON DELETE CASCADE);