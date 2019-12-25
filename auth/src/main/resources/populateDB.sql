INSERT INTO `roles` (`ID`, `TYPE`) VALUES ('1', 'admin');
INSERT INTO `roles` (`ID`, `TYPE`) VALUES ('2', 'normal');

INSERT INTO `users` (`USER_ID`, `ROLE_ID`, `PASSWORD`) VALUES ('radu@yahoo.com', '1', '');
INSERT INTO `users` (`USER_ID`, `ROLE_ID`, `PASSWORD`) VALUES ('a@a.com', '2', 'a');
INSERT INTO `users` (`USER_ID`, `ROLE_ID`, `PASSWORD`) VALUES ('b@b.com', '2', 'b');
INSERT INTO `users` (`USER_ID`, `ROLE_ID`, `PASSWORD`) VALUES ('c@c.com', '2', 'c');
