-- changelog number: 1
-- Antinie Radu creates table 'GAME_GROUPS'
CREATE TABLE IF NOT EXISTS GAME_GROUPS (
    ID INT(10) PRIMARY KEY AUTO_INCREMENT,
    GAME_ID INT
);

-- changelog number: 2
-- Antinie Radu creates table 'GROUP_MEMBERRS'
CREATE TABLE IF NOT EXISTS GROUP_MEMBERS (
  USER_EMAIL VARCHAR(50),
  GAME_GROUP_ID INT,
  PRIMARY KEY (USER_EMAIL, GAME_GROUP_ID)
);

-- changelog number: 3
-- Antinie Radu creates table 'STATS'
CREATE TABLE IF NOT EXISTS STATS (
    group_id INT,
    email VARCHAR(50),
    last_message long,
    message_count int,
    PRIMARY KEY (group_id, email)
)
