DROP TABLE IF EXISTS USER_CREDENTIALS;

CREATE TABLE `USER_CREDENTIALS` (
  `CREDS_ID` INT NOT NULL AUTO_INCREMENT,
  `USER_NAME` VARCHAR(20) NOT NULL,
  `PASSWORD` VARCHAR(45) NOT NULL,
   UNIQUE KEY `USERNAME_UNIQUE` (`USER_NAME`),
   PRIMARY KEY (`CREDS_ID`)
);

DROP TABLE IF EXISTS USER;

CREATE TABLE `USER` (
    `ID` int(11) NOT NULL AUTO_INCREMENT,
    `CREATED_TIME` datetime NOT NULL,
    `UPDATED_TIME` datetime DEFAULT NULL,
    `USER_TYPE` varchar(45) NOT NULL,
    `DOB` date NOT NULL,
    `CREDS_ID` NULL,
    PRIMARY KEY (`ID`),
    CONSTRAINT `CREDS_ID_FK` 
    FOREIGN KEY (`CREDS_ID`) REFERENCES `USER_CREDENTIALS` (`CREDS_ID`)
);

-- Example 2

DROP TABLE IF EXISTS MOVIE;

CREATE TABLE MOVIE (
    ID SERIAL PRIMARY KEY,
    TITLE TEXT,
    DESCRIPTION TEXT
);

DROP TABLE IF EXISTS RENTAL;

CREATE TABLE RENTAL (
    MOVIE INTEGER PRIMARY KEY REFERENCES MOVIE(ID),
    DURATION INTEGER,
    PRICE INTEGER
);

