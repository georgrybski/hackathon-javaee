-- INSERT INTO curso_rest.USUARIO
-- (DATA_DE_ATUALIZACAO, DATA_DE_CRIACAO, DATA_DE_NASCIMENTO, EMAIL, LOGIN, NOME, SENHA, SALT)
-- VALUES(NOW(), NOW(), NOW(), 'eveag@protonmail.com', 'evies', 'eveline aguiar', 'e5f415f2f9aee31796616f6cd5398a2bd17143e46af7d65c35f9434b8067f00f', '2R90q0fW:sZw'),
--       (NOW(), NOW(), NOW(), 'georgmatheusrybski@gmail.com', 'rybski', 'georg matheus', '16b091a92403c25c813e9553767f6361194d0c07c2876ca50261cda6fd7faae5', 'rFF76Xjx_V9I'),
--       (NOW(), NOW(), NOW(), 'jane.doe@hotmail.com', 'jenny', 'jane', '2f67ebd4265f589a643b72a4e10dac344bcd3b7d695b9ab31f0e829f6fb6718d', 'K-3l?7?ZrTRv'),
--       (NOW(), NOW(), NOW(), 'klaus.rybski@hotmail.com', 'krybski', 'klaus rybski', 'bd8bd63aabb36e774683e6ca07c5d0b680affb3d8e7038cddb69fd5d1ee1d043','AP37UNu1LF#/'),
--       (NOW(), NOW(), NOW(), 'gabriel.marinho@gmail.com', 'marinho', 'gabriel marinho', 'dde282423d45825c0847164c7af7e6e8aa176adab5afbcc112c2a916761f2881','LVG8a*1oh2G)');

INSERT INTO curso_rest.USUARIO
    (DATA_DE_ATUALIZACAO, DATA_DE_CRIACAO, DATA_DE_NASCIMENTO, EMAIL, LOGIN, NOME, SENHA, SALT)
VALUES(NOW(), NOW(), '1990-01-01', 'eveag@protonmail.com', 'evies', 'eveline aguiar', 'e5f415f2f9aee31796616f6cd5398a2bd17143e46af7d65c35f9434b8067f00f', '2R90q0fW:sZw'),
      (NOW(), NOW(), '1995-02-15', 'georgmatheusrybski@gmail.com', 'rybski', 'georg matheus', '16b091a92403c25c813e9553767f6361194d0c07c2876ca50261cda6fd7faae5', 'rFF76Xjx_V9I'),
      (NOW(), NOW(), '1988-11-28', 'jane.doe@hotmail.com', 'jenny', 'jane', '2f67ebd4265f589a643b72a4e10dac344bcd3b7d695b9ab31f0e829f6fb6718d', 'K-3l?7?ZrTRv'),
      (NOW(), NOW(), '1975-06-12', 'klaus.rybski@hotmail.com', 'krybski', 'klaus rybski', 'bd8bd63aabb36e774683e6ca07c5d0b680affb3d8e7038cddb69fd5d1ee1d043','AP37UNu1LF#/'),
      (NOW(), NOW(), '1998-08-30', 'gabriel.marinho@gmail.com', 'marinho', 'gabriel marinho', 'dde282423d45825c0847164c7af7e6e8aa176adab5afbcc112c2a916761f2881','LVG8a*1oh2G)');
