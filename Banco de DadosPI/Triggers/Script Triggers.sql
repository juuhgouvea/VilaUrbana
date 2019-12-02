-- Triggers do Sistema --

-- Valida o usuario verificando o email e a senha --
DELIMITER $$
DROP TRIGGER IF EXISTS valida_usuario $$
CREATE TRIGGER valida_usuario BEFORE INSERT ON usuarios FOR EACH ROW
BEGIN
    DECLARE msg VARCHAR(255);
    DECLARE usuario_email VARCHAR(100);
    DECLARE servidor_email VARCHAR(100);
    DECLARE tamanho_email INTEGER;

    SET tamanho_email = LENGTH(new.email);
    IF (tamanho_email < 10) THEN
        SET msg = 'Email inválido! O campo email deve ter ao menos 10 caracteres!';
        SIGNAL SQLSTATE '10000' SET MESSAGE_TEXT = msg;
    END IF;

    SET usuario_email = (SELECT SUBSTRING_INDEX(new.email, '@', 1));
    SET servidor_email = (SELECT SUBSTRING_INDEX(new.email, '@', -1));

    IF(LENGTH(usuario_email) = tamanho_email OR LENGTH(servidor_email) = tamanho_email) THEN
        SET msg = "Email Inválido! O campo deve indicar o usuario e servidor separado por '@'";
        SIGNAL SQLSTATE '10000' SET MESSAGE_TEXT = msg;
    END IF;

    SET new.email = CONCAT(usuario_email, "@", servidor_email);

    IF (LENGTH(new.senha) < 6) THEN
        SET msg = 'Senha inválida! O campo senha deve ter ao menos 6 caracteres!';
        SIGNAL SQLSTATE '10000' SET MESSAGE_TEXT = msg;
    END IF;
END $$
DELIMITER ;

-- Valida o Restaurante --

DELIMITER $$
DROP TRIGGER IF EXISTS valida_restaurante $$
CREATE TRIGGER valida_restaurante BEFORE INSERT ON restaurantes FOR EACH ROW
BEGIN
    DECLARE msg VARCHAR(255);

    IF (LENGTH(new.nome_restaurante) < 6) THEN
        SET msg = 'Nome inválido! O campo nome deve ter ao menos 6 caracteres!';
        SIGNAL SQLSTATE '10001' SET MESSAGE_TEXT = msg;
    END IF;
END $$
DELIMITER ;

-- Valida Produto --

DELIMITER $$
DROP TRIGGER IF EXISTS valida_produto $$
CREATE TRIGGER valida_produto BEFORE INSERT ON produtos FOR EACH ROW
BEGIN
    DECLARE msg VARCHAR(255);
    
    IF (LENGTH(new.nome_produto) < 2) THEN
        SET msg = 'Nome do produto inválido! O campo nome do produto deve ter ao menos 2 caracteres!';
        SIGNAL SQLSTATE '10002' SET MESSAGE_TEXT = msg;
    END IF;

    IF (new.valor < 0) THEN
        SET msg = 'Valor do produto inválido! Valor não pode ser negativo!';
        SIGNAL SQLSTATE '10002' SET MESSAGE_TEXT = msg;
    END IF;
END $$
DELIMITER ;