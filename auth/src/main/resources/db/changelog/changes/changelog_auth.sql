-- changelog number: 1
-- Antinie Radu creates table 'ROLES'
show variables like 'lower_case_table_names';
CREATE TABLE IF NOT EXISTS roles (
  ID   INT(10) PRIMARY KEY AUTO_INCREMENT,
  TYPE varchar(40)
);

-- changelog number: 2
-- Antinie Radu creates table 'USERS'
CREATE TABLE IF NOT EXISTS users (
  USER_ID  VARCHAR(50) PRIMARY KEY,
  ROLE_ID  INT(255)     NOT NULL,
  PASSWORD VARCHAR(255) NOT NULL,
  LOCATION VARCHAR(255),
  AVATAR_PATH VARCHAR(300),
  FOREIGN KEY (ROLE_ID) REFERENCES roles (ID)
);

INSERT INTO `roles` (`ID`, `TYPE`) VALUES ('1', 'admin');
INSERT INTO `roles` (`ID`, `TYPE`) VALUES ('2', 'normal');

INSERT INTO `users` (`USER_ID`, `ROLE_ID`, `LOCATION`, `PASSWORD`) VALUES ('radu@yahoo.com', '1','Romania,Sibiu', 'radu');
INSERT INTO `users` (`USER_ID`, `ROLE_ID`, `LOCATION`, `PASSWORD`) VALUES ('a@a.com', '2', 'France,Paris','a');
INSERT INTO `users` (`USER_ID`, `ROLE_ID`, `LOCATION`, `PASSWORD`) VALUES ('b@b.com', '2', 'Romania,Cluj-Napoca','b');
INSERT INTO `users` (`USER_ID`, `ROLE_ID`, `LOCATION`, `PASSWORD`) VALUES ('c@c.com', '2', 'Romania,Bucuresti','c');
