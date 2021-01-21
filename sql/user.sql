/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.18-log 
*********************************************************************
*/
/*!40101 SET NAMES utf8 */;

create table `user` (
	`id` int (11),
	`name` varchar (765),
	`surname` varchar (765),
	`email` varchar (765),
	`password` varchar (765),
	`user_type` char (15)
); 
insert into `user` (`id`, `name`, `surname`, `email`, `password`, `user_type`) values('1','admin','admin','admin@mail.com','$2a$10$vhQLPR69qdT6/K/0HsJcNuSNpk6dGMCzbGSCyXKXBB0/iF6x.jzGu','USER');
insert into `user` (`id`, `name`, `surname`, `email`, `password`, `user_type`) values('2','poxos','poxos','poxos@mail.com','$2a$10$wqfAVjavIgQD7t5DBTrbPOTutUfi3bkQ3n3ydPnfmsvfABFOc.KyK','USER');
