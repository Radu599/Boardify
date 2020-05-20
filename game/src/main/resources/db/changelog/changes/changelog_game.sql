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


INSERT INTO `game` (`ID`, `name`, `minimum_number_of_players`, `maximum_number_of_players`, `suggested_age`, `average_playing_time`, `description`, `image_link`)
VALUES (1, "COSMIC ENCOUNTER", 3, 5, 7, 60, ". COSMIC ENCOUNTER is a brilliant mix of genuine strategy and unpredictable hilarity", "https://cf.geekdo-images.com/opengraph/img/aSPoR14lqt1IWmeuSzgSpK6IQL8=/fit-in/1200x630/pic4243113.jpg");
INSERT INTO `game` (`ID`, `name`, `minimum_number_of_players`, `maximum_number_of_players`, `suggested_age`, `average_playing_time`, `description`, `image_link`)
VALUES (2, "SPLENDOR", 2, 4, 10, 40, "For a small and light board game that contains enough strategy to play over and over, while also not being intimidating to new players, Splendor is the ideal option. ", "https://cf.geekdo-images.com/opengraph/img/9ZQ1bKSqxPFhRn9MF01Wwf-wgPA=/fit-in/1200x630/pic1904079.jpg");
INSERT INTO `game` (`ID`, `name`, `minimum_number_of_players`, `maximum_number_of_players`, `suggested_age`, `average_playing_time`, `description`, `image_link`)
VALUES (3, "PANDEMIC LEGACY: SEASON 1", 2, 4, 13, 75, "Pandemic is a game of trying to stop diseases outbreaking all over the Earth. ", "https://images-na.ssl-images-amazon.com/images/I/61RUJVWvf9L._AC_.jpg");
INSERT INTO `game` (`ID`, `name`, `minimum_number_of_players`, `maximum_number_of_players`, `suggested_age`, `average_playing_time`, `description`, `image_link`)
VALUES (4, "QUACKS OF QUEDLINBURG", 2, 4, 10, 50, "Though its name may be needlessly complex, Quacks (as we''''ve taken to calling it) is easy to teach and simple to play: most of the game involves reaching into a bag of tokens and then revelling in the agony or the ecstasy of what you’ve drawn.", "https://cf.geekdo-images.com/opengraph/img/TZVmfChYRrrBESKa3z9oMBlggRk=/fit-in/1200x630/pic4474567.jpg");
INSERT INTO `game` (`ID`, `name`, `minimum_number_of_players`, `maximum_number_of_players`, `suggested_age`, `average_playing_time`, `description`, `image_link`)
VALUES (5, "SURVIVE: ESCAPE FROM ATLANTIS", 2, 4, 8, 50, "The idea of the game is that you all control a group of inhabitants of the island of Atlantis, which is in the process of sinking in the water. ", "https://cf.geekdo-images.com/opengraph/img/yqc6wsSENdTGcbWj_Fwx9gxdJ1s=/fit-in/1200x630/pic1300182.png");
INSERT INTO `game` (`ID`, `name`, `minimum_number_of_players`, `maximum_number_of_players`, `suggested_age`, `average_playing_time`, `description`, `image_link`)
VALUES (6, "Gloomhaven (2017)", 1, 4, 12, 90, "Gloomhaven is a game of Euro-inspired tactical combat in a persistent world of shifting motives. Players will take on the role of a wandering adventurer with their own special set of skills and their own reasons for traveling to this dark corner of the world.", "https://cf.geekdo-images.com/itemrep/img/P7MVqNuhAl8Y4fxiM6e74kMX6e0=/fit-in/246x300/pic2437871.jpg");
INSERT INTO `game` (`ID`, `name`, `minimum_number_of_players`, `maximum_number_of_players`, `suggested_age`, `average_playing_time`, `description`, `image_link`)VALUES (7, "Terraforming Mars (2016)",1,5,12,120,"In the 2400s, mankind begins to terraform the planet Mars. Giant corporations, sponsored by the World Government on Earth, initiate huge projects to raise the temperature, the oxygen level, and the ocean coverage until the environment is habitable.","https://cf.geekdo-images.com/itemrep/img/bhemoxL7PG1a_79L0D9syPTADSY=/fit-in/246x300/pic3536616.jpg");
INSERT INTO `game` (`ID`, `name`, `minimum_number_of_players`, `maximum_number_of_players`, `suggested_age`, `average_playing_time`, `description`, `image_link`)VALUES (8, "Brass: Birmingham (2018)",2,4,14,90,"Brass: Birmingham is an economic strategy game sequel to Martin Wallace' 2007 masterpiece, Brass. Birmingham tells the story of competing entrepreneurs in Birmingham during the industrial revolution, between the years of 1770-1870.","https://cf.geekdo-images.com/itemrep/img/_U34-eDDAf5K8Ftc9JDAi9vr-dE=/fit-in/246x300/pic3490053.jpg");
INSERT INTO `game` (`ID`, `name`, `minimum_number_of_players`, `maximum_number_of_players`, `suggested_age`, `average_playing_time`, `description`, `image_link`)VALUES (9, "Through the Ages: A New Story of Civilization (2015)",2,4,14,120,"Through the Ages: A New Story of Civilization is the new edition of Through the Ages: A Story of Civilization, with many changes small and large to the game's cards over its three ages and extensive changes to how military works.","https://cf.geekdo-images.com/itemrep/img/32e-PrFMZ0-P_KsnZHApZazlPqc=/fit-in/246x300/pic2663291.jpg");
INSERT INTO `game` (`ID`, `name`, `minimum_number_of_players`, `maximum_number_of_players`, `suggested_age`, `average_playing_time`, `description`, `image_link`)VALUES (10, "Twilight Imperium (Fourth Edition) (2017)",3,6,14,360,"Twilight Imperium (Fourth Edition) is a game of galactic conquest in which three to six players take on the role of one of seventeen factions vying for galactic domination through military might, political maneuvering, and economic bargaining.","https://cf.geekdo-images.com/itemrep/img/UxsyZBpFiBNPOppOQ7ILJde_YhY=/fit-in/246x300/pic3727516.jpg");
INSERT INTO `game` (`ID`, `name`, `minimum_number_of_players`, `maximum_number_of_players`, `suggested_age`, `average_playing_time`, `description`, `image_link`)VALUES (11, "Twilight Struggle (2005)",2,2,13,150,"Twilight Struggle inherits its fundamental s from the card-driven classics We the People and Hannibal: Rome vs. Carthage. It is a quick-playing, low-complexity game in that tradition. ","https://cf.geekdo-images.com/itemrep/img/0_gpX_v9CeKcm60nSkDzi47PVOA=/fit-in/246x300/pic361592.jpg");


