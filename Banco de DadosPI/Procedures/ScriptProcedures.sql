-- 1- Procedure que mostra a quantidade de restaurantes que cada usu�rio possui no sistema.

DELIMITER $$
DROP PROCEDURE IF EXISTS restaurantes_por_usuarios $$
CREATE PROCEDURE restaurantes_por_usuarios()
BEGIN
    SELECT u.nome_completo, COUNT(r.cod_restaurante) AS quantidade 
        FROM usuarios u, restaurantes r 
        WHERE u.cod_usuario = r.cod_usuario GROUP BY u.nome_completo;
END $$
DELIMITER ;

-- CHAMAR PROCEDURE --
CALL restaurantes_por_usuarios();

-- 2- Mostra os produtos de uma determinada categoria.

DELIMITER $$
DROP PROCEDURE IF EXISTS produtos_de_categoria $$
CREATE PROCEDURE produtos_de_categoria(IN cod_categoria INT)
BEGIN
    SELECT p.nome_produto FROM produtos p WHERE p.cod_categoria = cod_categoria;
END $$
DELIMITER ;

--CHAMAR OU EXCLUIR PROCEDURE--
CALL produtos_de_categoria(<n>); -- <n> parametro que espera um codigo da categoria

-- 3- Mostra o codigo do restaurante com mais produtos no sistema.

DELIMITER $$
DROP PROCEDURE IF EXISTS restaurante_maisProdutos $$
CREATE PROCEDURE restaurante_maisProdutos()
BEGIN
    SELECT r.cod_restaurante FROM restaurantes_has_produtos r GROUP BY r.cod_restaurante ORDER BY COUNT(r.cod_restaurante) DESC LIMIT 1;
END $$
DELIMITER ;

--CHAMAR OU EXCLUIR PROCEDURE--
CALL restaurante_maisProdutos();


-- 4- Recebe um valor como parametro e mostrar os produtos cujo valor seja menor ou igual ao valor requerido

DELIMITER $$
DROP PROCEDURE IF EXISTS produtos_por_valor  $$
CREATE PROCEDURE produtos_por_valor(IN valor INT)
BEGIN
    SELECT p.nome_produto AS 'produto', p.valor AS 'valor' FROM produtos p WHERE p.valor <= valor;
END $$
DELIMITER ;

-- Chamar procedure --
CALL produtos_por_valor(<valor>) -- <valor> é o valor requerido

-- 5- Categoria com mais produtos

DELIMITER $$
DROP PROCEDURE IF EXISTS categoria_maisProdutos  $$
CREATE PROCEDURE categoria_maisProdutos()
BEGIN
    SELECT p.cod_categoria AS 'categoria' FROM produtos p GROUP BY p.cod_categoria ORDER BY COUNT(p.cod_categoria) DESC LIMIT 1;
END $$
DELIMITER ;

-- Chamar procedure --
CALL categoria_maisProdutos()