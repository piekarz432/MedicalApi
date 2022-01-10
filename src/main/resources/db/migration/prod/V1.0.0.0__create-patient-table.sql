CREATE TABLE PATIENT
(
    ID                       SERIAL PRIMARY KEY,
    NAME                     VARCHAR(25)        NOT NULL,
    SURNAME                  VARCHAR(25)        NOT NULL,
    GENDER                   CHAR(1)            NOT NULL,
    DATE                     DATE               NOT NULL,
    PERSONAL_IDENTITY_NUMBER VARCHAR(11) UNIQUE NOT NULL
);

