INSERT INTO `roles` (`ID`, `TYPE`) VALUES ('1', 'admin');
INSERT INTO `roles` (`ID`, `TYPE`) VALUES ('2', 'normal');

INSERT INTO `users` (`USER_ID`, `ROLE_ID`, `LOCATION`, `PASSWORD`) VALUES ('radu@yahoo.com', '1','Romania,Sibiu', 'radu');
INSERT INTO `users` (`USER_ID`, `ROLE_ID`, `LOCATION`, `PASSWORD`) VALUES ('a@a.com', '2', 'France,Paris','a');
INSERT INTO `users` (`USER_ID`, `ROLE_ID`, `LOCATION`, `PASSWORD`) VALUES ('b@b.com', '2', 'Romania,Cluj-Napoca','b');
INSERT INTO `users` (`USER_ID`, `ROLE_ID`, `LOCATION`, `PASSWORD`) VALUES ('c@c.com', '2', 'Romania,Bucuresti','c');