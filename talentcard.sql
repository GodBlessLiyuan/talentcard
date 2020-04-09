SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS t_annex;
DROP TABLE IF EXISTS t_role_authority;
DROP TABLE IF EXISTS t_authority;
DROP TABLE IF EXISTS t_bank;
DROP TABLE IF EXISTS t_cert_approval;
DROP TABLE IF EXISTS t_user_card;
DROP TABLE IF EXISTS t_card;
DROP TABLE IF EXISTS t_certification;
DROP TABLE IF EXISTS t_education;
DROP TABLE IF EXISTS t_policy_approval;
DROP TABLE IF EXISTS t_policy_apply;
DROP TABLE IF EXISTS t_policy;
DROP TABLE IF EXISTS t_prof_quality;
DROP TABLE IF EXISTS t_prof_title;
DROP TABLE IF EXISTS t_user;
DROP TABLE IF EXISTS t_role;
DROP TABLE IF EXISTS t_talent;




/* Create Tables */

CREATE TABLE t_annex
(
	annex_id bigint unsigned NOT NULL AUTO_INCREMENT,
	name char(128),
	location char(255),
	pa_id bigint unsigned NOT NULL,
	PRIMARY KEY (annex_id),
	UNIQUE (annex_id)
);


-- È¨ÏÞ±í
CREATE TABLE t_authority
(
	authority_id bigint unsigned NOT NULL AUTO_INCREMENT,
	authority_name char(32),
	PRIMARY KEY (authority_id),
	UNIQUE (authority_id)
) COMMENT = 'È¨ÏÞ±í';


CREATE TABLE t_bank
(
	bank_id bigint unsigned NOT NULL AUTO_INCREMENT,
	num char(32) NOT NULL,
	name char(32) NOT NULL,
	pa_id bigint unsigned NOT NULL,
	PRIMARY KEY (bank_id),
	UNIQUE (bank_id),
	UNIQUE (num),
	UNIQUE (pa_id)
);


CREATE TABLE t_card
(
	card_id bigint unsigned NOT NULL AUTO_INCREMENT,
	title char(32) NOT NULL,
	name char(16) NOT NULL,
	initial_num char(32) NOT NULL,
	curr_num bigint DEFAULT 0,
	description char(255) NOT NULL,
	picture char(255) NOT NULL,
	create_time datetime,
	-- 1£ºÄ¬ÈÏ£»2£º·ÇÄ¬ÈÏ
	status tinyint COMMENT '1£ºÄ¬ÈÏ£»2£º·ÇÄ¬ÈÏ',
	PRIMARY KEY (card_id),
	UNIQUE (card_id)
);


CREATE TABLE t_certification
(
	cert_id bigint unsigned NOT NULL AUTO_INCREMENT,
	talent_id bigint unsigned,
	educ_id bigint unsigned,
	pq_id bigint unsigned,
	pq_id bigint unsigned,
	political char(128) NOT NULL,
	create_time datetime,
	-- 1£ºÒÑÍ¬Òâ£»2£ºÒÑ²µ»Ø£»3£º´ýÉóÅú
	status tinyint DEFAULT 3 COMMENT '1£ºÒÑÍ¬Òâ£»2£ºÒÑ²µ»Ø£»3£º´ýÉóÅú',
	PRIMARY KEY (cert_id),
	UNIQUE (cert_id)
);


CREATE TABLE t_cert_approval
(
	approval_id bigint unsigned NOT NULL AUTO_INCREMENT,
	cert_id bigint unsigned NOT NULL,
	create_time datetime,
	type char(32),
	user_id bigint unsigned NOT NULL,
	card_id bigint unsigned NOT NULL,
	category char(255),
	result char(255),
	opinion char(255),
	PRIMARY KEY (approval_id),
	UNIQUE (approval_id)
);


CREATE TABLE t_education
(
	educ_id bigint unsigned NOT NULL AUTO_INCREMENT,
	talent_id bigint unsigned NOT NULL,
	eduction char(32),
	school char(255),
	-- 1£ºÊÇ£»2£º·ñ
	frist_class tinyint COMMENT '1£ºÊÇ£»2£º·ñ',
	major char(255),
	educ_picture char(255),
	PRIMARY KEY (educ_id),
	UNIQUE (educ_id)
);


CREATE TABLE t_policy
(
	policy_id bigint unsigned NOT NULL AUTO_INCREMENT,
	name char(32) NOT NULL,
	num char(32) NOT NULL,
	description char(255) NOT NULL,
	cards char(255),
	categories char(255),
	educations char(255),
	-- 1£ºÐèÒª£»2£º²»ÐèÒª
	apply tinyint DEFAULT 2 COMMENT '1£ºÐèÒª£»2£º²»ÐèÒª',
	frequency char(32),
	-- 1£ºÐèÒª£»2£º²»ÐèÒª£»
	bank tinyint COMMENT '1£ºÐèÒª£»2£º²»ÐèÒª£»',
	-- 1£ºÐèÒª£»2£º²»ÐèÒª£»
	annex tinyint COMMENT '1£ºÐèÒª£»2£º²»ÐèÒª£»',
	PRIMARY KEY (policy_id),
	UNIQUE (policy_id)
);


CREATE TABLE t_policy_apply
(
	pa_id bigint unsigned NOT NULL AUTO_INCREMENT,
	talent_id bigint unsigned NOT NULL,
	name char(64) NOT NULL,
	policy_id bigint unsigned NOT NULL,
	create_time datetime,
	-- 1£ºÒÑÍ¬Òâ£»2£ºÒÑ²µ»Ø£»3£º´ýÉóÅú
	status tinyint DEFAULT 3 COMMENT '1£ºÒÑÍ¬Òâ£»2£ºÒÑ²µ»Ø£»3£º´ýÉóÅú',
	PRIMARY KEY (pa_id),
	UNIQUE (pa_id)
);


CREATE TABLE t_policy_approval
(
	approval_id bigint unsigned NOT NULL AUTO_INCREMENT,
	pa_id bigint unsigned NOT NULL,
	create_time datetime,
	type char(32),
	user_id bigint unsigned,
	result char(255),
	opinion char(255),
	PRIMARY KEY (approval_id),
	UNIQUE (approval_id),
	UNIQUE (pa_id)
);


CREATE TABLE t_prof_quality
(
	pq_id bigint unsigned NOT NULL AUTO_INCREMENT,
	talent_id bigint unsigned,
	category char(64),
	info char(255),
	picture char(255),
	PRIMARY KEY (pq_id),
	UNIQUE (pq_id)
);


CREATE TABLE t_prof_title
(
	pq_id bigint unsigned NOT NULL AUTO_INCREMENT,
	talent_id bigint unsigned,
	category char(64),
	info char(255),
	picture char(255),
	PRIMARY KEY (pq_id),
	UNIQUE (pq_id)
);


-- ½ÇÉ«±í
CREATE TABLE t_role
(
	role_id bigint unsigned NOT NULL AUTO_INCREMENT,
	role_name char(32),
	extra char(255),
	create_time date,
	PRIMARY KEY (role_id),
	UNIQUE (role_id)
) COMMENT = '½ÇÉ«±í';


CREATE TABLE t_role_authority
(
	ra_id bigint unsigned NOT NULL AUTO_INCREMENT,
	-- 0È¨ÏÞ¹Ø±Õ    1È¨ÏÞ¿ª·Å
	status tinyint(4) DEFAULT 1 COMMENT '0È¨ÏÞ¹Ø±Õ    1È¨ÏÞ¿ª·Å',
	authority_id bigint unsigned NOT NULL,
	role_id bigint unsigned NOT NULL,
	PRIMARY KEY (ra_id),
	UNIQUE (ra_id),
	UNIQUE (authority_id),
	UNIQUE (role_id)
);


CREATE TABLE t_talent
(
	talent_id bigint unsigned NOT NULL AUTO_INCREMENT,
	name char(64) NOT NULL,
	-- 1£ºÄÐ£»2£ºÅ®
	sex tinyint COMMENT '1£ºÄÐ£»2£ºÅ®',
	id_card char(18) NOT NULL,
	work_unit char(255) NOT NULL,
	industry char(255) NOT NULL,
	phone char(32) NOT NULL,
	create_time datetime,
	PRIMARY KEY (talent_id),
	UNIQUE (talent_id),
	UNIQUE (id_card)
);


-- ÈË²Å¿¨ÓÃ»§»ù±¾ÐÅÏ¢±í
CREATE TABLE t_user
(
	user_id bigint unsigned NOT NULL AUTO_INCREMENT,
	username char(32),
	name char(32),
	password char(32),
	create_time date,
	-- 0 Î´É¾³ý  1 ÒÑÉ¾³ý
	dr tinyint(4) DEFAULT 0 COMMENT '0 Î´É¾³ý  1 ÒÑÉ¾³ý',
	extra char(255),
	role_id bigint unsigned NOT NULL,
	PRIMARY KEY (user_id),
	UNIQUE (user_id),
	UNIQUE (username),
	UNIQUE (role_id)
) COMMENT = 'ÈË²Å¿¨ÓÃ»§»ù±¾ÐÅÏ¢±í';


CREATE TABLE t_user_card
(
	uc_id bigint unsigned NOT NULL AUTO_INCREMENT,
	talent_id bigint unsigned NOT NULL,
	card_id bigint unsigned,
	num char(32) NOT NULL,
	create_time datetime,
	PRIMARY KEY (uc_id),
	UNIQUE (uc_id),
	UNIQUE (talent_id),
	UNIQUE (num)
);



/* Create Foreign Keys */

ALTER TABLE t_role_authority
	ADD FOREIGN KEY (authority_id)
	REFERENCES t_authority (authority_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE t_cert_approval
	ADD FOREIGN KEY (card_id)
	REFERENCES t_card (card_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE t_user_card
	ADD FOREIGN KEY (card_id)
	REFERENCES t_card (card_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE t_cert_approval
	ADD FOREIGN KEY (cert_id)
	REFERENCES t_certification (cert_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE t_certification
	ADD FOREIGN KEY (educ_id)
	REFERENCES t_education (educ_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE t_policy_apply
	ADD FOREIGN KEY (policy_id)
	REFERENCES t_policy (policy_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE t_annex
	ADD FOREIGN KEY (pa_id)
	REFERENCES t_policy_apply (pa_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE t_bank
	ADD FOREIGN KEY (pa_id)
	REFERENCES t_policy_apply (pa_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE t_policy_approval
	ADD FOREIGN KEY (pa_id)
	REFERENCES t_policy_apply (pa_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE t_certification
	ADD FOREIGN KEY (pq_id)
	REFERENCES t_prof_quality (pq_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE t_certification
	ADD FOREIGN KEY (pq_id)
	REFERENCES t_prof_title (pq_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE t_role_authority
	ADD FOREIGN KEY (role_id)
	REFERENCES t_role (role_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE t_user
	ADD FOREIGN KEY (role_id)
	REFERENCES t_role (role_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE t_certification
	ADD FOREIGN KEY (talent_id)
	REFERENCES t_talent (talent_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE t_education
	ADD FOREIGN KEY (talent_id)
	REFERENCES t_talent (talent_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE t_policy_apply
	ADD FOREIGN KEY (talent_id)
	REFERENCES t_talent (talent_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE t_prof_quality
	ADD FOREIGN KEY (talent_id)
	REFERENCES t_talent (talent_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE t_prof_title
	ADD FOREIGN KEY (talent_id)
	REFERENCES t_talent (talent_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE t_user_card
	ADD FOREIGN KEY (talent_id)
	REFERENCES t_talent (talent_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



