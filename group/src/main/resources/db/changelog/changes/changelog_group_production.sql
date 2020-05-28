-- changelog number: 1
-- Antinie Radu creates table 'GAME_GROUPS'
show variables like 'lower_case_table_names';
CREATE TABLE IF NOT EXISTS game_groups (
    ID INT(10) PRIMARY KEY AUTO_INCREMENT,
    GAME_ID INT
);

-- changelog number: 2
-- Antinie Radu creates table 'GROUP_MEMBERRS'
CREATE TABLE IF NOT EXISTS group_members (
  USER_EMAIL VARCHAR(50),
  GAME_GROUP_ID INT,
  PRIMARY KEY (USER_EMAIL, GAME_GROUP_ID)
);

-- changelog number: 3
-- Antinie Radu creates table 'STATS'
CREATE TABLE IF NOT EXISTS stats (
    group_id INT,
    email VARCHAR(50),
    last_message long,
    message_count int,
    PRIMARY KEY (group_id, email)
);

INSERT INTO `game_groups` (`ID`, `GAME_ID`) VALUES ('1', '1');


INSERT INTO `group_members` (`USER_EMAIL`, `GAME_GROUP_ID`) VALUES ('a@a.com', '1');
INSERT INTO `group_members` (`USER_EMAIL`, `GAME_GROUP_ID`) VALUES ('b@b.com', '1');
