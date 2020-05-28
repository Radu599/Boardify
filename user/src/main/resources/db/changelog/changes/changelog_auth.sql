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
