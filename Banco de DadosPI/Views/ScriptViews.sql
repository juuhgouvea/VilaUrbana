-- Restaurantes por usuario --

CREATE VIEW restaurantes_por_usuario
    AS SELECT cod_usuario as Usuario, COUNT(*) as Quantidade 
    FROM restaurantes GROUP BY cod_usuario;

-- Produtos por restaurante --
CREATE VIEW produtos_por_restaurante AS
	SELECT cod_restaurante as Restaurante, COUNT(*) as Quantidade_Produtos 
	FROM restaurantes_has_produtos GROUP BY cod_restaurante;