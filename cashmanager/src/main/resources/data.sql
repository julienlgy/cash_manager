-- INITIALISATION DE LA TABLE USER
INSERT INTO UTILISATEUR(USER_ID, USER_NAME, USER_PASSWORD) values (1, 'admin', 'ABCD');
INSERT INTO UTILISATEUR(USER_ID, USER_NAME, USER_PASSWORD) values (2, 'user', 'RERER');
INSERT INTO UTILISATEUR(USER_ID, USER_NAME, USER_PASSWORD) values (3, 'user1', 'FFFF');

-- INITIALISATION DE LA TABLE ARTICLE
INSERT INTO ARTICLE(ARTICLE_ID, ARTICLE_NAME, DESCRIPTION, IMAGE, PRIX) values (1,'basket','nike','photo.png',100);
INSERT INTO ARTICLE(ARTICLE_ID, ARTICLE_NAME, DESCRIPTION, IMAGE, PRIX) values (2,'chaussure','zanotti','photo2.png',200);

-- INITIALISATION DE LA TABLE PANIER
INSERT INTO PANIER(PANIER_ID, IS_PAID) values (1,'false');
INSERT INTO PANIER(PANIER_ID, IS_PAID) values (2,'false');

--INITIALISATION DE LA TABLE PAIMENT
INSERT INTO PAIMENT(PAIMENT_ID, TYPE_PAIMENT, TOTAL) values (1,'carte bancaire',100);
INSERT INTO PAIMENT(PAIMENT_ID, TYPE_PAIMENT, TOTAL) values (2,'cheque',200);
INSERT INTO PAIMENT(PAIMENT_ID, TYPE_PAIMENT, TOTAL) values (3,'carte bancaire',300);
INSERT INTO PAIMENT(PAIMENT_ID, TYPE_PAIMENT, TOTAL) values (4,'cheque',400);--inactif user