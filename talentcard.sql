SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS t_annex;
DROP TABLE IF EXISTS t_role_authority;
DROP TABLE IF EXISTS t_authority;
DROP TABLE IF EXISTS t_bank;
DROP TABLE IF EXISTS t_cert_approval;
DROP TABLE IF EXISTS t_user_card;
DROP TABLE IF EXISTS t_card;
DROP TABLE IF EXISTS t_education;
DROP TABLE IF EXISTS t_prof_quality;
DROP TABLE IF EXISTS t_prof_title;
DROP TABLE IF EXISTS t_certification;
DROP TABLE IF EXISTS t_policy_approval;
DROP TABLE IF EXISTS t_policy_apply;
DROP TABLE IF EXISTS t_policy;
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


-- Ȩ�ޱ�
CREATE TABLE t_authority
(
	authority_id bigint unsigned NOT NULL AUTO_INCREMENT,
	name char(32),
	PRIMARY KEY (authority_id),
	UNIQUE (authority_id)
) COMMENT = 'Ȩ�ޱ�';


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
	-- 1��Ĭ�ϣ�2����Ĭ��
	status tinyint COMMENT '1��Ĭ�ϣ�2����Ĭ��',
	PRIMARY KEY (card_id),
	UNIQUE (card_id)
);


CREATE TABLE t_certification
(
	cert_id bigint unsigned NOT NULL AUTO_INCREMENT,
	talent_id bigint unsigned,
	political char(128) NOT NULL,
	create_time datetime,
	-- 1����ͬ�⣻2���Ѳ��أ�3��������
	status tinyint DEFAULT 3 COMMENT '1����ͬ�⣻2���Ѳ��أ�3��������',
	PRIMARY KEY (cert_id),
	UNIQUE (cert_id)
);


CREATE TABLE t_cert_approval
(
	approval_id bigint unsigned NOT NULL AUTO_INCREMENT,
	cert_id bigint unsigned NOT NULL,
	create_time datetime,
	type char(32),
	user_id bigint unsigned,
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
	eduction int,
	school char(255),
	-- 1���ǣ�2����
	frist_class tinyint COMMENT '1���ǣ�2����',
	major char(255),
	educ_picture char(255),
	cert_id bigint unsigned NOT NULL,
	PRIMARY KEY (educ_id),
	UNIQUE (educ_id),
	UNIQUE (cert_id)
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
	-- 1����Ҫ��2������Ҫ
	apply tinyint DEFAULT 2 COMMENT '1����Ҫ��2������Ҫ',
	frequency char(32),
	-- 1����Ҫ��2������Ҫ��
	bank tinyint COMMENT '1����Ҫ��2������Ҫ��',
	-- 1����Ҫ��2������Ҫ��
	annex tinyint COMMENT '1����Ҫ��2������Ҫ��',
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
	-- 1����ͬ�⣻2���Ѳ��أ�3��������
	status tinyint DEFAULT 3 COMMENT '1����ͬ�⣻2���Ѳ��أ�3��������',
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
	category int,
	info char(255),
	picture char(255),
	cert_id bigint unsigned NOT NULL,
	PRIMARY KEY (pq_id),
	UNIQUE (pq_id),
	UNIQUE (cert_id)
);


CREATE TABLE t_prof_title
(
	pt_id bigint unsigned NOT NULL AUTO_INCREMENT,
	category int,
	info char(255),
	picture char(255),
	cert_id bigint unsigned NOT NULL,
	PRIMARY KEY (pt_id),
	UNIQUE (pt_id),
	UNIQUE (cert_id)
);


-- ��ɫ��
CREATE TABLE t_role
(
	role_id bigint unsigned NOT NULL AUTO_INCREMENT,
	name char(32),
	extra char(255),
	create_time date,
	PRIMARY KEY (role_id),
	UNIQUE (role_id)
) COMMENT = '��ɫ��';


CREATE TABLE t_role_authority
(
	ra_id bigint unsigned NOT NULL AUTO_INCREMENT,
	-- 1Ȩ�޿���; 2Ȩ�޹ر�    
	status tinyint(4) DEFAULT 1 COMMENT '1Ȩ�޿���; 2Ȩ�޹ر�    ',
	authority_id bigint unsigned NOT NULL,
	role_id bigint unsigned NOT NULL,
	PRIMARY KEY (ra_id),
	UNIQUE (ra_id)
);


CREATE TABLE t_talent
(
	talent_id bigint unsigned NOT NULL AUTO_INCREMENT,
	name char(64) NOT NULL,
	-- 1���У�2��Ů
	sex tinyint COMMENT '1���У�2��Ů',
	id_card char(18) NOT NULL,
	passport char(32),
	work_unit char(255) NOT NULL,
	industry char(255) NOT NULL,
	phone char(32) NOT NULL,
	create_time datetime,
	PRIMARY KEY (talent_id),
	UNIQUE (talent_id),
	UNIQUE (id_card)
);


-- �˲ſ��û�������Ϣ��
CREATE TABLE t_user
(
	user_id bigint unsigned NOT NULL AUTO_INCREMENT,
	username char(32),
	name char(32),
	password bigint(32),
	cread_time date,
	-- 1 δɾ��  2 ��ɾ��
	dr tinyint COMMENT '1 δɾ��  2 ��ɾ��',
	extra char(255),
	role_id bigint unsigned NOT NULL,
	PRIMARY KEY (user_id),
	UNIQUE (user_id),
	UNIQUE (username)
) COMMENT = '�˲ſ��û�������Ϣ��';


CREATE TABLE t_user_card
(
	uc_id bigint unsigned NOT NULL AUTO_INCREMENT,
	talent_id bigint unsigned NOT NULL,
	card_id bigint unsigned,
	num char(32) NOT NULL,
	create_time datetime,
	-- 1 ������
	-- 2 ������
	-- 3 ����
	status tinyint COMMENT '1 ������
2 ������
3 ����',
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


ALTER TABLE t_education
	ADD FOREIGN KEY (cert_id)
	REFERENCES t_certification (cert_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE t_prof_quality
	ADD FOREIGN KEY (cert_id)
	REFERENCES t_certification (cert_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE t_prof_title
	ADD FOREIGN KEY (cert_id)
	REFERENCES t_certification (cert_id)
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


ALTER TABLE t_policy_apply
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


ALTER TABLE t_cert_approval
	ADD FOREIGN KEY (user_id)
	REFERENCES t_user (user_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE t_policy_approval
	ADD FOREIGN KEY (user_id)
	REFERENCES t_user (user_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



