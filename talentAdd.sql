SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS m_farmhouse_daily;
DROP TABLE IF EXISTS m_farmhouse_month;
DROP TABLE IF EXISTS t_user_feedback;




/* Create Tables */

CREATE TABLE m_farmhouse_daily
(
	fh_d bigint NOT NULL AUTO_INCREMENT,
	dailyFarmHouseID char(255) NOT NULL,
	farmhouse_id bigint NOT NULL,
	name char(16) NOT NULL,
	daily_time date NOT NULL,
	number bigint,
	times bigint,
	PRIMARY KEY (fh_d),
	UNIQUE (fh_d)
);


CREATE TABLE m_farmhouse_month
(
	fh_m bigint NOT NULL AUTO_INCREMENT,
	monthFarmhouseID char(255),
	farmhouse_id bigint NOT NULL,
	name char(16) NOT NULL,
	month date NOT NULL,
	number bigint,
	times bigint,
	PRIMARY KEY (fh_m),
	UNIQUE (fh_m)
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



