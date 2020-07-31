SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS t_opweb_record;
DROP TABLE IF EXISTS t_talent_un_confirm_send;




/* Create Tables */

CREATE TABLE t_opweb_record
(
	op_id bigint NOT NULL AUTO_INCREMENT,
	use_id bigint NOT NULL,
	username char(32) NOT NULL,
	frist_menu char(32) NOT NULL,
	second_menu char(32) NOT NULL,
	third_menu char(32),
	detail_info char(255),
	create_time datetime,
	PRIMARY KEY (op_id),
	UNIQUE (op_id)
);


CREATE TABLE t_talent_un_confirm_send
(
	t_tsend_id bigint NOT NULL AUTO_INCREMENT,
	talent_id bigint NOT NULL,
	open_id char(64) NOT NULL,
	-- 1:已发
	-- 2:未发
	status tinyint unsigned COMMENT '1:已发
2:未发',
	create_time datetime,
	update_time datetime,
	PRIMARY KEY (t_tsend_id),
	UNIQUE (talent_id),
	UNIQUE (open_id)
);



