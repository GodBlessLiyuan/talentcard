SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS t_annex;
DROP TABLE IF EXISTS t_role_authority;
DROP TABLE IF EXISTS t_authority;
DROP TABLE IF EXISTS t_bank;
DROP TABLE IF EXISTS t_cert_approval;
DROP TABLE IF EXISTS t_education;
DROP TABLE IF EXISTS t_prof_quality;
DROP TABLE IF EXISTS t_prof_title;
DROP TABLE IF EXISTS t_certification;
DROP TABLE IF EXISTS t_policy_approval;
DROP TABLE IF EXISTS t_policy_apply;
DROP TABLE IF EXISTS t_user_card;
DROP TABLE IF EXISTS t_user_current_info;
DROP TABLE IF EXISTS t_talent;
DROP TABLE IF EXISTS t_card;
DROP TABLE IF EXISTS t_farmhouse_group_authority;
DROP TABLE IF EXISTS t_policy;
DROP TABLE IF EXISTS t_user;
DROP TABLE IF EXISTS t_role;
DROP TABLE IF EXISTS t_scenic_enjoy;
DROP TABLE IF EXISTS t_scenic_picture;
DROP TABLE IF EXISTS t_scenic;
DROP TABLE IF EXISTS t_staff_farmhouse;
DROP TABLE IF EXISTS t_staff_trip;
DROP TABLE IF EXISTS t_talent_farmhouse;
DROP TABLE IF EXISTS t_talent_trip;
DROP TABLE IF EXISTS t_trip_group_authority;




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
    initial_num char(32) NOT NULL,
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
    -- 4.已有卡，且审批通过但未领卡（待领卡）
    -- 5.基础卡正常使用
    -- 9. 基本卡失效
    -- 10.其他情况失效
    status tinyint DEFAULT 2 COMMENT '1.正常使用
2.注册没领卡（待领卡）
3.发起过认证未审批（待审批）
4.已有卡，且审批通过但未领卡（待领卡）
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
    -- 4.已有卡，且审批通过但未领卡（待领卡）
    -- 5.基础卡正常使用
    -- 9. 基本卡失效
    -- 10.其他情况失效
    status tinyint COMMENT '1.正常使用
2.注册没领卡（待领卡）
3.发起过认证未审批（待审批）
4.已有卡，且审批通过但未领卡（待领卡）
5.基础卡正常使用
9. 基本卡失效
10.其他情况失效',
    PRIMARY KEY (educ_id),
    UNIQUE (educ_id)
);


CREATE TABLE t_farmhouse_group_authority
(

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
    -- 1：需要；2：不需要
    apply tinyint DEFAULT 2 COMMENT '1：需要；2：不需要',
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
    -- 4.已有卡，且审批通过但未领卡（待领卡）
    -- 5.基础卡正常使用
    -- 9. 基本卡失效
    -- 10.其他情况失效
    status tinyint COMMENT '1.正常使用
2.注册没领卡（待领卡）
3.发起过认证未审批（待审批）
4.已有卡，且审批通过但未领卡（待领卡）
5.基础卡正常使用
9. 基本卡失效
10.其他情况失效',
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
    -- 4.已有卡，且审批通过但未领卡（待领卡）
    -- 5.基础卡正常使用
    -- 9. 基本卡失效
    -- 10.其他情况失效
    status tinyint COMMENT '1.正常使用
2.注册没领卡（待领卡）
3.发起过认证未审批（待审批）
4.已有卡，且审批通过但未领卡（待领卡）
5.基础卡正常使用
9. 基本卡失效
10.其他情况失效',
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
    times tinyint,
    avatar char(255),
    desc char(255),
    extra char(255),
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
    sg_id bigint unsigned NOT NULL AUTO_INCREMENT,
    scenic_id bigint unsigned NOT NULL,
    card_id bigint unsigned,
    category_id bigint unsigned,
    education_id bigint unsigned,
    title_id bigint unsigned,
    quality bigint unsigned,
    -- 1：人才卡；2：人才类别；3：人才学历；4：职称；5：职业资格
    type tinyint COMMENT '1：人才卡；2：人才类别；3：人才学历；4：职称；5：职业资格',
    PRIMARY KEY (sg_id),
    UNIQUE (sg_id)
);


CREATE TABLE t_scenic_picture
(
    sp_id bigint unsigned NOT NULL AUTO_INCREMENT,
    scenic_id bigint unsigned NOT NULL,
    picture char(255),
    PRIMARY KEY (sp_id),
    UNIQUE (sp_id),
    UNIQUE (scenic_id)
);


CREATE TABLE t_staff_farmhouse
(

);


CREATE TABLE t_staff_trip
(

);


CREATE TABLE t_talent
(
    talent_id bigint unsigned NOT NULL AUTO_INCREMENT,
    open_id char(128) NOT NULL,
    name char(64) NOT NULL,
    -- 1：男；2：女
    sex tinyint COMMENT '1：男；2：女',
    id_card char(18) NOT NULL,
    passport char(32),
    work_unit char(255) NOT NULL,
    industry int unsigned,
    industry_second int unsigned,
    phone char(32) NOT NULL,
    create_time datetime,
    category char(255),
    -- 1 认证通过
    -- 2 认证没通过
    status tinyint DEFAULT 2 COMMENT '1 认证通过
2 认证没通过',
    card_id bigint unsigned,
    -- 1正在使用
    -- 2删除
    dr tinyint unsigned COMMENT '1正在使用
2删除',
    PRIMARY KEY (talent_id),
    UNIQUE (talent_id),
    UNIQUE (id_card)
);


CREATE TABLE t_talent_farmhouse
(

);


CREATE TABLE t_talent_trip
(
    tt_id bigint unsigned NOT NULL AUTO_INCREMENT,
    open_id char(128) NOT NULL,
    PRIMARY KEY (tt_id),
    UNIQUE (tt_id),
    UNIQUE (open_id)
);


CREATE TABLE t_trip_group_authority
(

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


ALTER TABLE t_user_current_info
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



