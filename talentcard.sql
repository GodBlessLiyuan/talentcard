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
DROP TABLE IF EXISTS t_policy;
DROP TABLE IF EXISTS t_user;
DROP TABLE IF EXISTS t_role;




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
    name char(16),
    brand_name char(16) NOT NULL,
    title char(32) NOT NULL,
    curr_num bigint DEFAULT 0,
    description varchar(2048) NOT NULL,
    picture char(255) NOT NULL,
    picture_cdn char(255),
    prerogative varchar(2048),
    initial_word char(32),
    initial_num char(32) NOT NULL,
    create_time datetime,
    -- 1：默认；2：非默认
    status tinyint COMMENT '1：默认；2：非默认',
    PRIMARY KEY (card_id),
    UNIQUE (card_id)
);


CREATE TABLE t_certification
(
    cert_id bigint unsigned NOT NULL AUTO_INCREMENT,
    talent_id bigint unsigned,
    political tinyint,
    create_time datetime,
    -- 1 正常使用
    -- 2 注册未认证
    -- 3 认证未审批/待审批
    -- 4 已有卡，且审批通过但未领卡
    -- 5 未注册
    -- 6 失效
    status tinyint DEFAULT 5 COMMENT '1 正常使用
2 注册未认证
3 认证未审批/待审批
4 已有卡，且审批通过但未领卡
5 未注册
6 失效',
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
    -- 1 正常使用
    -- 2 注册未认证
    -- 3 认证未审批/待审批
    -- 4 已有卡，且审批通过但未领卡
    -- 5 未注册
    -- 6 失效
    status tinyint COMMENT '1 正常使用
2 注册未认证
3 认证未审批/待审批
4 已有卡，且审批通过但未领卡
5 未注册
6 失效',
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
    info char(255),
    picture char(255),
    cert_id bigint unsigned NOT NULL,
    talent_id bigint unsigned NOT NULL,
    -- 1 正常使用
    -- 2 注册未认证
    -- 3 认证未审批/待审批
    -- 4 已有卡，且审批通过但未领卡
    -- 5 未注册
    -- 6 失效
    status tinyint COMMENT '1 正常使用
2 注册未认证
3 认证未审批/待审批
4 已有卡，且审批通过但未领卡
5 未注册
6 失效',
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
    -- 1 正常使用
    -- 2 注册未认证
    -- 3 认证未审批/待审批
    -- 4 已有卡，且审批通过但未领卡
    -- 5 未注册
    -- 6 失效
    status tinyint COMMENT '1 正常使用
2 注册未认证
3 认证未审批/待审批
4 已有卡，且审批通过但未领卡
5 未注册
6 失效',
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
    industry char(255) NOT NULL,
    phone char(32) NOT NULL,
    create_time datetime,
    category char(255),
    -- 1 正常使用
    -- 2 注册未认证
    -- 3 认证未审批/待审批
    -- 4 已有卡，且审批通过但未领卡
    -- 5 未注册
    -- 6 失效
    -- 用户当前的卡套号，状态2、3为基本卡；状态4为要换的新卡、状态1为当前持有卡；状态5、6无卡
    status tinyint DEFAULT 5 COMMENT '1 正常使用
2 注册未认证
3 认证未审批/待审批
4 已有卡，且审批通过但未领卡
5 未注册
6 失效
用户当前的卡套号，状态2、3为基本卡；状态4为要换的新卡、状态1为当前持有卡；状态5、6无卡',
    card_id bigint unsigned,
    PRIMARY KEY (talent_id),
    UNIQUE (talent_id),
    UNIQUE (id_card)
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
    -- 1 待审批或待激活
    -- 2 已激活，使用中
    -- 3 废弃
    status tinyint DEFAULT 1 COMMENT '1 待审批或待激活
2 已激活，使用中
3 废弃',
    PRIMARY KEY (uc_id),
    UNIQUE (uc_id),
    UNIQUE (num)
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



