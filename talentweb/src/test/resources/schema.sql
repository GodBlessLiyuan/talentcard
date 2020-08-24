/* Drop Tables */

DROP TABLE IF EXISTS m_farmhouse_daily;
DROP TABLE IF EXISTS m_farmhouse_month;
DROP TABLE IF EXISTS m_trip_daily;
DROP TABLE IF EXISTS m_trip_month;
DROP TABLE IF EXISTS po_compliance;
DROP TABLE IF EXISTS po_setting;
DROP TABLE IF EXISTS po_statistics;
DROP TABLE IF EXISTS po_talent_type;
DROP TABLE IF EXISTS t_annex;
DROP TABLE IF EXISTS t_bank;
DROP TABLE IF EXISTS t_policy_approval;
DROP TABLE IF EXISTS t_policy_apply;
DROP TABLE IF EXISTS t_policy;
DROP TABLE IF EXISTS po_type;
DROP TABLE IF EXISTS po_type_exclude;
DROP TABLE IF EXISTS t_activity_residue_num;
DROP TABLE IF EXISTS t_role_authority;
DROP TABLE IF EXISTS t_authority;
DROP TABLE IF EXISTS t_banner;
DROP TABLE IF EXISTS t_batch_certificate;
DROP TABLE IF EXISTS t_cert_approval;
DROP TABLE IF EXISTS t_cert_approval_pass_record;
DROP TABLE IF EXISTS t_cert_examine_record;
DROP TABLE IF EXISTS t_education;
DROP TABLE IF EXISTS t_prof_quality;
DROP TABLE IF EXISTS t_prof_title;
DROP TABLE IF EXISTS t_talent_honour;
DROP TABLE IF EXISTS t_certification;
DROP TABLE IF EXISTS t_edit_talent_record;
DROP TABLE IF EXISTS t_feedback;
DROP TABLE IF EXISTS t_insert_cert_approval;
DROP TABLE IF EXISTS t_insert_education;
DROP TABLE IF EXISTS t_insert_honour;
DROP TABLE IF EXISTS t_insert_quality;
DROP TABLE IF EXISTS t_insert_title;
DROP TABLE IF EXISTS t_insert_certification;
DROP TABLE IF EXISTS t_talent_activity_collect;
DROP TABLE IF EXISTS t_talent_certification_info;
DROP TABLE IF EXISTS t_talent_type;
DROP TABLE IF EXISTS t_user_card;
DROP TABLE IF EXISTS t_user_current_info;
DROP TABLE IF EXISTS t_talent;
DROP TABLE IF EXISTS t_card;
DROP TABLE IF EXISTS t_category;
DROP TABLE IF EXISTS t_config;
DROP TABLE IF EXISTS t_farmhouse_enjoy;
DROP TABLE IF EXISTS t_farmhouse_group_authority;
DROP TABLE IF EXISTS t_farmhouse_picture;
DROP TABLE IF EXISTS t_talent_farmhouse;
DROP TABLE IF EXISTS t_farmhouse;
DROP TABLE IF EXISTS t_honour;
DROP TABLE IF EXISTS t_user;
DROP TABLE IF EXISTS t_role;
DROP TABLE IF EXISTS t_scenic_enjoy;
DROP TABLE IF EXISTS t_scenic_picture;
DROP TABLE IF EXISTS t_talent_trip;
DROP TABLE IF EXISTS t_trip_group_authority;
DROP TABLE IF EXISTS t_scenic;
DROP TABLE IF EXISTS t_staff;
DROP TABLE IF EXISTS t_talent_activity_history;
DROP TABLE IF EXISTS t_talent_card_hold_list;
DROP TABLE IF EXISTS t_talent_json_record;
DROP TABLE IF EXISTS t_test_talent_info;
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
	UNIQUE (fh_d),
	UNIQUE (dailyFarmHouseID)
);


CREATE TABLE m_farmhouse_month
(
	fh_m bigint NOT NULL AUTO_INCREMENT,
	monthFarmhouseID char(32) NOT NULL,
	farmhouse_id bigint NOT NULL,
	name char(16) NOT NULL,
	month date NOT NULL,
	number bigint,
	times bigint,
	update_time datetime,
	PRIMARY KEY (fh_m),
	UNIQUE (fh_m),
	UNIQUE (monthFarmhouseID)
);


CREATE TABLE m_trip_daily
(
	td_id bigint unsigned NOT NULL AUTO_INCREMENT,
	sid_daily char(32) NOT NULL,
	sid bigint unsigned NOT NULL,
	daily date NOT NULL,
	scenic_name char(16) NOT NULL,
	numbers bigint unsigned,
	free_times bigint unsigned,
	discount_times bigint unsigned,
	total_times bigint unsigned,
	update_time datetime,
	PRIMARY KEY (td_id),
	UNIQUE (td_id),
	UNIQUE (sid_daily)
);


CREATE TABLE m_trip_month
(
	tm_id bigint NOT NULL AUTO_INCREMENT,
	sid_month char(32) NOT NULL,
	sid bigint unsigned NOT NULL,
	month date NOT NULL,
	name char(16) NOT NULL,
	numbers bigint unsigned,
	free_times bigint unsigned,
	discount_times bigint unsigned,
	total_times bigint unsigned,
	update_time datetime,
	PRIMARY KEY (tm_id),
	UNIQUE (tm_id),
	UNIQUE (sid_month)
);


CREATE TABLE po_compliance
(
	-- 复合人才政策的自增id
	p_co_id bigint NOT NULL AUTO_INCREMENT ,
	-- 人才政策ID
	policy_id bigint unsigned NOT NULL ,
	talent_id bigint unsigned NOT NULL,
	-- 申请时间
	apply_time datetime ,
	-- 0：未申请；1：已同意；2：已驳回；3：待审批；10：不可申请（互斥申请政策存在）
	status tinyint DEFAULT 0 ,
	-- 申请年份
	year int COMMENT '申请年份',
	PRIMARY KEY (p_co_id),
	UNIQUE (p_co_id)
);


-- 政策设置享受人群标签属性表
CREATE TABLE po_setting
(
	-- 政策适配人群属性id
	p_setingid bigint NOT NULL AUTO_INCREMENT,
	-- 人才政策ID
	policy_id bigint unsigned NOT NULL,
	-- 人才卡ID
	card_id bigint unsigned ,
	-- 人才类别ID
	category_id bigint unsigned ,
	-- 学历ID
	education_id int unsigned,
	-- 职称ID
	title_id int unsigned,
	-- 人才职业资格ID
	quality int unsigned,
	-- 人才荣誉id
	honour_id bigint unsigned ,
	-- 1：人才卡；2：人才类别；3：人才学历；4：职称；5：职业资格；6：人才荣誉
	type tinyint,
	PRIMARY KEY (p_setingid),
	UNIQUE (p_setingid)
) COMMENT = '政策设置享受人群标签属性表';


CREATE TABLE po_statistics
(
	-- 人才政策ID
	policy_id bigint unsigned NOT NULL ,
	-- 符合条件人数
	total bigint,
	-- 符合条件的人数
	not_approval bigint,
	-- 未申请
	not_apply bigint,
	-- 已通过
	pass bigint,
	-- 拒绝人数
	reject bigint,
	PRIMARY KEY (policy_id),
	UNIQUE (policy_id)
);


CREATE TABLE po_talent_type
(
	-- 自增id
	p_talent_type_id bigint NOT NULL AUTO_INCREMENT ,
	-- 人才标签综合id
	talent_type char(254),
	-- 人才政策ID
	policy_id bigint unsigned NOT NULL,
	PRIMARY KEY (p_talent_type_id),
	UNIQUE (p_talent_type_id)
);


CREATE TABLE po_type
(
	p_tId bigint unsigned NOT NULL AUTO_INCREMENT,
	-- 政策类型名称
	p_type_name char(64),
	-- 政策类型互斥Id
	exclude_id char(254) ,
	-- 政策类型适配最好的政策Id
	best_policys char(254) ,
	-- 1：上架；2：下架
	status tinyint,
	-- 1 未删除  2 已删除
	dr tinyint,
	-- 描述
	description char(255) ,
	-- 更新时间
	update_time datetime ,
	PRIMARY KEY (p_tId),
	UNIQUE (p_tId)
);


CREATE TABLE po_type_exclude
(
	-- 互斥id
	exclude_id bigint NOT NULL AUTO_INCREMENT ,
	-- 政策类型id1
	p_tid1 bigint ,
	-- 政策类型id2
	p_tid2 bigint ,
	PRIMARY KEY (exclude_id),
	UNIQUE (exclude_id)
);


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
	pa_id bigint unsigned,
	PRIMARY KEY (bank_id),
	UNIQUE (bank_id)
);


CREATE TABLE t_banner
(
	banner_id bigint unsigned NOT NULL AUTO_INCREMENT,
	name char(255),
	picture char(255),
	jump char(255),
	type tinyint unsigned,
	extra char(255),
	create_time datetime,
	update_time datetime,
	status tinyint unsigned,
	-- 1 未删除  2 已删除
	dr tinyint,
	PRIMARY KEY (banner_id),
	UNIQUE (banner_id)
);


CREATE TABLE t_batch_certificate
(
	bc_id bigint unsigned NOT NULL AUTO_INCREMENT,
	file_name char(255),
	-- 1认证中
	-- 2认证结束
	status tinyint unsigned,
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
	status tinyint,
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
	dr tinyint unsigned,
	trip_times int unsigned,
	PRIMARY KEY (card_id),
	UNIQUE (card_id)
);


CREATE TABLE t_category
(
	category_id bigint unsigned NOT NULL AUTO_INCREMENT,
	name char(32),
	-- 描述
	description char(255),
	-- 1上架；2下架
	status tinyint unsigned,
	create_time datetime,
	update_time datetime,
	-- 1 未删除  2 已删除
	dr tinyint,
	PRIMARY KEY (category_id),
	UNIQUE (category_id)
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
	status tinyint DEFAULT 2,
	-- 1 学历
	-- 2 职称
	-- 3 职业资格
	-- 4 全都有
	current_type tinyint DEFAULT 4,
	-- 1 是基本卡
	-- 2 是基本卡换的高级卡
	-- 3 是高级卡换的高级卡
	-- 4 批量认证成功的高级卡
	type tinyint ,
	PRIMARY KEY (cert_id),
	UNIQUE (cert_id)
);


CREATE TABLE t_cert_approval
(
	approval_id bigint unsigned NOT NULL AUTO_INCREMENT,
	cert_id bigint unsigned NOT NULL,
	create_time datetime,
	-- 1：提交；2：审批
	type tinyint ,
	-- 人才卡ID
	card_id bigint unsigned,
	category char(255),
	user_id bigint unsigned,
	update_time datetime,
	-- 1：同意；2：拒绝
	result tinyint ,
	opinion char(255),
	PRIMARY KEY (approval_id),
	UNIQUE (approval_id)
);


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
	sex tinyint,
	education int,
	pt_category int,
	pq_category int,
	honour_id bigint unsigned,
	-- 1通过；2驳回；3待审批
	result tinyint unsigned ,
	create_time datetime,
	PRIMARY KEY (cea_id),
	UNIQUE (cea_id)
);


CREATE TABLE t_config
(
	config_key char(64) NOT NULL,
	config_value text,
	create_time datetime,
	update_time datetime,
	PRIMARY KEY (config_key),
	UNIQUE (config_key)
);


CREATE TABLE t_edit_talent_record
(
	etc_id bigint unsigned NOT NULL AUTO_INCREMENT,
	talent_id bigint unsigned,
	user_id bigint unsigned,
	-- 1 增
	-- 2 删
	-- 3 改
	operation_type tinyint unsigned,
	operation_content tinyint unsigned,
	create_time datetime,
	comment char(254),
	before_json_record varchar(1000),
	after_json_record varchar(1000),
	PRIMARY KEY (etc_id),
	UNIQUE (etc_id)
);


CREATE TABLE t_education
(
	educ_id bigint unsigned NOT NULL AUTO_INCREMENT,
	education int,
	school char(255),
	-- 1双一流；2海外人才；3啥也不是
	first_class tinyint ,
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
	status tinyint ,
	-- 1 已认证；
	-- 2 未认证；
	-- 8 已认证，且是后来新加入的（编辑或者新增认证）
	-- 10 本次不认证
	if_certificate tinyint unsigned ,
	graduate_time char(64),
	educ_picture2 char(255),
	educ_picture3 char(255),
	-- 1全日制；
	-- 2不是全日制；
	full_time tinyint unsigned DEFAULT 1 ,
	PRIMARY KEY (educ_id),
	UNIQUE (educ_id)
);


CREATE TABLE t_farmhouse
(
	farmhouse_id bigint unsigned NOT NULL AUTO_INCREMENT,
	name char(16) NOT NULL,
	discount decimal(2,1),
	avatar char(255),
	description text,
	extra text,
	qr_code char(255),
	-- 1：上架；2：下架
	status tinyint ,
	create_time datetime,
	update_time datetime,
	-- 1 未删除  2 已删除
	dr tinyint,
	subtitle char(64),
	area int unsigned,
	location char(128),
	average_cost decimal,
	PRIMARY KEY (farmhouse_id),
	UNIQUE (farmhouse_id),
	UNIQUE (name)
);


CREATE TABLE t_farmhouse_enjoy
(
	fe_id bigint unsigned NOT NULL AUTO_INCREMENT,
	farmhouse_id bigint unsigned NOT NULL,
	card_id bigint unsigned,
	-- 人才类别ID
	category_id bigint unsigned ,
	-- 学历ID
	education_id int unsigned ,
	-- 职称ID
	title_id int unsigned,
	-- 人才职业资格ID
	quality int unsigned ,
	honour_id bigint unsigned,
	-- 1：人才卡；2：人才类别；3：人才学历；4：职称；5：职业资格；6：人才荣誉
	type tinyint ,
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


CREATE TABLE t_feedback
(
	feedback_id bigint unsigned NOT NULL AUTO_INCREMENT,
	talent_id bigint unsigned,
	contact char(255),
	create_time datetime,
	content varchar(1024),
	picture char(255),
	PRIMARY KEY (feedback_id),
	UNIQUE (feedback_id)
);


CREATE TABLE t_honour
(
	-- 人才荣誉id
	honour_id bigint unsigned NOT NULL AUTO_INCREMENT ,
	name char(32),
	description char(255),
	-- 1上架；2下架
	status tinyint unsigned,
	create_time datetime,
	-- 更新时间
	update_time datetime ,
	-- 1 未删除  2 已删除
	dr tinyint ,
	PRIMARY KEY (honour_id),
	UNIQUE (honour_id)
);


CREATE TABLE t_insert_certification
(
	insert_cert_id bigint unsigned NOT NULL AUTO_INCREMENT,
	talent_id bigint unsigned,
	-- 1认证通过
	-- 2待审批
	-- 3驳回
	-- 10弃用（编辑后）
	status tinyint unsigned ,
	-- 1 学历
	-- 2 职称
	-- 3 职业资格
	-- 4 人才荣誉
	type tinyint DEFAULT 1 ,
	cert_info bigint unsigned,
	create_time datetime,
	-- 1 未删除  2 已删除
	dr tinyint ,
	PRIMARY KEY (insert_cert_id),
	UNIQUE (insert_cert_id)
);


CREATE TABLE t_insert_cert_approval
(
	ica_id bigint unsigned NOT NULL AUTO_INCREMENT,
	create_time datetime,
	-- 1：提交；2：审批
	type tinyint,
	user_id bigint unsigned,
	update_time datetime,
	-- 1：同意；2：拒绝；8：提交审批
	result tinyint ,
	opinion char(255),
	insert_cert_id bigint unsigned,
	-- 1 未删除  2 已删除
	dr tinyint ,
	PRIMARY KEY (ica_id),
	UNIQUE (ica_id)
);


CREATE TABLE t_insert_education
(
	insert_educ_id bigint unsigned NOT NULL AUTO_INCREMENT,
	education int,
	school char(255),
	-- 1双一流；2海外人才；3啥也不是
	first_class tinyint ,
	major char(255),
	educ_picture char(255),
	open_id char(128) NOT NULL,
	-- 1认证通过
	-- 2待审批
	-- 3驳回
	status tinyint ,
	graduate_time char(64),
	insert_cert_id bigint unsigned,
	-- 1 未删除  2 已删除
	dr tinyint ,
	educ_picture2 char(255),
	educ_picture3 char(255),
	-- 1全日制；
	-- 2不是全日制；
	full_time tinyint unsigned ,
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
	status tinyint ,
	insert_cert_id bigint unsigned,
	open_id char(128),
	-- 1 未删除  2 已删除
	dr tinyint ,
	honour_picture2 char(255),
	honour_picture3 char(255),
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
	status tinyint ,
	insert_cert_id bigint unsigned,
	open_id char(128),
	-- 1 未删除  2 已删除
	dr tinyint ,
	picture2 char(255),
	picture3 char(255),
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
	status tinyint ,
	insert_cert_id bigint unsigned,
	open_id char(128),
	-- 1 未删除  2 已删除
	dr tinyint ,
	picture2 char(255),
	picture3 char(255),
	PRIMARY KEY (insert_pt_id),
	UNIQUE (insert_pt_id)
);


CREATE TABLE t_policy
(
	-- 人才政策ID
	policy_id bigint unsigned NOT NULL AUTO_INCREMENT ,
	name char(32) NOT NULL,
	num char(32) NOT NULL,
	description varchar(512) NOT NULL,
	cards char(255),
	categories char(255),
	educations char(255),
	titles char(255),
	qualities char(255),
	honour_ids char(255),
	-- 1：需要；2：不需要
	apply tinyint DEFAULT 2 ,
	color char(64),
	rate int,
	unit tinyint,
	times int,
	-- 1：需要；2：不需要；
	bank tinyint ,
	-- 1：必填；2：不填；3：选填
	annex tinyint ,
	annex_info char(255),
	apply_form char(255),
	funds int,
	user_id bigint unsigned,
	create_time datetime,
	-- 1 未删除  2 已删除
	dr tinyint ,
	p_tId bigint unsigned NOT NULL,
	role_id bigint unsigned NOT NULL,
	-- 1. 一次性发放。2. 按月发放。3. 按年发放。
	funds_form tinyint ,
	declaration_target varchar(512),
	start_time datetime,
	end_time datetime,
	apply_materials varchar(512),
	bonus varchar(512),
	business_process varchar(512),
	phone varchar(512),
	if_social_security tinyint unsigned,
	social_area tinyint unsigned,
	social_times tinyint unsigned,
	social_unit tinyint unsigned,
	-- 1上架；2下架
	up_down tinyint unsigned,
	update_time datetime,
	-- 政策开启年份
	year int ,
	relationship_id bigint,
	PRIMARY KEY (policy_id),
	UNIQUE (policy_id)
);


CREATE TABLE t_policy_apply
(
	pa_id bigint unsigned NOT NULL AUTO_INCREMENT,
	talent_id bigint unsigned NOT NULL,
	-- 人才政策ID
	policy_id bigint unsigned NOT NULL ,
	talent_name char(64) NOT NULL,
	policy_name char(32),
	create_time datetime,
	-- 1：已同意；2：已驳回；3：待审批
	status tinyint DEFAULT 3 ,
	actual_funds decimal unsigned,
	-- 如果状态为已通过，则id表示为当前已通过的那个
	policy_approval_id bigint unsigned ,
	PRIMARY KEY (pa_id),
	UNIQUE (pa_id)
);


CREATE TABLE t_policy_approval
(
	approval_id bigint unsigned NOT NULL AUTO_INCREMENT,
	pa_id bigint unsigned NOT NULL,
	create_time datetime,
	-- 1：提交；2：审批
	type tinyint ,
	user_id bigint unsigned,
	username char(32),
	update_time datetime,
	-- 1：同意；2：拒绝；3撤回
	result tinyint ,
	opinion char(255),
	actual_funds decimal unsigned,
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
	status tinyint ,
	-- 1 已认证；
	-- 2 未认证；
	-- 8 已认证，且是后来新加入的（编辑或者新增认证）
	-- 10 本次不认证
	if_certificate tinyint unsigned ,
	picture2 char(255),
	picture3 char(255),
	PRIMARY KEY (pq_id),
	UNIQUE (pq_id)
);


CREATE TABLE t_prof_title
(
	pt_id bigint unsigned NOT NULL AUTO_INCREMENT,
	category int,
	info char(255),
	picture varchar(255),
	cert_id bigint unsigned NOT NULL,
	talent_id bigint unsigned NOT NULL,
	-- 1.正常使用
	-- 2.注册没领卡（待领卡）
	-- 3.发起过认证未审批（待审批）
	-- 4.已有基础卡，且审批通过但未领卡（待领卡）
	-- 5.基础卡正常使用
	-- 9. 基本卡失效
	-- 10.其他情况失效
	status tinyint ,
	-- 1 已认证；
	-- 2 未认证；
	-- 8 已认证，且是后来新加入的（编辑或者新增认证）
	-- 10 本次不认证
	if_certificate tinyint unsigned ,
	picture2 char(255),
	picture3 char(255),
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
	-- 1. 正常角色；2.政策角色
	role_type tinyint ,
	PRIMARY KEY (role_id),
	UNIQUE (role_id)
) COMMENT = '角色表';


CREATE TABLE t_role_authority
(
	ra_id bigint unsigned NOT NULL AUTO_INCREMENT,
	-- 1权限开放; 2权限关闭
	status tinyint(4) DEFAULT 1 ,
	authority_id bigint unsigned NOT NULL,
	role_id bigint unsigned NOT NULL,
	PRIMARY KEY (ra_id),
	UNIQUE (ra_id)
);


CREATE TABLE t_scenic
(
	scenic_id bigint unsigned NOT NULL AUTO_INCREMENT,
	name char(16),
	rate int,
	-- 1：年；2：季；3：月
	unit tinyint ,
	times int,
	avatar char(255),
	description text,
	extra text,
	qr_code char(255),
	-- 1：上架；2：下架
	status tinyint ,
	create_time datetime,
	update_time datetime,
	-- 1 未删除  2 已删除
	dr tinyint ,
	subtitle char(64),
	starlevel tinyint unsigned,
	area int unsigned,
	location char(128),
	discount decimal(2,1),
	PRIMARY KEY (scenic_id),
	UNIQUE (scenic_id)
);


CREATE TABLE t_scenic_enjoy
(
	se_id bigint unsigned NOT NULL AUTO_INCREMENT,
	scenic_id bigint unsigned NOT NULL,
	card_id bigint unsigned,
	-- 人才类别ID
	category_id bigint unsigned,
	-- 学历ID
	education_id int unsigned ,
	-- 职称ID
	title_id int unsigned,
	-- 人才职业资格ID
	quality int unsigned ,
	honour_id bigint unsigned,
	-- 1：人才卡；2：人才类别；3：人才学历；4：职称；5：职业资格；6：人才荣誉
	type tinyint ,
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
	activity_first_content_id bigint unsigned NOT NULL,
	activity_second_content_id bigint unsigned NOT NULL,
	activity_second_content_name char(32) NOT NULL,
	-- 1：男；2：女
	sex tinyint,
	id_card char(128),
	phone char(32),
	create_time datetime,
	-- 1正在使用
	-- 2删除
	dr tinyint unsigned ,
	PRIMARY KEY (staff_id),
	UNIQUE (staff_id)
);


CREATE TABLE t_talent
(
	talent_id bigint unsigned NOT NULL AUTO_INCREMENT,
	open_id char(128) NOT NULL,
	union_id char(32),
	name char(64),
	-- 1：男；2：女
	sex tinyint ,
	id_card char(128),
	passport char(32),
	driver_card char(128),
	-- 1身份证2护照3驾照
	card_type tinyint unsigned,
	work_unit char(255),
	industry int unsigned,
	industry_second int unsigned,
	phone char(32) NOT NULL,
	political tinyint,
	category char(255),
	work_location char(255),
	-- 1国内；
	-- 2海外；
	work_location_type tinyint ,
	-- 人才卡ID
	card_id bigint unsigned ,
	-- 1 认证通过
	-- 2 认证没通过
	status tinyint DEFAULT 2 ,
	create_time datetime,
	-- 1正在使用
	-- 2删除
	dr tinyint unsigned ,
	talent_source int unsigned,
	PRIMARY KEY (talent_id),
	UNIQUE (talent_id),
	UNIQUE (open_id)
);


CREATE TABLE t_talent_activity_collect
(
	tac_id bigint unsigned NOT NULL AUTO_INCREMENT,
	talent_id bigint unsigned,
	-- 1 旅游
	-- 2 农家乐
	activity_first_content_id bigint unsigned ,
	activity_second_content_id bigint unsigned,
	create_time datetime,
	-- 1收藏；2未收藏
	status tinyint unsigned ,
	PRIMARY KEY (tac_id),
	UNIQUE (tac_id)
);


CREATE TABLE t_talent_activity_history
(
	tah_id bigint unsigned NOT NULL AUTO_INCREMENT,
	open_id char(128) NOT NULL,
	staff_id bigint unsigned,
	-- 1 旅游
	-- 2 农家乐
	activity_first_content_id bigint unsigned ,
	activity_second_content_id bigint unsigned,
	activity_second_content_name char(32),
	ip_address char(255),
	create_time datetime,
	-- 1待使用
	-- 2已使用
	-- 3折扣
	status tinyint unsigned ,
	-- 1 未删除  2 已删除
	dr tinyint ,
	PRIMARY KEY (tah_id),
	UNIQUE (tah_id)
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


CREATE TABLE t_talent_farmhouse
(
	tt_id bigint unsigned NOT NULL AUTO_INCREMENT,
	open_id char(128) NOT NULL,
	farmhouse_id bigint unsigned NOT NULL,
	staff_id bigint unsigned,
	discount decimal(2,1),
	effective_time datetime,
	update_time datetime,
	-- 1待使用
	-- 2已使用
	-- 3折扣
	status tinyint unsigned,
	-- 1 未删除  2 已删除
	dr tinyint,
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
	status tinyint ,
	-- 1 已认证；
	-- 2 未认证；
	-- 8 已认证，且是后来新加入的（编辑或者新增认证）
	-- 10 本次不认证
	if_certificate tinyint unsigned,
	honour_picture2 char(255),
	honour_picture3 char(255),
	PRIMARY KEY (th_id),
	UNIQUE (th_id)
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


CREATE TABLE t_talent_trip
(
	tt_id bigint unsigned NOT NULL AUTO_INCREMENT,
	open_id char(128) NOT NULL,
	scenic_id bigint unsigned NOT NULL,
	staff_id bigint unsigned,
	create_time datetime,
	effective_time datetime NOT NULL,
	update_time datetime,
	-- 1待使用
	-- 2已使用
	status tinyint unsigned ,
	-- 1 未删除  2 已删除
	dr tinyint ,
	usage_period char(128),
	effective_time_start datetime,
	PRIMARY KEY (tt_id),
	UNIQUE (tt_id)
);


CREATE TABLE t_talent_type
(
	-- 自增id
	id bigint NOT NULL AUTO_INCREMENT ,
	talent_id bigint unsigned NOT NULL,
	card_id bigint unsigned,
	-- 人才类别ID
	category_id bigint unsigned ,
	-- 学历ID
	education_id int unsigned ,
	-- 职称ID
	title_id int unsigned ,
	-- 人才职业资格ID
	quality int unsigned ,
	honour_id bigint unsigned,
	-- 1：人才卡；2：人才类别；3：人才学历；4：职称；5：职业资格；6：人才荣誉
	type tinyint ,
	PRIMARY KEY (id),
	UNIQUE (id)
);


CREATE TABLE t_test_talent_info
(
	tti_id bigint unsigned NOT NULL AUTO_INCREMENT,
	open_id char(255),
	primary_card_num char(64),
	senior_card_num char(64),
	PRIMARY KEY (tti_id),
	UNIQUE (tti_id)
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
	dr tinyint ,
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
	-- 人才卡ID
	card_id bigint unsigned ,
	name char(16),
	num char(32) NOT NULL,
	-- 当前用户带的号码，不含区域号和前缀
	current_num char(64) ,
	create_time datetime,
	-- 1 待领卡
	-- 2 已领卡，使用中
	-- 3 废弃
	status tinyint DEFAULT 1 ,
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
	-- 1双一流；2海外人才；3啥也不是
	first_class tinyint,
	major char(255),
	pt_category int,
	pt_info char(255),
	pq_category int,
	pq_info char(255),
	talent_category char(255),
	honour_id bigint unsigned,
	th_info char(255),
	graduate_time char(64),
	PRIMARY KEY (uci_id),
	UNIQUE (uci_id)
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


/* Create Foreign Keys */

ALTER TABLE t_policy
	ADD FOREIGN KEY (p_tId)
	REFERENCES po_type (p_tId)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


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


ALTER TABLE po_compliance
	ADD FOREIGN KEY (policy_id)
	REFERENCES t_policy (policy_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE po_setting
	ADD FOREIGN KEY (policy_id)
	REFERENCES t_policy (policy_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE po_talent_type
	ADD FOREIGN KEY (policy_id)
	REFERENCES t_policy (policy_id)
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


ALTER TABLE t_policy
	ADD FOREIGN KEY (role_id)
	REFERENCES t_role (role_id)
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


ALTER TABLE po_compliance
	ADD FOREIGN KEY (talent_id)
	REFERENCES t_talent (talent_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE t_certification
	ADD FOREIGN KEY (talent_id)
	REFERENCES t_talent (talent_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE t_cert_approval_pass_record
	ADD FOREIGN KEY (talent_id)
	REFERENCES t_talent (talent_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE t_cert_examine_record
	ADD FOREIGN KEY (talent_id)
	REFERENCES t_talent (talent_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE t_edit_talent_record
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


ALTER TABLE t_feedback
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


ALTER TABLE t_talent_activity_collect
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


ALTER TABLE t_talent_honour
	ADD FOREIGN KEY (talent_id)
	REFERENCES t_talent (talent_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE t_talent_type
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



