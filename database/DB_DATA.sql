/*
    $Id: sample.sql,v 1.5 2005/05/02 15:07:27 unsaved Exp $
    Examplifies use of SqlTool.
    PCTASK Table creation
*/

/* Ignore error for these two statements */
\c true
DROP TABLE COMPTE;
DROP TABLE CLIENT;
\c false

\p Creating table CLIENT
CREATE TABLE CLIENT (
    ID integer identity PRIMARY KEY ,
    NOM varchar(40) not null,
    PRENOM varchar(40) not null,
);

\p Creating table COMPTE
CREATE TABLE COMPTE (
    ID integer identity PRIMARY KEY ,
    NUMERO int not null,
    SOLDE integer,
	FK_CLIENT integer,
	DESCRIPTION varchar(40),
    FOREIGN KEY (FK_CLIENT) REFERENCES CLIENT,
);

\p Inserting test records
INSERT INTO CLIENT (ID, NOM, PRENOM) VALUES (
    '1', 'Zidane', 'Zinedine' );
	
INSERT INTO CLIENT (ID, NOM, PRENOM) VALUES (
    '2', 'Platini', 'Michel' );
INSERT INTO CLIENT (ID, NOM, PRENOM) VALUES (
    '3', 'Papin', 'Jean-Pierre' );
	
INSERT INTO COMPTE (ID, NUMERO, SOLDE, DESCRIPTION, FK_CLIENT) VALUES (
    '5', 1000,10000,'Compte Courant',1 );

INSERT INTO COMPTE (ID, NUMERO, SOLDE, DESCRIPTION,  FK_CLIENT) VALUES (
    '1', 1001,20000,'Compte Courant',2 );

INSERT INTO COMPTE (ID, NUMERO, SOLDE, DESCRIPTION, FK_CLIENT) VALUES (
    '2', 1002,30000,'Compte Courant',3 );

INSERT INTO COMPTE (ID, NUMERO, SOLDE, DESCRIPTION, FK_CLIENT) VALUES (
    '3', 1003,1000,'livret A',2 );
	
	
commit;
