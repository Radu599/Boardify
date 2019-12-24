-- changelog number: 1
-- Antinie Radu creates table 'ROLES'
CREATE TABLE IF NOT EXISTS ROLES (
  ID   INT(10) PRIMARY KEY AUTO_INCREMENT,
  TYPE varchar(40)
);

-- changelog number: 2
-- Antinie Radu creates table 'USERS'
CREATE TABLE IF NOT EXISTS USERS (
  USER_ID  VARCHAR(50) PRIMARY KEY,
  ROLE_ID  INT(255)     NOT NULL,
  PASSWORD VARCHAR(255) NOT NULL,
  FOREIGN KEY (ROLE_ID) REFERENCES ROLES (ID)
);