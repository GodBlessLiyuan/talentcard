
### 修改表 ###
1. t_role表中添加角色类型字段role_type：1. 正常角色；2.政策角色
2. t_policy表中添加很多新的字段
funds_form
declaration_target
start_time
end_time
apply_materials
bonus
business_process
phone
if_social_security
social_area
social_times
social_unit
up_down
update_time 
3. po_compliance表中添加step字段
4. t_policy_approval 增加actual_funds字段
### 新添加表 ###
1. po_compliance表
2. po_setting表，政策设置享受人群标签属性表
3. po_talent_type表
4. po_type表
5. po_type_exclude表
6. t_talent_type表
7. op_sendmessage表，符合政策用户的推送消息汇总
8. op_mess_record表，一键推送消息记录表
9. po_statistics表

### 需要执行的旧数据导入到新的表格的数据 ###

1. t_talent_type表，执行 {{server}}/dataMigration/cerToTalentTypeDB
2. po_compliance表，


### application.properties ###
1. 添加配置wechat.sendToNotApplyPolicy
2. 添加配置vbooster.token



CREATE TABLE po_statistics
(
	-- 人才政策ID
	policy_id bigint unsigned NOT NULL COMMENT '人才政策ID',
	-- 符合条件人数
	total bigint COMMENT '符合条件人数',
	-- 待审批
	not_approval bigint COMMENT '待审批',
	-- 未申请
	not_apply bigint COMMENT '未申请',
	-- 已通过
	pass bigint COMMENT '已通过',
	-- 拒绝人数
	reject bigint COMMENT '拒绝人数',
	PRIMARY KEY (policy_id),
	UNIQUE (policy_id)
);


CREATE TABLE po_compliance
(
	-- 复合人才政策的自增id
	p_co_id bigint NOT NULL AUTO_INCREMENT COMMENT '复合人才政策的自增id',
	-- 人才政策ID
	policy_id bigint unsigned NOT NULL COMMENT '人才政策ID',
	-- 人才ID
	talent_id bigint unsigned NOT NULL COMMENT '人才ID',
	-- 申请时间
	apply_time datetime COMMENT '申请时间',
	-- 0：未申请；1：已同意；2：已驳回；3：待审批；10：不可申请（互斥申请政策存在）
	status tinyint DEFAULT 0 COMMENT '0：未申请；1：已同意；2：已驳回；3：待审批；10：不可申请（互斥申请政策存在）',
	-- 申请年份
	year int COMMENT '申请年份',
	PRIMARY KEY (p_co_id),
	UNIQUE (p_co_id)
);


-- 政策设置享受人群标签属性表
CREATE TABLE po_setting
(
	-- 政策适配人群属性id
	p_setingid bigint NOT NULL AUTO_INCREMENT COMMENT '政策适配人群属性id',
	-- 人才政策ID
	policy_id bigint unsigned NOT NULL COMMENT '人才政策ID',
	-- 人才卡ID
	card_id bigint unsigned COMMENT '人才卡ID',
	-- 人才类别ID
	category_id bigint unsigned COMMENT '人才类别ID',
	-- 学历ID
	education_id int unsigned COMMENT '学历ID',
	-- 职称ID
	title_id int unsigned COMMENT '职称ID',
	-- 人才职业资格ID
	quality int unsigned COMMENT '人才职业资格ID',
	-- 人才荣誉id
	honour_id bigint unsigned COMMENT '人才荣誉id',
	-- 1：人才卡；2：人才类别；3：人才学历；4：职称；5：职业资格；6：人才荣誉
	type tinyint COMMENT '1：人才卡；2：人才类别；3：人才学历；4：职称；5：职业资格；6：人才荣誉',
	PRIMARY KEY (p_setingid),
	UNIQUE (p_setingid)
) COMMENT = '政策设置享受人群标签属性表';

CREATE TABLE po_talent_type
(
	-- 自增id
	p_talent_type_id bigint NOT NULL AUTO_INCREMENT COMMENT '自增id',
	-- 人才标签综合id
	talent_type char(254) COMMENT '人才标签综合id',
	-- 人才政策ID
	policy_id bigint unsigned NOT NULL COMMENT '人才政策ID',
	PRIMARY KEY (p_talent_type_id),
	UNIQUE (p_talent_type_id)
);


CREATE TABLE po_type
(
	p_tId bigint unsigned NOT NULL AUTO_INCREMENT,
	-- 政策类型名称
	p_type_name char(64) COMMENT '政策类型名称',
	-- 政策类型互斥Id
	exclude_id char(254) COMMENT '政策类型互斥Id',
	-- 政策类型适配最好的政策Id
	best_policys char(254) COMMENT '政策类型适配最好的政策Id',
	-- 1：上架；2：下架
	status tinyint COMMENT '1：上架；2：下架',
	-- 1 未删除  2 已删除
	dr tinyint COMMENT '1 未删除  2 已删除',
	-- 描述
	description char(255) COMMENT '描述',
	-- 更新时间
	update_time datetime COMMENT '更新时间',
	PRIMARY KEY (p_tId),
	UNIQUE (p_tId)
);


CREATE TABLE po_type_exclude
(
	-- 互斥id
	exclude_id bigint NOT NULL AUTO_INCREMENT COMMENT '互斥id',
	-- 政策类型id1
	p_tid1 bigint COMMENT '政策类型id1',
	-- 政策类型id2
	p_tid2 bigint COMMENT '政策类型id2',
	PRIMARY KEY (exclude_id),
	UNIQUE (exclude_id)
);


CREATE TABLE t_talent_type
(
	-- 自增id
	id bigint NOT NULL AUTO_INCREMENT COMMENT '自增id',
	-- 人才ID
	talent_id bigint unsigned NOT NULL COMMENT '人才ID',
	card_id bigint unsigned,
	-- 人才类别ID
	category_id bigint unsigned COMMENT '人才类别ID',
	-- 学历ID
	education_id int unsigned COMMENT '学历ID',
	-- 职称ID
	title_id int unsigned COMMENT '职称ID',
	-- 人才职业资格ID
	quality int unsigned COMMENT '人才职业资格ID',
	honour_id bigint unsigned,
	-- 1：人才卡；2：人才类别；3：人才学历；4：职称；5：职业资格；6：人才荣誉
	type tinyint COMMENT '1：人才卡；2：人才类别；3：人才学历；4：职称；5：职业资格；6：人才荣誉',
	PRIMARY KEY (id),
	UNIQUE (id)
);


CREATE TABLE op_mess_record
(
	id bigint NOT NULL AUTO_INCREMENT,
	-- 一键推送id
	send_id bigint NOT NULL COMMENT '一键推送id',
	-- 人才ID
	talent_id bigint unsigned COMMENT '人才ID',
	-- 人才的openId
	open_id char(128) COMMENT '人才的openId',
	-- 0：发送成功；43004：未关注公众号
	status int COMMENT '0：发送成功；43004：未关注公众号',
	PRIMARY KEY (id),
	UNIQUE (id)
);


CREATE TABLE op_sendmessage
(
	-- 一键推送id
	send_id bigint NOT NULL AUTO_INCREMENT COMMENT '一键推送id',
	-- 人才政策ID
	policy_id bigint unsigned NOT NULL COMMENT '人才政策ID',
	use_id bigint unsigned,
	-- 用户名
	username char(32) COMMENT '用户名',
	-- 成功推送数量
	success bigint COMMENT '成功推送数量',
	-- 失败推送数量
	failure bigint COMMENT '失败推送数量',
	-- 创建时间
	create_time datetime COMMENT '创建时间',
	PRIMARY KEY (send_id),
	UNIQUE (send_id)
);


ALTER TABLE po_compliance
	ADD FOREIGN KEY (policy_id)
	REFERENCES t_policy (policy_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;
ALTER TABLE po_compliance
	ADD FOREIGN KEY (talent_id)
	REFERENCES t_talent (talent_id)
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





ALTER TABLE t_talent_type
	ADD FOREIGN KEY (talent_id)
	REFERENCES t_talent (talent_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



ALTER TABLE op_sendmessage
	ADD FOREIGN KEY (policy_id)
	REFERENCES t_policy (policy_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



ALTER TABLE op_mess_record
	ADD FOREIGN KEY (send_id)
	REFERENCES op_sendmessage (send_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



ALTER TABLE op_mess_record
	ADD FOREIGN KEY (send_id)
	REFERENCES op_sendmessage (send_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



---


ALTER TABLE t_policy
	ADD FOREIGN KEY (p_tId)
	REFERENCES po_type (p_tId)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;