SET SESSION FOREIGN_KEY_CHECKS = 0;

use talentcard;

/*Truncate Tables*/
TRUNCATE TABLE t_user;
TRUNCATE TABLE t_role;
TRUNCATE TABLE t_authority;
TRUNCATE TABLE t_role_authority;

/*权限：所有权限展示*/
INSERT INTO `t_authority`(`authority_id`, `name`) VALUES (1, 'roleQuery');
INSERT INTO `t_authority`(`authority_id`, `name`) VALUES (2, 'userCreate');
INSERT INTO `t_authority`(`authority_id`, `name`) VALUES (3, 'userUpdate');
INSERT INTO `t_authority`(`authority_id`, `name`) VALUES (4, 'userDelete');
INSERT INTO `t_authority`(`authority_id`, `name`) VALUES (5, 'userQuery');
INSERT INTO `t_authority`(`authority_id`, `name`) VALUES (6, 'userCardUpdate');
INSERT INTO `t_authority`(`authority_id`, `name`) VALUES (7, 'userCardDelete');
INSERT INTO `t_authority`(`authority_id`, `name`) VALUES (8, 'userCardQuery');
INSERT INTO `t_authority`(`authority_id`, `name`) VALUES (9, 'userCardCreate');
INSERT INTO `t_authority`(`authority_id`, `name`) VALUES (10, 'generalTalentQuery');
INSERT INTO `t_authority`(`authority_id`, `name`) VALUES (11, 'verifiedTalentQuery');
INSERT INTO `t_authority`(`authority_id`, `name`) VALUES (12, 'verifiedTalentUpdate');
INSERT INTO `t_authority`(`authority_id`, `name`) VALUES (13, 'approvalTalentQuery');
INSERT INTO `t_authority`(`authority_id`, `name`) VALUES (14, 'approvalTalentAction');
INSERT INTO `t_authority`(`authority_id`, `name`) VALUES (15, 'policyCreate');
INSERT INTO `t_authority`(`authority_id`, `name`) VALUES (16, 'policyUpdate');
INSERT INTO `t_authority`(`authority_id`, `name`) VALUES (17, 'policyDelete');
INSERT INTO `t_authority`(`authority_id`, `name`) VALUES (18, 'policyQuery');
INSERT INTO `t_authority`(`authority_id`, `name`) VALUES (19, 'approvalPolicyQuery');
INSERT INTO `t_authority`(`authority_id`, `name`) VALUES (20, 'approvalPolicyAction');



/*角色：主要是超级管理员和运营人员*/
INSERT INTO `t_role`(`role_id`, `name`, `extra`, `create_time`) VALUES (1, '超级管理员', NULL, '2020-04-17 14:06:25');
INSERT INTO `t_role`(`role_id`, `name`, `extra`, `create_time`) VALUES (2, '运营人员', NULL, '2020-04-17 14:06:44');

/*角色权限表*/
INSERT INTO `t_role_authority`(`ra_id`, `status`, `authority_id`, `role_id`) VALUES (1, 1, 1, 1);
INSERT INTO `t_role_authority`(`ra_id`, `status`, `authority_id`, `role_id`) VALUES (2, 1, 2, 1);
INSERT INTO `t_role_authority`(`ra_id`, `status`, `authority_id`, `role_id`) VALUES (3, 1, 3, 1);
INSERT INTO `t_role_authority`(`ra_id`, `status`, `authority_id`, `role_id`) VALUES (4, 1, 4, 1);
INSERT INTO `t_role_authority`(`ra_id`, `status`, `authority_id`, `role_id`) VALUES (5, 1, 5, 1);
INSERT INTO `t_role_authority`(`ra_id`, `status`, `authority_id`, `role_id`) VALUES (6, 1, 6, 1);
INSERT INTO `t_role_authority`(`ra_id`, `status`, `authority_id`, `role_id`) VALUES (7, 1, 7, 1);
INSERT INTO `t_role_authority`(`ra_id`, `status`, `authority_id`, `role_id`) VALUES (8, 1, 8, 1);
INSERT INTO `t_role_authority`(`ra_id`, `status`, `authority_id`, `role_id`) VALUES (9, 1, 9, 1);
INSERT INTO `t_role_authority`(`ra_id`, `status`, `authority_id`, `role_id`) VALUES (10, 1, 10, 1);
INSERT INTO `t_role_authority`(`ra_id`, `status`, `authority_id`, `role_id`) VALUES (11, 1, 11, 1);
INSERT INTO `t_role_authority`(`ra_id`, `status`, `authority_id`, `role_id`) VALUES (12, 1, 12, 1);
INSERT INTO `t_role_authority`(`ra_id`, `status`, `authority_id`, `role_id`) VALUES (13, 1, 13, 1);
INSERT INTO `t_role_authority`(`ra_id`, `status`, `authority_id`, `role_id`) VALUES (14, 1, 14, 1);
INSERT INTO `t_role_authority`(`ra_id`, `status`, `authority_id`, `role_id`) VALUES (15, 1, 15, 1);
INSERT INTO `t_role_authority`(`ra_id`, `status`, `authority_id`, `role_id`) VALUES (16, 1, 16, 1);
INSERT INTO `t_role_authority`(`ra_id`, `status`, `authority_id`, `role_id`) VALUES (17, 1, 17, 1);
INSERT INTO `t_role_authority`(`ra_id`, `status`, `authority_id`, `role_id`) VALUES (18, 1, 18, 1);
INSERT INTO `t_role_authority`(`ra_id`, `status`, `authority_id`, `role_id`) VALUES (19, 1, 19, 1);
INSERT INTO `t_role_authority`(`ra_id`, `status`, `authority_id`, `role_id`) VALUES (20, 1, 20, 1);
INSERT INTO `t_role_authority`(`ra_id`, `status`, `authority_id`, `role_id`) VALUES (22, 1, 2, 2);
INSERT INTO `t_role_authority`(`ra_id`, `status`, `authority_id`, `role_id`) VALUES (23, 1, 3, 2);
INSERT INTO `t_role_authority`(`ra_id`, `status`, `authority_id`, `role_id`) VALUES (24, 1, 4, 2);
INSERT INTO `t_role_authority`(`ra_id`, `status`, `authority_id`, `role_id`) VALUES (25, 1, 5, 2);
INSERT INTO `t_role_authority`(`ra_id`, `status`, `authority_id`, `role_id`) VALUES (26, 1, 6, 2);
INSERT INTO `t_role_authority`(`ra_id`, `status`, `authority_id`, `role_id`) VALUES (27, 1, 7, 2);
INSERT INTO `t_role_authority`(`ra_id`, `status`, `authority_id`, `role_id`) VALUES (28, 1, 8, 2);
INSERT INTO `t_role_authority`(`ra_id`, `status`, `authority_id`, `role_id`) VALUES (29, 1, 9, 2);
INSERT INTO `t_role_authority`(`ra_id`, `status`, `authority_id`, `role_id`) VALUES (30, 1, 10, 2);
INSERT INTO `t_role_authority`(`ra_id`, `status`, `authority_id`, `role_id`) VALUES (31, 1, 11, 2);
INSERT INTO `t_role_authority`(`ra_id`, `status`, `authority_id`, `role_id`) VALUES (32, 1, 12, 2);
INSERT INTO `t_role_authority`(`ra_id`, `status`, `authority_id`, `role_id`) VALUES (33, 1, 13, 2);
INSERT INTO `t_role_authority`(`ra_id`, `status`, `authority_id`, `role_id`) VALUES (34, 1, 14, 2);
INSERT INTO `t_role_authority`(`ra_id`, `status`, `authority_id`, `role_id`) VALUES (35, 1, 15, 2);
INSERT INTO `t_role_authority`(`ra_id`, `status`, `authority_id`, `role_id`) VALUES (36, 1, 16, 2);
INSERT INTO `t_role_authority`(`ra_id`, `status`, `authority_id`, `role_id`) VALUES (37, 1, 17, 2);
INSERT INTO `t_role_authority`(`ra_id`, `status`, `authority_id`, `role_id`) VALUES (38, 1, 18, 2);
INSERT INTO `t_role_authority`(`ra_id`, `status`, `authority_id`, `role_id`) VALUES (39, 1, 19, 2);
INSERT INTO `t_role_authority`(`ra_id`, `status`, `authority_id`, `role_id`) VALUES (40, 1, 20, 2);

/*用户：超级管理员角色*/
INSERT INTO `t_user`(`user_id`, `username`, `name`, `password`, `create_time`, `dr`, `extra`, `role_id`) VALUES
(1, 'admin', 'admin', '15a162a79e4f467350be58833cdd8c66', '2020-04-22 10:57:40', 1, '222', 1);
/*用户：运营人员角色*/
INSERT INTO `t_user`(`user_id`, `username`, `name`, `password`, `create_time`, `dr`, `extra`, `role_id`) VALUES
(2, 'Operation', 'Operation', '15a162a79e4f467350be58833cdd8c66', '2020-04-22 16:09:32', 1, 'fawaikuantu', 2);

# INSERT INTO `t_policy` VALUES (1,'A类人才政策','QJZC000001','中国科学院、中国工程院院士等顶尖人才（A类人才，具体类型详见附件），给予为期五年每人每年60万元的购房补贴，一次性安家补助100万元，每年发放12万元人才津贴；', '','','','','',1,1,1,1,1,1,NULL,'2020-04-30 09:00:48',1),
#                               (2,'B类人才政策','QJZC000002','“长江学者奖励计划”教授等国家级领军人才（B类人才，不含国家“千人计划”人才），给予为期五年每人每年40万元的购房补贴，一次性安家补助60万元，每年发放6万元人才津贴；','','','','','',1,1,1,1,1,1,NULL,'2020-04-30 09:29:12',1),
#                               (3,'C类人才政策','QJZC000003','省“钱江学者”特聘教授等省级领军人才（C类人才，不含省“千人计划”人才），给予为期五年每人每年30万元的购房补贴，一次性安家补助45万元，每年发放4万元人才津贴； ','','','','','',1,1,1,1,1,1,1,'2020-04-30 10:43:46',1),
#                               (4,'省“千人计划”政策','QJZC000004','在衢江区申报入选国家、省“千人计划”人才，分别给予每人200万元、100万元的一次性配套奖励，每年分别发放6万元、4万元的人才津贴。对携带项目来衢江区转化并产业化或与用人单位签订五年及以上服务合同的国家、省“千人计划”人才，分别给予每人80万元、40万元的一次性奖励（与高层次创业人才创业启动补助不重复享受），每年分别发放6万元、4万元的人才津贴； ','','','','','',1,1,1,1,2,1,1,'2020-04-30 10:47:36',1),
#                               (5,'中医类人才政策','QJZC000005','各类中医针灸类院士、国家级名中医、国医大师、国家级中医针灸传承人等的全日制普通高校博士研究生以上亲传弟子及同级别人才，给予每人一次性安家补助30万元、购房补贴20万元，每年发放3万元人才津贴；','','','','','',1,1,1,1,1,2,1,'2020-04-30 10:49:07',1),
#                               (6,'省功勋类人才政策','QJZC000006','省功勋教师（省杰出教师）、省特级教师、省名中医（专家）、文化名家，给予每人一次性安家补助30万元，每年发放3万元人才津贴； ','4','','','','',1,1,1,1,1,1,1,'2020-04-30 10:50:56',1),
#                               (7,'紧缺专业技术人才政策','QJZC000007','紧缺的55岁及以下的具有正高级专业技术职务人员或45岁及以下的全日制普通高校博士研究生，给予每人一次性安家补助20万元，每年分别发放2万元、1.5万元人才津贴；','','','','','',1,1,1,1,1,1,1,'2020-04-30 10:53:14',1),
#                               (8,'急需专业人才政策','QJZC000008','急需的55岁及以下的高级技师、45岁及以下的具有副高级专业技术职务人员、35岁及以下的“双一流”院校（含原“211”或“985”院校，不含二级学院）全日制普通高校硕士研究生，给予一次性安家补助5万元； ','','','','','',1,1,1,1,1,1,1,'2020-04-30 10:55:34',1),
#                               (9,'紧缺专业技术人才政策','QZJC000009','紧缺专业的45岁及以下具有中级专业技术职务人员或工业企业技师职业任职资格人员、35岁及以下的“双一流”院校（含原“211”或“985”院校，不含二级学院）本科学历并获学士学位的全日制普通高校毕业生，给予一次性安家补助1万元；','','','','','',1,1,1,1,2,1,1,'2020-04-30 10:57:33',1),
#                               (10,'高端领军人才政策','QJZC000010','支持高端领军人才来衢江创业。对符合衢江区产业发展导向或高新技术领域的高层次人才创业项目，经评审认定，按不同等次，给予200万元、400万元、600万元的创业启动补助，并给予三年内合计不超过500万元银行同期贷款基准利率的全额贴息补助等方面支持。对入选浙江省领军型创新创业团队的，首个资助期内，按照省级财政投入额度给予1:1配套资助。对新引进的具有世界先进、国内领先技术的人才及团队，其领衔的产业项目在衢江落户，经“一事一议”，可给予最低2000万元资助，不设上限。 ','1','','','','',1,1,1,1,2,1,1,'2020-04-30 10:59:22',1),
#                               (11,'海外人才政策','QJZC000011','对引进急需紧缺的海外教授级专家或留学归国博士研究生并签订五年及以上服务合同的，授予“引才贡献奖”，并给予用人单位一次性5万元奖励。依托商会、学会等组织，在国内外设立人才工作站，聘请引才顾问，在协议期内给予每年最高15万元工作经费补助。 ','','','','','',1,1,1,1,1,1,1,'2020-04-30 11:00:54',1),
#                               (12,'柔性人才引进政策','QJZC000012','鼓励科技研发外联外包。支持企业与企业、高校、专业科研院所和专家团队合作进行技术攻关，对双方签订技术合同，并在全国技术合同网上认定登记系统登记和向买卖双方所在地科技部门报备，项目符合衢江产业导向并实现产业化的，经有关部门审核，按实际支付技术费用金额的20%予以补助，每家企业每年不超过20万元；对参加政府主办的科技成果竞价（拍卖）会竞拍、难题招标、创新挑战赛等活动，成交并签订科技成果转让或科技合作协议的，经区主管部门确定，按实际支付技术费用金额的30%予以补助（与技术合同登记补助不重复享受），单个项目补助最高不超过30万元。对通过自主科技成果技术入股占30%以上的，在项目建成验收后，可连续三年给予其贡献50%的奖励，每年最高不超过50万元。','','','','','',1,1,1,1,1,1,1,'2020-04-30 11:02:26',1),
#                               (13,'本土人才培养政策','QJZC000013','（1）入选顶尖人才的（A类），每人一次性400万元奖励，每年发放12万元人才津贴； \n（2）入选国家级领军人才（B类）的国家“万人计划”中除杰出人才以外的人选、国家有突出贡献的中青年专家、百千万人才工程国家级人选、中科院“百人计划”A类人才、何梁何利科技奖获得者、“长江学者奖励计划”教授、国家杰出青年基金项目完成人、国家科学技术一等奖获得者（前三位完成人）、省科学技术重大贡献奖获得者、省领军型创新创业团队带头人或同等次其他人才，每人一次性100万元奖励，每年发放6万元人才津贴； \n（3）入选国家级领军人才（B类）的省特级专家、省级领军人才（C类）的“钱江学者”特聘教授，每人一次性50万元奖励，每年发放4万元人才津贴；对入选国家或浙江省“万人计划”的，分别给予1：1配套奖励； （4）入选省级领军人才（C类人才）的省有突出贡献的中青年专家、中科院“百人计划”B类人才、省级重点创新团队带头人、国务院特殊津贴专家、国家科学技术二等奖、省科学技术奖一等奖获得者（前三位完成人）或同层次其他人才，每人一次性10万元奖励，每年发放3万元人才津贴； ','','7,6','1','','',1,1,1,1,1,2,1,'2020-04-30 11:04:01',1);

/*游客*/
INSERT INTO `talentcard`.`t_talent`(`talent_id`, `open_id`, `union_id`, `name`, `sex`, `id_card`, `passport`, `driver_card`, `card_type`, `work_unit`, `industry`, `industry_second`, `phone`, `political`, `category`, `work_location`, `work_location_type`, `card_id`, `status`, `create_time`, `dr`, `talent_source`) VALUES (1, '000000000000000', '000000000000000', '游客', 1, '000000000000000', NULL, NULL, NULL, NULL, NULL, NULL, '00000000000', NULL, '100', NULL, NULL, NULL, 2, NULL, 1, NULL);

INSERT INTO `talentcard`.`t_user_current_info`(`uci_id`, `talent_id`, `political`, `education`, `school`, `first_class`, `major`, `pt_category`, `pt_info`, `pq_category`, `pq_info`, `talent_category`, `honour_id`, `th_info`, `graduate_time`) VALUES (1, 1, 100, 100, '100', 100, '100', 100, '100', 100, '100', '100', NULL, NULL, NULL);
