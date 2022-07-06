
/*DROP TABLE IF EXISTS USER_RECORD;
CREATE TABLE USER_RECORD (
                             id   LONG NOT NULL AUTO_INCREMENT,
                             firstname VARCHAR(128) NOT NULL,
                             lastname VARCHAR(128) NOT NULL,
                             email VARCHAR(128) NOT NULL,
                             password VARCHAR(128) NOT NULL,
                             admin BOOLEAN,
                             dob TIMESTAMP,
                             phone VARCHAR(128),
                             address VARCHAR(128),
                             cart_id LONG,
                             account_id LONG,
                             PRIMARY KEY (id)
);

INSERT INTO USER_RECORD (firstname, lastname, email, password, admin)
VALUES ('admin', 'Jack', 'smith@gmail.com', 'admin', true),
       ('Jack', 'Smith', 'jsmith@gmail.com', 'pass', false),
       ('Alice', 'Rose', 'arose@gmail.com', 'pass', false),
       ('Mark', 'Spencer', 'mspencer@gmail.com', 'pass', false);

*/
DROP TABLE IF EXISTS MEDICINE;
CREATE TABLE MEDICINE (
                          id   LONG NOT NULL AUTO_INCREMENT,
                          name VARCHAR(128) NOT NULL UNIQUE,
                          company VARCHAR(128) ,
                          price DOUBLE ,
                          quantity LONG ,
                          uses VARCHAR,
                          disease VARCHAR,
                          expire TIMESTAMP,
                          discount INTEGER,
                          PRIMARY KEY (id)
);

INSERT INTO MEDICINE (name, company, price, quantity, uses, disease, expire, discount)
VALUES ('ibuprophen', 'company', 2.50, 10, 'pain', 'disaese', CURRENT_TIMESTAMP(), 0),
       ('paracetamol', 'company', 5, 20, 'ache', 'pepe', CURRENT_TIMESTAMP(), 10),
       ('aspi', 'company', 1, 10, 'pain', 'disaese', CURRENT_TIMESTAMP(), null);


DROP TABLE IF EXISTS ITEM;
CREATE TABLE ITEM (
                          id   LONG NOT NULL AUTO_INCREMENT,
                          medicine_id LONG,
                          quantity INTEGER NOT NULL ,
                          cart_id LONG,
                          PRIMARY KEY (id)
);

INSERT INTO ITEM (medicine_id, quantity, cart_id)
VALUES (2, 2, 1),
       (2, 3, 1),
       (2, 4, 1);

DROP TABLE IF EXISTS CART;
CREATE TABLE CART (
                      id   LONG NOT NULL AUTO_INCREMENT,
                      user_id LONG,
                      status VARCHAR,
                      PRIMARY KEY (id)
);

//INSERT INTO CART (items, user_id) VALUES ((SELECT * FROM ITEM), 2);

DROP TABLE IF EXISTS BANK;
CREATE TABLE BANK (
                      id   LONG NOT NULL AUTO_INCREMENT,
                      account_number VARCHAR(128),
                      funds DOUBLE,
                      user_id LONG,
                      PRIMARY KEY (id)
);

INSERT INTO BANK (account_number, funds, user_id) VALUES ('23456789', 500, 3);

CREATE TABLE ROLES (
                    id INTEGER NOT NULL AUTO_INCREMENT,
                    name VARCHAR(128) UNIQUE ,
                    PRIMARY KEY (id)
);

INSERT INTO ROLES(name) VALUES('ADMIN');
INSERT INTO ROLES(name) VALUES('USER');

