SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS m_farmhouse_daily;
DROP TABLE IF EXISTS m_farmhouse_month;
DROP TABLE IF EXISTS m_trip_daily;
DROP TABLE IF EXISTS m_trip_month;
DROP TABLE IF EXISTS t_user_feedback;




/* Create Tables */

CREATE TABLE m_farmhouse_daily
(
	fh_d bigint NOT NULL AUTO_INCREMENT,
	dailyFarmHouseID char(32) NOT NULL,
	farmhouse_id bigint NOT NULL,
	name char(16) NOT NULL,
	daily_time date NOT NULL,
	number bigint,
	times bigint,
	update_time datetime,
	PRIMARY KEY (fh_d),
	UNIQUE (fh_d)
);


CREATE TABLE m_farmhouse_month
(
	fh_m bigint NOT NULL AUTO_INCREMENT,
	monthFarmhouseID char(32),
	farmhouse_id bigint NOT NULL,
	name char(16) NOT NULL,
	month date NOT NULL,
	number bigint,
	times bigint,
	update_time datetime,
	PRIMARY KEY (fh_m),
	UNIQUE (fh_m)
);


CREATE TABLE m_trip_daily
(
	td_id bigint unsigned NOT NULL AUTO_INCREMENT,
	sid_daily char(32) NOT NULL,
	sid bigint unsigned,
	daily date NOT NULL,
	scenic_name char(16) NOT NULL,
	numbers bigint unsigned,
	free_times bigint unsigned,
	discount_times bigint unsigned,
	total_times bigint unsigned,
	update_time datetime,
	PRIMARY KEY (td_id),
	UNIQUE (td_id)
);


CREATE TABLE m_trip_month
(
	tm_id bigint NOT NULL AUTO_INCREMENT,
	sid_month char(32) NOT NULL,
	sid bigint unsigned,
	month date NOT NULL,
	name char(16) NOT NULL,
	numbers bigint unsigned,
	free_times bigint unsigned,
	discount_times bigint unsigned,
	total_times bigint unsigned,
	update_time datetime,
	PRIMARY KEY (tm_id),
	UNIQUE (tm_id)
);


CREATE TABLE t_user_feedback
(
	uf_id bigint NOT NULL AUTO_INCREMENT,
	open_id char(128) NOT NULL,
	page_type tinyint NOT NULL,
	relate_item char(64) NOT NULL,
	choose_item char(64) NOT NULL,
	pro_describe char(255),
	submit_date datetime,
	PRIMARY KEY (uf_id),
	UNIQUE (uf_id)
);



