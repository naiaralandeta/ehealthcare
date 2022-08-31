
CREATE TABLE ROLES (
                       id INTEGER NOT NULL AUTO_INCREMENT,
                       name VARCHAR(128) UNIQUE ,
                       PRIMARY KEY (id)
);

INSERT INTO ROLES(name) VALUES('ADMIN');
INSERT INTO ROLES(name) VALUES('USER');

CREATE TABLE ORDER_STATUS (
                              id INTEGER NOT NULL AUTO_INCREMENT,
                              status VARCHAR(128) UNIQUE ,
                              PRIMARY KEY (id)
);

INSERT INTO ORDER_STATUS(status) VALUES('PENDING');
INSERT INTO ORDER_STATUS(status) VALUES('ORDERED');
INSERT INTO ORDER_STATUS(status) VALUES('DISPATCHING');
INSERT INTO ORDER_STATUS(status) VALUES('SHIPPED');
INSERT INTO ORDER_STATUS(status) VALUES('OUT_FOR_DELIVERY');
INSERT INTO ORDER_STATUS(status) VALUES('DELIVERED');

DROP TABLE IF EXISTS USER_RECORD;
CREATE TABLE USER_RECORD (
                             id   LONG NOT NULL AUTO_INCREMENT,
                             username VARCHAR(128) NOT NULL,
                             firstname VARCHAR(128) NOT NULL,
                             lastname VARCHAR(128) NOT NULL,
                             email VARCHAR(128) NOT NULL,
                             password VARCHAR(128) NOT NULL,
                             dob TIMESTAMP,
                             phone VARCHAR(128),
                             address VARCHAR(128),
                             PRIMARY KEY (id)
);

INSERT INTO USER_RECORD (username, firstname, lastname, email, password, dob, phone, address)
VALUES ('admin', 'Jack', 'Smith', 'smith@gmail.com', 'admin', '1967-01-23', '5555', 'Sunflower Road'),
       ('pmurphy', 'Peter', 'Murphy', 'petermurphy@gmail.com', 'peter', '1989-11-04', '5556', 'Grass Road'),
       ('mgray', 'Marian', 'Gray', 'mariangray@gmail.com', 'marian', '2000-05-17', '5557', 'Rose Road');


DROP TABLE IF EXISTS USER_ROLES;
CREATE TABLE USER_ROLES (
                            user_id LONG NOT NULL UNIQUE,
                            role_id LONG NOT NULL
);

INSERT INTO USER_ROLES (user_id, role_id)
VALUES (1, 1),
       (2, 2),
       (3, 2)
;

DROP TABLE IF EXISTS BANK;
CREATE TABLE BANK (
                      id LONG NOT NULL AUTO_INCREMENT,
                      account_number VARCHAR(128) NOT NULL UNIQUE,
                      funds DOUBLE NOT NULL,
                      user_id LONG NOT NULL UNIQUE,
                      PRIMARY KEY (id)
);

INSERT INTO BANK (account_number, funds, user_id)
VALUES ('AIO45680', 1000, 2),
       ('EOI57424', 1000, 3)
;

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
                          inserted TIMESTAMP,
                          PRIMARY KEY (id)
);

INSERT INTO MEDICINE (name, company, price, quantity, uses, disease, expire, discount, inserted)
VALUES ('Ibuprofen', 'NSAID', 4.50, 30, 'reduce fever and to relieve minor aches and pain from headaches, muscle aches, arthritis, menstrual periods, the common cold, toothaches, and backaches', 'headache, dental pain, menstrual cramps, muscle aches, or arthritis', '2021-07-20', 0, '2021-07-16'),
       ('Pandol', 'Pfizer', 3.15, 20, 'headaches, menstrual periods, toothaches, backaches, osteoarthritis, or cold/flu aches and pains', 'headaches, menstrual periods, toothaches, backaches, osteoarthritis, or cold/flu aches and pains', '2022-01-24', 10, '2021-07-20'),
       ('Advil', 'Bayer', 4.20, 50, 'reduce fever and relieve mild to moderate pain from conditions such as muscle aches, toothaches, common cold, and headaches', 'cold and flu', '2025-08-12', 0, '2021-07-01'),
       ('Insulin glulisine', 'Apidra', 8, 10, 'Control blood sugar', 'diabetes', '2027-09-14', 0, CURRENT_TIMESTAMP()),
       ('Insulin lispro U-100/U-200', 'Humalog', 7.5, 30, 'Control blood sugar', 'diabetes', '2026-02-02', 0, CURRENT_TIMESTAMP())
;

DROP TABLE IF EXISTS MEDICINE_HISTORY;
CREATE TABLE MEDICINE_HISTORY (
                                  id   LONG NOT NULL AUTO_INCREMENT,
                                  medicine_id LONG NOT NULL UNIQUE,
                                  name VARCHAR(128) NOT NULL UNIQUE,
                                  company VARCHAR(128) ,
                                  price DOUBLE ,
                                  quantity LONG ,
                                  uses VARCHAR,
                                  disease VARCHAR,
                                  expire TIMESTAMP,
                                  discount INTEGER,
                                  inserted TIMESTAMP,
                                  PRIMARY KEY (id)
);

INSERT INTO MEDICINE_HISTORY (medicine_id, name, company, price, quantity, uses, disease, expire, discount, inserted)
VALUES (1, 'Ibuprofen', 'Moderna', 4.50, 30, 'reduce fever and to relieve minor aches and pain from headaches, muscle aches, arthritis, menstrual periods, the common cold, toothaches, and backaches', 'headache, dental pain, menstrual cramps, muscle aches, or arthritis', '2021-07-20', 0, '2021-07-16'),
       (2, 'Pandol', 'Pfizer', 3.15, 20, 'headaches, menstrual periods, toothaches, backaches, osteoarthritis, or cold/flu aches and pains', 'headaches, menstrual periods, toothaches, backaches, osteoarthritis, or cold/flu aches and pains', '2022-01-24', 10, '2021-07-16'),
       (3, 'Advil', 'Bayer', 4.20, 50, 'reduce fever and relieve mild to moderate pain from conditions such as muscle aches, toothaches, common cold, and headaches', 'cold and flu', '2025-08-12', 0, '2021-07-16'),
       (4, 'Insulin glulisine', 'Apidra', 8, 10, 'Control blood sugar', 'diabetes', '2027-09-14', 0, CURRENT_TIMESTAMP()),
       (5, 'Insulin lispro U-100/U-200', 'Humalog', 7.5, 30, 'Control blood sugar', 'diabetes', '2026-02-02', 0, CURRENT_TIMESTAMP())
;

DROP TABLE IF EXISTS CART;
CREATE TABLE CART (
                      id LONG NOT NULL AUTO_INCREMENT,
                      owner LONG,
                      medname VARCHAR,
                      quantity INT,
                      price DOUBLE,
                      discount INT,
                      status VARCHAR(128),
                      total DOUBLE,
                      date TIMESTAMP,
                      PRIMARY KEY (id)
);

INSERT INTO CART (owner, medname, quantity, price, discount, status, total, date)
VALUES (3, 'Ibuprofen',4, 30, 0, 1, 10, '2021-07-16'),
       (3, 'Pandol', 3, 3.15, 10, 0, 8.5, '2021-07-16')
;