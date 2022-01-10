CREATE TABLE APPLICATION_USER
(
    ID       SERIAL PRIMARY KEY,
    USERNAME VARCHAR(15) UNIQUE NOT NULL,
    PASSWORD VARCHAR(60)        NOT NULL,
    ROLE     VARCHAR(30)        NOT NULL,
    STATUS   VARCHAR(10)        NOT NULL
);