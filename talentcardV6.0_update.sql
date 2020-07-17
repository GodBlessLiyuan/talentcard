CREATE TABLE t_cert_approval_pass_record
(
	capr_id bigint unsigned NOT NULL AUTO_INCREMENT,
	talent_bo_json varchar(10000),
	cert_id bigint unsigned,
	create_time datetime,
	talent_id bigint unsigned,
	PRIMARY KEY (capr_id),
	UNIQUE (capr_id)
);
CREATE TABLE t_cert_examine_record
(
	cea_id bigint unsigned NOT NULL AUTO_INCREMENT,
	talent_id bigint unsigned,
	cert_id bigint unsigned,
	name char(16),
	-- 1：男；2：女
	sex tinyint COMMENT '1：男；2：女',
	education int,
	pt_category int,
	pq_category int,
	honour_id bigint unsigned,
	-- 1通过；2驳回；3待审批
	result tinyint unsigned COMMENT '1通过；2驳回；3待审批',
	create_time datetime,
	PRIMARY KEY (cea_id),
	UNIQUE (cea_id)
);

CREATE TABLE t_insert_certification
(
	insert_cert_id bigint unsigned NOT NULL AUTO_INCREMENT,
	talent_id bigint unsigned,
	-- 1认证通过
	-- 2待审批
	-- 3驳回
	status tinyint unsigned COMMENT '1认证通过
2待审批
3驳回',
	-- 1 学历
	-- 2 职称
	-- 3 职业资格
	-- 4 人才荣誉
	type tinyint DEFAULT 1 COMMENT '1 学历
2 职称
3 职业资格
4 人才荣誉',
	cert_info bigint unsigned,
	create_time datetime,
	-- 1 未删除  2 已删除
	dr tinyint COMMENT '1 未删除  2 已删除',
	PRIMARY KEY (insert_cert_id),
	UNIQUE (insert_cert_id)
);


CREATE TABLE t_insert_cert_approval
(
	ica_id bigint unsigned NOT NULL AUTO_INCREMENT,
	create_time datetime,
	-- 1：提交；2：审批
	type tinyint COMMENT '1：提交；2：审批',
	user_id bigint unsigned,
	update_time datetime,
	-- 1：同意；2：拒绝
	result tinyint COMMENT '1：同意；2：拒绝',
	opinion char(255),
	insert_cert_id bigint unsigned,
	-- 1 未删除  2 已删除
	dr tinyint COMMENT '1 未删除  2 已删除',
	PRIMARY KEY (ica_id),
	UNIQUE (ica_id)
);


CREATE TABLE t_insert_education
(
	insert_educ_id bigint unsigned NOT NULL AUTO_INCREMENT,
	education int,
	school char(255),
	-- 1双一流；2海外人才；3啥也不是
	first_class tinyint COMMENT '1双一流；2海外人才；3啥也不是',
	major char(255),
	educ_picture char(255),
	open_id char(128) NOT NULL,
	-- 1认证通过
	-- 2待审批
	-- 3驳回
	status tinyint COMMENT '1认证通过
2待审批
3驳回',
	graduate_time char(64),
	insert_cert_id bigint unsigned,
	-- 1 未删除  2 已删除
	dr tinyint COMMENT '1 未删除  2 已删除',
	PRIMARY KEY (insert_educ_id),
	UNIQUE (insert_educ_id)
);


CREATE TABLE t_insert_honour
(
	insert_th_id bigint unsigned NOT NULL AUTO_INCREMENT,
	honour_id bigint unsigned,
	honour_picture char(255),
	info char(255),
	-- 1 认证通过
	-- 2 待审批
	-- 3 驳回
	-- 4 已废弃
	status tinyint COMMENT '1 认证通过
2 待审批
3 驳回
4 已废弃',
	insert_cert_id bigint unsigned,
	open_id char(128),
	-- 1 未删除  2 已删除
	dr tinyint COMMENT '1 未删除  2 已删除',
	PRIMARY KEY (insert_th_id),
	UNIQUE (insert_th_id)
);


CREATE TABLE t_insert_quality
(
	insert_pq_id bigint unsigned NOT NULL AUTO_INCREMENT,
	category int,
	picture char(255),
	info char(255),
	-- 1认证通过
	-- 2待审批
	-- 3驳回
	status tinyint COMMENT '1认证通过
2待审批
3驳回',
	insert_cert_id bigint unsigned,
	open_id char(128),
	-- 1 未删除  2 已删除
	dr tinyint COMMENT '1 未删除  2 已删除',
	PRIMARY KEY (insert_pq_id),
	UNIQUE (insert_pq_id)
);


CREATE TABLE t_insert_title
(
	insert_pt_id bigint unsigned NOT NULL AUTO_INCREMENT,
	category int,
	info char(255),
	picture char(255),
	-- 1认证通过
	-- 2待审批
	-- 3驳回
	status tinyint COMMENT '1认证通过
2待审批
3驳回',
	insert_cert_id bigint unsigned,
	open_id char(128),
	-- 1 未删除  2 已删除
	dr tinyint COMMENT '1 未删除  2 已删除',
	PRIMARY KEY (insert_pt_id),
	UNIQUE (insert_pt_id)
);


CREATE TABLE t_talent_card_hold_list
(
	tchl_id bigint unsigned NOT NULL AUTO_INCREMENT,
	id_card char(128),
	num bigint unsigned,
	PRIMARY KEY (tchl_id),
	UNIQUE (tchl_id)
);


CREATE TABLE t_talent_certification_info
(
	tci_id bigint unsigned NOT NULL AUTO_INCREMENT,
	talent_id bigint unsigned,
	education varchar(1024),
	pt_category varchar(1024),
	pq_category varchar(1024),
	talent_category char(255),
	honour_id varchar(1024),
	PRIMARY KEY (tci_id),
	UNIQUE (tci_id)
);

CREATE TABLE t_talent_json_record
(
	tti_id bigint unsigned NOT NULL AUTO_INCREMENT,
	info varchar(10000),
	open_id char(128),
	create_time datetime,
	PRIMARY KEY (tti_id),
	UNIQUE (tti_id)
);



ALTER TABLE t_cert_approval_pass_record
	ADD FOREIGN KEY (cert_id)
	REFERENCES t_certification (cert_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;
ALTER TABLE t_cert_examine_record
	ADD FOREIGN KEY (cert_id)
	REFERENCES t_certification (cert_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;

ALTER TABLE t_insert_cert_approval
	ADD FOREIGN KEY (insert_cert_id)
	REFERENCES t_insert_certification (insert_cert_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE t_insert_education
	ADD FOREIGN KEY (insert_cert_id)
	REFERENCES t_insert_certification (insert_cert_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE t_insert_honour
	ADD FOREIGN KEY (insert_cert_id)
	REFERENCES t_insert_certification (insert_cert_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE t_insert_quality
	ADD FOREIGN KEY (insert_cert_id)
	REFERENCES t_insert_certification (insert_cert_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE t_insert_title
	ADD FOREIGN KEY (insert_cert_id)
	REFERENCES t_insert_certification (insert_cert_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE t_cert_examine_record
	ADD FOREIGN KEY (talent_id)
	REFERENCES t_talent (talent_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;

ALTER TABLE t_insert_certification
	ADD FOREIGN KEY (talent_id)
	REFERENCES t_talent (talent_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE t_talent_certification_info
	ADD FOREIGN KEY (talent_id)
	REFERENCES t_talent (talent_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;

