-- Event scheduler --
-- Substitui a descricao de todos os produtos que tenham sua descricao vazia ou nula

DELIMITER $$
DROP EVENT IF EXISTS substitue_desc $$
CREATE EVENT substitue_desc
ON SCHEDULE EVERY 1 MINUTE STARTS NOW() -- ENDS NOW() + INTERVAL 5 MINUTE
ENABLE
DO
BEGIN
    UPDATE produtos SET desc_produto = "Nao ha descricao para esse produto" WHERE desc_produto LIKE " " OR desc_produto IS NULL OR desc_produto LIKE "";
END $$
DELIMITER ;
