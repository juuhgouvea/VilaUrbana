-- Função Logar --
-- SELECT logar('nome', 'senha')
DELIMITER $$
DROP FUNCTION IF EXISTS logar $$
CREATE FUNCTION logar (nome VARCHAR(150), senha_usuario VARCHAR(45))
RETURNS INT
BEGIN
    RETURN (SELECT COUNT(*) FROM usuarios AS u WHERE u.nome_usuario = nome AND u.senha = senha_usuario);
END $$
DELIMITER ;

-- Função Consultar Quantidade de Produtos de um restaurante --

DELIMITER $$
DROP FUNCTION IF EXISTS consultar_produtos_restaurante $$
CREATE FUNCTION consultar_produtos_restaurante(restaurante INT)
RETURNS INT
BEGIN
		RETURN (SELECT COUNT(*) AS Quantidade_Produtos
        FROM restaurantes_has_produtos WHERE cod_restaurante = restaurante);
END $$
DELIMITER ;