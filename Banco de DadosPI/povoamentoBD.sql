INSERT INTO `usuarios` VALUES (1, 'Julia Gouvêa Santos', 'juuhgouvea', 'juliasantos@yahoo.com', '12344');
INSERT INTO `usuarios` VALUES (2, 'Gustavo Galdino de Camargo', 'gustaacamargo', 'carmargogusta@gmail.com', '456789');
INSERT INTO `usuarios` VALUES (3, 'Ana Luiza Barreto', 'aninhabarreto', 'analuizab@hotmail.com', '34567');
INSERT INTO `usuarios` VALUES (4, 'Fernanda Swensson', 'ferswensson', 'fernandaswen@yahoo.com', '89067');
INSERT INTO `usuarios` VALUES (5, 'Yuri Ziemba', 'yuziem', 'yuriziemba@gmail.com', '367959');

INSERT INTO `restaurantes` (cod_usuario, nome_restaurante, foto_restaurante) VALUES (1, 'Vila Morretes', NULL);
INSERT INTO `restaurantes` (cod_usuario, nome_restaurante, foto_restaurante) VALUES (2, 'Dona Gertrudez', NULL);
INSERT INTO `restaurantes` (cod_usuario, nome_restaurante, foto_restaurante) VALUES (3, 'Barolo', NULL);
INSERT INTO `restaurantes` (cod_usuario, nome_restaurante, foto_restaurante) VALUES (4, 'Kandoo', NULL);
INSERT INTO `restaurantes` (cod_usuario, nome_restaurante, foto_restaurante) VALUES (5, 'Paradoja', NULL);

INSERT INTO `categoria` (desc_categoria) VALUES ('Sobremesas');
INSERT INTO `categoria` (desc_categoria) VALUES ('Salgados');
INSERT INTO `categoria` (desc_categoria) VALUES ('Pratos Quentes');
INSERT INTO `categoria` (desc_categoria) VALUES ('Pratos Frios');
INSERT INTO `categoria` (desc_categoria) VALUES ('Porções');
INSERT INTO `categoria` (desc_categoria) VALUES ('Bebidas');

INSERT INTO `produtos` (cod_categoria, nome_produto, foto_produto, valor, desc_produto) VALUES (1, 'Barreado', NULL, '60.00', 'Prato tipicamente paranaense');
INSERT INTO `produtos` (cod_categoria, nome_produto, foto_produto, valor, desc_produto) VALUES (2, 'Coxinha', NULL, '3.50', 'Salgado mais saboroso');
INSERT INTO `produtos` (cod_categoria, nome_produto, foto_produto, valor, desc_produto) VALUES (3, 'Risoto de salmão', NULL, '52.00', 'Servido com raspas de limão');
INSERT INTO `produtos` (cod_categoria, nome_produto, foto_produto, valor, desc_produto) VALUES (4, 'Combinado de sushi', NULL, '80.00', 'Combinado de 42 peças');
INSERT INTO `produtos` (cod_categoria, nome_produto, foto_produto, valor, desc_produto) VALUES (5, 'Porção de fritas', NULL, '19.90', 'Com tiras de bacon');

INSERT INTO `restaurantes_has_produtos` VALUES (1, 1);
INSERT INTO `restaurantes_has_produtos` VALUES (2, 2);
INSERT INTO `restaurantes_has_produtos` VALUES (3, 3);
INSERT INTO `restaurantes_has_produtos` VALUES (4, 4);
INSERT INTO `restaurantes_has_produtos` VALUES (5, 5);
