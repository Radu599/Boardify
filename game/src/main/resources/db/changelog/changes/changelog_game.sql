-- changelog number: 3
-- Antinie Radu creates table 'GAME'
show variables like 'lower_case_table_names';
CREATE TABLE IF NOT EXISTS game (
  ID   INT(10) PRIMARY KEY AUTO_INCREMENT,
  name varchar(5000),
  minimum_number_of_players int,
  maximum_number_of_players int,
  suggested_age int,
  average_playing_time int,
  description varchar(10000),
  image_link varchar(10000)
);
