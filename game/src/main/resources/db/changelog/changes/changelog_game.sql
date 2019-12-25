-- changelog number: 1
-- Antinie Radu creates table 'ROLES'
CREATE TABLE IF NOT EXISTS GAME (
  ID   INT(10) PRIMARY KEY AUTO_INCREMENT,
  name varchar(40),
  minimum_number_of_players int,
  maximum_number_of_players int,
  suggested_age int,
  average_playing_time int,
  description varchar(100)
);