-- changelog number: 1
-- Antinie Radu creates table 'GAME_GROUPS'
CREATE TABLE IF NOT EXISTS GAME_GROUPS (
    ID INT(10) PRIMARY KEY AUTO_INCREMENT,
    GAME_ID INT
);

-- changelog number: 2
-- Antinie Radu creates table 'GROUP_MEMBERRS'
CREATE TABLE IF NOT EXISTS GROUP_MEMBERRS (
  USER_EMAIL VARCHAR(50),
  GAME_GROUP_ID INT
);