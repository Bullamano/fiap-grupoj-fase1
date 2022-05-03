-- Populando a tabela nhaf_categorias
INSERT INTO `needhelpappfiap`.`nhaf_categorias`
(`CTG_ID`,
`CTG_NOME`)
VALUES
(1,
'Receitas rápidas'),
(2,
'Reparos e consertos'),
(3,
'Roupas'),
(4,
'Dicas diversas');

-- Populando a tabela nhaf_paginas
INSERT INTO `needhelpappfiap`.`nhaf_paginas`
(`PG_ID`,
`PG_CTG_ID`,
`PG_NOME`)
VALUES
(1,
1,
'Arroz com feijão'),
(2,
1,
'Lasagna'),
(3,
2,
'Como desentupir uma privada'),
(4,
3,
'Nós de gravata'),
(5,
3,
'Como dobrar camisetas de um jeito eficiente'),
(6,
4,
'Lugares para se visitar em São Paulo');

-- Populando a tabela nhaf_procedimentos
INSERT INTO `needhelpappfiap`.`nhaf_procedimentos`
(`PRC_ID`,
`PRC_PG_ID`,
`PRC_TITULO`,
`PRC_TAREFAS`)
VALUES
(1,
1,
'Ingredientes',
'3 xícaras de arroz cru, 2 concha de feijão cozido...'),
(2,
1,
'Preparo',
'Coloque o óleo, a cebola e o alho...'),
(3,
2,
'Ingredientes',
'500g de massa de lasagna, 500g de carne moída...'),
(4,
2,
'Preparo',
'Cozinhe a massa segundo as orientações...'),
(5,
3,
'Passo-a-passo',
'Pegue o desentupidor. Posicione-o...'),
(6,
4,
'Opções',
'Existem diversos nós possíveis para serem usados em diferentes ocasiões...'),
(7,
5,
'Passo-a-passo',
'Pegue as mangas da camiseta e dobre-as no sentido...'),
(8,
6,
'Top 10 lugares',
'1- MASP, 2- MIS, 3-...');

-- Populando a tabela nhaf_recursos
INSERT INTO `needhelpappfiap`.`nhaf_recursos`
(`REC_ID`,
`REC_PG_ID`,
`REC_LINKVIDEO`,
`REC_LINKLEITURA`,
`REC_LINKIMAGEM`)
VALUES
(1,
6,
null,
'https://www.masp.org.br/',
'https://imagens.ebc.com.br/ZnNy-PMxXCSQ8ig6dLoAkuhwmb0=/1170x700/smart/https://agenciabrasil.ebc.com.br/sites/default/files/thumbnails/image/avenida_paulista_rvsa_081220202132.jpg?itok=KuTl0h-D'),
(2,
6,
'https://www.youtube.com/watch?v=xAg7z6u4NE8&ab_channel=tiehole',
'https://www.wikihow.com/Tie-a-Windsor-Knot',
null);
