/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.18-log 
*********************************************************************
*/
/*!40101 SET NAMES utf8 */;

create table `last_login` (
	`id` int (11),
	`city` varchar (765),
	`country_code` varchar (765),
	`country_name` varchar (765),
	`ip` varchar (765),
	`region_code` varchar (765),
	`region_name` varchar (765),
	`user_id` int (11),
	`login_date` timestamp 
); 
insert into `last_login` (`id`, `city`, `country_code`, `country_name`, `ip`, `region_code`, `region_name`, `user_id`, `login_date`) values('5','Yerevan','Asia','Armenia','141.136.89.22','ER','Yerevan','1','2021-01-26 22:01:18');
insert into `last_login` (`id`, `city`, `country_code`, `country_name`, `ip`, `region_code`, `region_name`, `user_id`, `login_date`) values('6','qwer','qwer','qwer','141.142.11.22','qwer','qwer','2','2021-01-26 22:17:59');
