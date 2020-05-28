/* Drop Tables */

DROP TABLE IF EXISTS t_activity_residue_num;
DROP TABLE IF EXISTS t_annex;
DROP TABLE IF EXISTS t_role_authority;
DROP TABLE IF EXISTS t_authority;
DROP TABLE IF EXISTS t_bank;
DROP TABLE IF EXISTS t_batch_certificate;
DROP TABLE IF EXISTS t_cert_approval;
DROP TABLE IF EXISTS t_education;
DROP TABLE IF EXISTS t_prof_quality;
DROP TABLE IF EXISTS t_prof_title;
DROP TABLE IF EXISTS t_talent_honour;
DROP TABLE IF EXISTS t_certification;
DROP TABLE IF EXISTS t_policy_approval;
DROP TABLE IF EXISTS t_policy_apply;
DROP TABLE IF EXISTS t_user_card;
DROP TABLE IF EXISTS t_user_current_info;
DROP TABLE IF EXISTS t_talent;
DROP TABLE IF EXISTS t_card;
DROP TABLE IF EXISTS t_config;
DROP TABLE IF EXISTS t_farmhouse_enjoy;
DROP TABLE IF EXISTS t_farmhouse_group_authority;
DROP TABLE IF EXISTS t_farmhouse_picture;
DROP TABLE IF EXISTS t_talent_farmhouse;
DROP TABLE IF EXISTS t_farmhouse;
DROP TABLE IF EXISTS t_policy;
DROP TABLE IF EXISTS t_user;
DROP TABLE IF EXISTS t_role;
DROP TABLE IF EXISTS t_scenic_enjoy;
DROP TABLE IF EXISTS t_scenic_picture;
DROP TABLE IF EXISTS t_talent_trip;
DROP TABLE IF EXISTS t_trip_group_authority;
DROP TABLE IF EXISTS t_scenic;
DROP TABLE IF EXISTS t_staff;
DROP TABLE IF EXISTS t_talent_activity_history;




/* Create Tables */

CREATE TABLE t_activity_residue_num
(
	arn_id bigint unsigned NOT NULL AUTO_INCREMENT,
	num bigint unsigned NOT NULL,
	time char(64) NOT NULL,
	PRIMARY KEY (arn_id),
	UNIQUE (arn_id)
);


CREATE TABLE t_annex
(
	annex_id bigint unsigned NOT NULL AUTO_INCREMENT,
	name char(128),
	location char(255),
	pa_id bigint unsigned NOT NULL,
	PRIMARY KEY (annex_id),
	UNIQUE (annex_id)
);


-- 权限表
CREATE TABLE t_authority
(
	authority_id bigint unsigned NOT NULL AUTO_INCREMENT,
	name char(32),
	PRIMARY KEY (authority_id),
	UNIQUE (authority_id)
) COMMENT = '权限表';


CREATE TABLE t_bank
(
	bank_id bigint unsigned NOT NULL AUTO_INCREMENT,
	num char(32) NOT NULL,
	name char(32) NOT NULL,
	pa_id bigint unsigned NOT NULL,
	PRIMARY KEY (bank_id),
	UNIQUE (bank_id)
);


CREATE TABLE t_batch_certificate
(
	bc_id bigint unsigned NOT NULL,
	file_name char(255),
	-- 1认证中
	-- 2认证结束
	status tinyint unsigned COMMENT '1认证中
2认证结束',
	total_num int unsigned,
	success_num int unsigned,
	failure_num int unsigned,
	download_url char(255),
	user_id bigint unsigned,
	user_name char(32),
	create_time datetime,
	update_time datetime,
	PRIMARY KEY (bc_id),
	UNIQUE (bc_id)
);


CREATE TABLE t_card
(
	card_id bigint unsigned NOT NULL AUTO_INCREMENT,
	wx_card_id char(255),
	name char(16),
	title char(32) NOT NULL,
	-- 1：默认；2：非默认
	status tinyint COMMENT '1：默认；2：非默认',
	member_num bigint unsigned,
	waiting_member_num bigint unsigned,
	curr_num bigint unsigned DEFAULT 0,
	description varchar(2048) NOT NULL,
	picture char(255) NOT NULL,
	picture_cdn varchar(1024),
	logo_url char(255),
	prerogative varchar(2048),
	initial_word char(32) NOT NULL,
	initial_num char(32),
	area_num char(128),
	business_description char(255),
	create_person char(16),
	update_person char(16),
	create_time datetime,
	update_time datetime,
	-- 1正在使用
	-- 2删除
	dr tinyint unsigned COMMENT '1正在使用
2删除',
	PRIMARY KEY (card_id),
	UNIQUE (card_id)
);


CREATE TABLE t_certification
(
	cert_id bigint unsigned NOT NULL AUTO_INCREMENT,
	talent_id bigint unsigned,
	political tinyint,
	create_time datetime,
	-- 1.正常使用
	-- 2.注册没领卡（待领卡）
	-- 3.发起过认证未审批（待审批）
	-- 4.已有基础卡，且审批通过但未领卡（待领卡）
	-- 5.基础卡正常使用
	-- 9. 基本卡失效
	-- 10.其他情况失效
	status tinyint DEFAULT 2 COMMENT '1.正常使用
2.注册没领卡（待领卡）
3.发起过认证未审批（待审批）
4.已有基础卡，且审批通过但未领卡（待领卡）
5.基础卡正常使用
9. 基本卡失效
10.其他情况失效',
	-- 1 学历
	-- 2 职称
	-- 3 职业资格
	-- 4 全都有
	current_type tinyint DEFAULT 4 COMMENT '1 学历
2 职称
3 职业资格
4 全都有',
	-- 1 是基本卡
	-- 2 是基本卡换的高级卡
	-- 3 是高级卡换的高级卡
	-- 4 批量认证成功的高级卡
	type tinyint COMMENT '1 是基本卡
2 是基本卡换的高级卡
3 是高级卡换的高级卡
4 批量认证成功的高级卡',
	PRIMARY KEY (cert_id),
	UNIQUE (cert_id)
);


CREATE TABLE t_cert_approval
(
	approval_id bigint unsigned NOT NULL AUTO_INCREMENT,
	cert_id bigint unsigned NOT NULL,
	create_time datetime,
	-- 1：提交；2：审批
	type tinyint COMMENT '1：提交；2：审批',
	card_id bigint unsigned,
	category char(255),
	user_id bigint unsigned,
	update_time datetime,
	-- 1：同意；2：拒绝
	result tinyint COMMENT '1：同意；2：拒绝',
	opinion char(255),
	PRIMARY KEY (approval_id),
	UNIQUE (approval_id)
);


CREATE TABLE t_config
(
	config_key char(64) NOT NULL,
	config_value char(255),
	create_time datetime,
	update_time datetime,
	PRIMARY KEY (config_key),
	UNIQUE (config_key)
);


CREATE TABLE t_education
(
	educ_id bigint unsigned NOT NULL AUTO_INCREMENT,
	education int,
	school char(255),
	-- 1：是；2：否
	first_class tinyint COMMENT '1：是；2：否',
	major char(255),
	educ_picture char(255),
	cert_id bigint unsigned NOT NULL,
	talent_id bigint unsigned NOT NULL,
	-- 1.正常使用
	-- 2.注册没领卡（待领卡）
	-- 3.发起过认证未审批（待审批）
	-- 4.已有基础卡，且审批通过但未领卡（待领卡）
	-- 5.基础卡正常使用
	-- 9. 基本卡失效
	-- 10.其他情况失效
	status tinyint COMMENT '1.正常使用
2.注册没领卡（待领卡）
3.发起过认证未审批（待审批）
4.已有基础卡，且审批通过但未领卡（待领卡）
5.基础卡正常使用
9. 基本卡失效
10.其他情况失效',
	-- 1 已认证；
	-- 2 未认证；
	-- 10 本次不认证
	if_certificate tinyint unsigned COMMENT '1 已认证；
2 未认证；
10 本次不认证',
	PRIMARY KEY (educ_id),
	UNIQUE (educ_id)
);


CREATE TABLE t_farmhouse
(
	farmhouse_id bigint unsigned NOT NULL AUTO_INCREMENT,
	name char(16) NOT NULL,
	discount decimal (2,1),
	avatar char(255),
	description text,
	extra text,
	qr_code char(255),
	status tinyint,
	create_time datetime,
	dr tinyint,
	PRIMARY KEY (farmhouse_id),
	UNIQUE (farmhouse_id),
	UNIQUE (name)
);


CREATE TABLE t_farmhouse_enjoy
(
	fe_id bigint unsigned NOT NULL AUTO_INCREMENT,
	farmhouse_id bigint unsigned NOT NULL,
	card_id bigint unsigned,
	category_id bigint unsigned,
	education_id int unsigned,
	title_id int unsigned,
	quality int unsigned,
	honour_id bigint unsigned,
	-- 1：人才卡；2：人才类别；3：人才学历；4：职称；5：职业资格；6：人才荣誉
	type tinyint COMMENT '1：人才卡；2：人才类别；3：人才学历；4：职称；5：职业资格；6：人才荣誉',
	PRIMARY KEY (fe_id),
	UNIQUE (fe_id)
);


CREATE TABLE t_farmhouse_group_authority
(
	fgt_id bigint unsigned NOT NULL AUTO_INCREMENT,
	farmhouse_id bigint unsigned NOT NULL,
	authority_code char(128),
	PRIMARY KEY (fgt_id),
	UNIQUE (fgt_id)
);


CREATE TABLE t_farmhouse_picture
(
	fp_id bigint unsigned NOT NULL AUTO_INCREMENT,
	farmhouse_id bigint unsigned NOT NULL,
	picture char(255),
	PRIMARY KEY (fp_id),
	UNIQUE (fp_id)
);


CREATE TABLE t_policy
(
	policy_id bigint unsigned NOT NULL AUTO_INCREMENT,
	name char(32) NOT NULL,
	num char(32) NOT NULL,
	description text NOT NULL,
	cards char(255),
	categories char(255),
	educations char(255),
	titles char(255),
	qualities char(255),
	honour_ids varchar(2048),
	-- 1：需要；2：不需要
	apply tinyint DEFAULT 2 COMMENT '1：需要；2：不需要',
	color char(64),
	rate int,
	unit tinyint,
	times int,
	-- 1：需要；2：不需要；
	bank tinyint COMMENT '1：需要；2：不需要；',
	-- 1：需要；2：不需要；
	annex tinyint COMMENT '1：需要；2：不需要；',
	user_id bigint unsigned,
	create_time datetime,
	-- 1 未删除  2 已删除
	dr tinyint COMMENT '1 未删除  2 已删除',
	PRIMARY KEY (policy_id),
	UNIQUE (policy_id)
);


CREATE TABLE t_policy_apply
(
	pa_id bigint unsigned NOT NULL AUTO_INCREMENT,
	talent_id bigint unsigned NOT NULL,
	talent_name char(64) NOT NULL,
	policy_id bigint unsigned NOT NULL,
	policy_name char(32),
	create_time datetime,
	-- 1：已同意；2：已驳回；3：待审批
	status tinyint DEFAULT 3 COMMENT '1：已同意；2：已驳回；3：待审批',
	PRIMARY KEY (pa_id),
	UNIQUE (pa_id)
);


CREATE TABLE t_policy_approval
(
	approval_id bigint unsigned NOT NULL AUTO_INCREMENT,
	pa_id bigint unsigned NOT NULL,
	create_time datetime,
	-- 1：提交；2：审批
	type tinyint COMMENT '1：提交；2：审批',
	user_id bigint unsigned,
	username char(32),
	update_time datetime,
	-- 1：同意；2：拒绝
	result tinyint COMMENT '1：同意；2：拒绝',
	opinion char(255),
	PRIMARY KEY (approval_id),
	UNIQUE (approval_id)
);


CREATE TABLE t_prof_quality
(
	pq_id bigint unsigned NOT NULL AUTO_INCREMENT,
	category int,
	picture char(255),
	info char(255),
	cert_id bigint unsigned NOT NULL,
	talent_id bigint unsigned NOT NULL,
	-- 1.正常使用
	-- 2.注册没领卡（待领卡）
	-- 3.发起过认证未审批（待审批）
	-- 4.已有基础卡，且审批通过但未领卡（待领卡）
	-- 5.基础卡正常使用
	-- 9. 基本卡失效
	-- 10.其他情况失效
	status tinyint COMMENT '1.正常使用
2.注册没领卡（待领卡）
3.发起过认证未审批（待审批）
4.已有基础卡，且审批通过但未领卡（待领卡）
5.基础卡正常使用
9. 基本卡失效
10.其他情况失效',
	-- 1 已认证；
	-- 2 未认证；
	-- 10 本次不认证
	if_certificate tinyint unsigned COMMENT '1 已认证；
2 未认证；
10 本次不认证',
	PRIMARY KEY (pq_id),
	UNIQUE (pq_id)
);


CREATE TABLE t_prof_title
(
	pt_id bigint unsigned NOT NULL AUTO_INCREMENT,
	category int,
	info char(255),
	picture char(255),
	cert_id bigint unsigned NOT NULL,
	talent_id bigint unsigned NOT NULL,
	-- 1.正常使用
	-- 2.注册没领卡（待领卡）
	-- 3.发起过认证未审批（待审批）
	-- 4.已有基础卡，且审批通过但未领卡（待领卡）
	-- 5.基础卡正常使用
	-- 9. 基本卡失效
	-- 10.其他情况失效
	status tinyint COMMENT '1.正常使用
2.注册没领卡（待领卡）
3.发起过认证未审批（待审批）
4.已有基础卡，且审批通过但未领卡（待领卡）
5.基础卡正常使用
9. 基本卡失效
10.其他情况失效',
	-- 1 已认证；
	-- 2 未认证；
	-- 10 本次不认证
	if_certificate tinyint unsigned COMMENT '1 已认证；
2 未认证；
10 本次不认证',
	PRIMARY KEY (pt_id),
	UNIQUE (pt_id)
);


-- 角色表
CREATE TABLE t_role
(
	role_id bigint unsigned NOT NULL AUTO_INCREMENT,
	name char(32),
	extra char(255),
	create_time datetime,
	PRIMARY KEY (role_id),
	UNIQUE (role_id)
) COMMENT = '角色表';


CREATE TABLE t_role_authority
(
	ra_id bigint unsigned NOT NULL AUTO_INCREMENT,
	-- 1权限开放; 2权限关闭
	status tinyint(4) DEFAULT 1 COMMENT '1权限开放; 2权限关闭    ',
	authority_id bigint unsigned NOT NULL,
	role_id bigint unsigned NOT NULL,
	PRIMARY KEY (ra_id),
	UNIQUE (ra_id)
);


CREATE TABLE t_scenic
(
	scenic_id bigint unsigned NOT NULL AUTO_INCREMENT,
	name char(16) NOT NULL,
	rate int,
	-- 1：年；2：季；3：月
	unit tinyint COMMENT '1：年；2：季；3：月',
	times int,
	avatar char(255),
	description text,
	extra text,
	qr_code char(255),
	-- 1：上架；2：下架
	status tinyint COMMENT '1：上架；2：下架',
	create_time datetime,
	-- 1 未删除  2 已删除
	dr tinyint COMMENT '1 未删除  2 已删除',
	PRIMARY KEY (scenic_id),
	UNIQUE (scenic_id),
	UNIQUE (name)
);


CREATE TABLE t_scenic_enjoy
(
	se_id bigint unsigned NOT NULL AUTO_INCREMENT,
	scenic_id bigint unsigned NOT NULL,
	card_id bigint unsigned,
	category_id bigint unsigned,
	education_id int unsigned,
	title_id int unsigned,
	quality int unsigned,
	honour_id bigint unsigned,
	-- 1：人才卡；2：人才类别；3：人才学历；4：职称；5：职业资格；6：人才荣誉
	type tinyint COMMENT '1：人才卡；2：人才类别；3：人才学历；4：职称；5：职业资格；6：人才荣誉',
	PRIMARY KEY (se_id),
	UNIQUE (se_id)
);


CREATE TABLE t_scenic_picture
(
	sp_id bigint unsigned NOT NULL AUTO_INCREMENT,
	scenic_id bigint unsigned NOT NULL,
	picture char(255),
	PRIMARY KEY (sp_id),
	UNIQUE (sp_id)
);


CREATE TABLE t_staff
(
	staff_id bigint unsigned NOT NULL AUTO_INCREMENT,
	open_id char(128),
	name char(32),
	-- 1 旅游
	-- 2 农家乐
	activity_first_content_id bigint unsigned NOT NULL COMMENT '1 旅游
2 农家乐',
	activity_second_content_id bigint unsigned NOT NULL,
	activity_second_content_name char(32) NOT NULL,
	-- 1：男；2：女
	sex tinyint COMMENT '1：男；2：女',
	id_card char(128),
	phone char(32),
	create_time datetime,
	-- 1正在使用
	-- 2删除
	dr tinyint unsigned COMMENT '1正在使用
2删除',
	PRIMARY KEY (staff_id),
	UNIQUE (staff_id)
);


CREATE TABLE t_talent
(
	talent_id bigint unsigned NOT NULL AUTO_INCREMENT,
	open_id char(128) NOT NULL,
	name char(64),
	-- 1：男；2：女
	sex tinyint COMMENT '1：男；2：女',
	id_card char(128),
	passport char(32),
	driver_card char(128),
	-- 1身份证2护照3驾照
	card_type tinyint unsigned COMMENT '1身份证2护照3驾照',
	work_unit char(255),
	industry int unsigned,
	industry_second int unsigned,
	phone char(32) NOT NULL,
	political tinyint,
	category char(255),
	work_location char(255),
	-- 1国内；
	-- 2海外；
	work_location_type tinyint COMMENT '1国内；
2海外；',
	card_id bigint unsigned,
	-- 1 认证通过
	-- 2 认证没通过
	status tinyint DEFAULT 2 COMMENT '1 认证通过
2 认证没通过',
	create_time datetime,
	-- 1正在使用
	-- 2删除
	dr tinyint unsigned COMMENT '1正在使用
2删除',
	PRIMARY KEY (talent_id),
	UNIQUE (talent_id),
	UNIQUE (open_id)
);


CREATE TABLE t_talent_activity_history
(
	tah_id bigint unsigned NOT NULL AUTO_INCREMENT,
	open_id char(128) NOT NULL,
	staff_id bigint unsigned,
	-- 1 旅游
	-- 2 农家乐
	activity_first_content_id bigint unsigned COMMENT '1 旅游
2 农家乐',
	activity_second_content_id bigint unsigned,
	activity_second_content_name char(32),
	ip_address char(255),
	create_time datetime,
	status tinyint unsigned,
	-- 1 未删除  2 已删除
	dr tinyint COMMENT '1 未删除  2 已删除',
	PRIMARY KEY (tah_id),
	UNIQUE (tah_id)
);


CREATE TABLE t_talent_farmhouse
(
	tt_id bigint unsigned NOT NULL AUTO_INCREMENT,
	open_id char(128) NOT NULL,
	farmhouse_id bigint unsigned NOT NULL,
	staff_id bigint unsigned,
	discount decimal (2,1),
	effective_time datetime,
	update_time datetime,
	status tinyint unsigned,
	-- 1 未删除  2 已删除
	dr tinyint COMMENT '1 未删除  2 已删除',
	PRIMARY KEY (tt_id),
	UNIQUE (tt_id)
);


CREATE TABLE t_talent_honour
(
	th_id bigint unsigned NOT NULL AUTO_INCREMENT,
	honour_id bigint unsigned,
	honour_picture char(255),
	info char(255),
	cert_id bigint unsigned NOT NULL,
	talent_id bigint unsigned NOT NULL,
	-- 1.正常使用
	-- 2.注册没领卡（待领卡）
	-- 3.发起过认证未审批（待审批）
	-- 4.已有基础卡，且审批通过但未领卡（待领卡）
	-- 5.基础卡正常使用
	-- 9. 基本卡失效
	-- 10.其他情况失效
	status tinyint COMMENT '1.正常使用
2.注册没领卡（待领卡）
3.发起过认证未审批（待审批）
4.已有基础卡，且审批通过但未领卡（待领卡）
5.基础卡正常使用
9. 基本卡失效
10.其他情况失效',
	-- 1 已认证；
	-- 2 未认证；
	-- 10 本次不认证
	if_certificate tinyint unsigned COMMENT '1 已认证；
2 未认证；
10 本次不认证',
	PRIMARY KEY (th_id),
	UNIQUE (th_id)
);


CREATE TABLE t_talent_trip
(
	tt_id bigint unsigned NOT NULL AUTO_INCREMENT,
	open_id char(128) NOT NULL,
	scenic_id bigint unsigned NOT NULL,
	staff_id bigint unsigned,
	create_time datetime,
	effective_time datetime NOT NULL,
	update_time datetime,
	status tinyint unsigned,
	-- 1 未删除  2 已删除
	dr tinyint COMMENT '1 未删除  2 已删除',
	PRIMARY KEY (tt_id),
	UNIQUE (tt_id)
);


CREATE TABLE t_trip_group_authority
(
	fgt_id bigint unsigned NOT NULL AUTO_INCREMENT,
	scenic_id bigint unsigned NOT NULL,
	authority_code char(128),
	PRIMARY KEY (fgt_id),
	UNIQUE (fgt_id)
);


-- 人才卡用户基本信息表
CREATE TABLE t_user
(
	user_id bigint unsigned NOT NULL AUTO_INCREMENT,
	username char(32),
	name char(32),
	password char(32),
	create_time datetime,
	-- 1 未删除  2 已删除
	dr tinyint COMMENT '1 未删除  2 已删除',
	extra char(255),
	role_id bigint unsigned NOT NULL,
	PRIMARY KEY (user_id),
	UNIQUE (user_id),
	UNIQUE (username)
) COMMENT = '人才卡用户基本信息表';


CREATE TABLE t_user_card
(
	uc_id bigint unsigned NOT NULL AUTO_INCREMENT,
	talent_id bigint unsigned NOT NULL,
	card_id bigint unsigned,
	name char(16),
	num char(32) NOT NULL,
	-- 当前用户带的号码，不含区域号和前缀
	current_num char(64) COMMENT '当前用户带的号码，不含区域号和前缀',
	create_time datetime,
	-- 1 待领卡
	-- 2 已领卡，使用中
	-- 3 废弃
	status tinyint DEFAULT 1 COMMENT '1 待领卡
2 已领卡，使用中
3 废弃',
	PRIMARY KEY (uc_id),
	UNIQUE (uc_id)
);


CREATE TABLE t_user_current_info
(
	uci_id bigint unsigned NOT NULL AUTO_INCREMENT,
	talent_id bigint unsigned,
	political tinyint,
	education int,
	school char(255),
	-- 1：是；2：否
	first_class tinyint COMMENT '1：是；2：否',
	major char(255),
	pt_category int,
	pt_info char(255),
	pq_category int,
	pq_info char(255),
	talent_category char(255),
	honour_id bigint unsigned,
	th_info char(255),
	PRIMARY KEY (uci_id),
	UNIQUE (uci_id)
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


ALTER TABLE t_talent
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


ALTER TABLE t_talent_honour
	ADD FOREIGN KEY (cert_id)
	REFERENCES t_certification (cert_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE t_farmhouse_enjoy
	ADD FOREIGN KEY (farmhouse_id)
	REFERENCES t_farmhouse (farmhouse_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE t_farmhouse_group_authority
	ADD FOREIGN KEY (farmhouse_id)
	REFERENCES t_farmhouse (farmhouse_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE t_farmhouse_picture
	ADD FOREIGN KEY (farmhouse_id)
	REFERENCES t_farmhouse (farmhouse_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE t_talent_farmhouse
	ADD FOREIGN KEY (farmhouse_id)
	REFERENCES t_farmhouse (farmhouse_id)
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


ALTER TABLE t_scenic_enjoy
	ADD FOREIGN KEY (scenic_id)
	REFERENCES t_scenic (scenic_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE t_scenic_picture
	ADD FOREIGN KEY (scenic_id)
	REFERENCES t_scenic (scenic_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE t_talent_trip
	ADD FOREIGN KEY (scenic_id)
	REFERENCES t_scenic (scenic_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE t_trip_group_authority
	ADD FOREIGN KEY (scenic_id)
	REFERENCES t_scenic (scenic_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE t_talent_farmhouse
	ADD FOREIGN KEY (staff_id)
	REFERENCES t_staff (staff_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE t_talent_trip
	ADD FOREIGN KEY (staff_id)
	REFERENCES t_staff (staff_id)
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


ALTER TABLE t_talent_honour
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


ALTER TABLE t_user_current_info
	ADD FOREIGN KEY (talent_id)
	REFERENCES t_talent (talent_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE t_batch_certificate
	ADD FOREIGN KEY (user_id)
	REFERENCES t_user (user_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE t_cert_approval
	ADD FOREIGN KEY (user_id)
	REFERENCES t_user (user_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE t_policy
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



