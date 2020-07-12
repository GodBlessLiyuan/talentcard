
/*权限：所有权限展示*/
INSERT INTO `t_authority`(`authority_id`, `name`) VALUES (1, ''roleQuery'');
INSERT INTO `t_authority`(`authority_id`, `name`) VALUES (2, ''userCreate'');
INSERT INTO `t_authority`(`authority_id`, `name`) VALUES (3, ''userUpdate'');
INSERT INTO `t_authority`(`authority_id`, `name`) VALUES (4, ''userDelete'');
INSERT INTO `t_authority`(`authority_id`, `name`) VALUES (5, ''userQuery'');
INSERT INTO `t_authority`(`authority_id`, `name`) VALUES (6, ''userCardUpdate'');
INSERT INTO `t_authority`(`authority_id`, `name`) VALUES (7, ''userCardDelete'');
INSERT INTO `t_authority`(`authority_id`, `name`) VALUES (8, ''userCardQuery'');
INSERT INTO `t_authority`(`authority_id`, `name`) VALUES (9, ''userCardCreate'');
INSERT INTO `t_authority`(`authority_id`, `name`) VALUES (10, ''generalTalentQuery'');
INSERT INTO `t_authority`(`authority_id`, `name`) VALUES (11, ''verifiedTalentQuery'');
INSERT INTO `t_authority`(`authority_id`, `name`) VALUES (12, ''verifiedTalentUpdate'');
INSERT INTO `t_authority`(`authority_id`, `name`) VALUES (13, ''approvalTalentQuery'');
INSERT INTO `t_authority`(`authority_id`, `name`) VALUES (14, ''approvalTalentAction'');
INSERT INTO `t_authority`(`authority_id`, `name`) VALUES (15, ''policyCreate'');
INSERT INTO `t_authority`(`authority_id`, `name`) VALUES (16, ''policyUpdate'');
INSERT INTO `t_authority`(`authority_id`, `name`) VALUES (17, ''policyDelete'');
INSERT INTO `t_authority`(`authority_id`, `name`) VALUES (18, ''policyQuery'');
INSERT INTO `t_authority`(`authority_id`, `name`) VALUES (19, ''approvalPolicyQuery'');
INSERT INTO `t_authority`(`authority_id`, `name`) VALUES (20, ''approvalPolicyAction'');



/*角色：主要是超级管理员和运营人员*/
INSERT INTO `t_role`(`role_id`, `name`, `extra`, `create_time`) VALUES (1, ''超级管理员'', NULL, ''2020-04-17 14:06:25'');
INSERT INTO `t_role`(`role_id`, `name`, `extra`, `create_time`) VALUES (2, ''运营人员'', NULL, ''2020-04-17 14:06:44'');

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
(1, ''admin'', ''admin'', ''15a162a79e4f467350be58833cdd8c66'', ''2020-04-22 10:57:40'', 1, ''222'', 1);
/*用户：运营人员角色*/
INSERT INTO `t_user`(`user_id`, `username`, `name`, `password`, `create_time`, `dr`, `extra`, `role_id`) VALUES
(2, ''Operation'', ''Operation'', ''15a162a79e4f467350be58833cdd8c66'', ''2020-04-22 16:09:32'', 1, ''fawaikuantu'', 2);


-- 人才学历 中专/高中及以下 1


/*卡 */
INSERT INTO `t_card` VALUES (1, ''pQetQ1T6Hbgvg6_iDyBRRlKvbj8o'', ''衢江区人才卡'', ''衢州有礼 康养衢江'', 1, 3, 0, 5, '''', ''/talentcard//cardBackground/cardBackground1594342874393hSms6HT1.png'', ''http://mmbiz.qpic.cn/sz_mmbiz_png/71tTaLntw0GzM9HPBiaXuMSwKAdm7UDOhIYibb8BrdhZ4721HkticaRuQuREq4TcI6s1MLibHDkr4ePHLNY6EceuNA/0'', ''/talentcard/wx/logo.png'', ''可享受多项人才服务，如人才政策网上兑现、人才公寓申请、免费旅游、酒店农家乐折扣、VIP出行等'', '''', NULL, ''010'', ''系统自动生成'', NULL, NULL, ''2020-7-10 09:01:19'', ''2020-7-10 09:01:19'', 1, 4);
INSERT INTO `t_card` VALUES (2, ''pQetQ1efW8oQDpOgpxiurrvmneE8'', ''衢江区人才卡'', ''衢州有礼 康养衢江'', 2, 1, 0, 1, '''', ''/talentcard//cardBackground/cardBackground1594342889122I7INRidF.png'', ''http://mmbiz.qpic.cn/sz_mmbiz_png/71tTaLntw0GzM9HPBiaXuMSwKAdm7UDOhDLJNpLRMqcAVF8ylCIf9DHAVSY0hiaKEEA5s0swXuJmtuFicaOibBWPzg/0'', ''/talentcard/wx/logo.png'', ''可享受多项人才服务，如人才政策网上兑现、人才公寓申请、免费旅游、酒店农家乐折扣、VIP出行等'', ''C'', NULL, ''010'', ''系统自动生成'', NULL, NULL, ''2020-7-10 09:01:31'', ''2020-7-10 09:01:31'', 1, 4);


/*游客*/
INSERT INTO `t_talent`(`talent_id`, `open_id`, `union_id`, `name`, `sex`, `id_card`, `passport`, `driver_card`, `card_type`, `work_unit`, `industry`, `industry_second`, `phone`, `political`, `category`, `work_location`, `work_location_type`, `card_id`, `status`, `create_time`, `dr`, `talent_source`) VALUES (1, ''000000000000000'', ''000000000000000'', ''游客'', 1, ''000000000000000'', NULL, NULL, NULL, NULL, NULL, NULL, ''00000000000'', NULL, ''100'', NULL, NULL, NULL, 2, NULL, 1, NULL);
INSERT INTO `t_talent` VALUES (2, ''oQetQ1ULYaj1Cg5UwNGbQ2hEGIQQ'', ''oNuP70TlbFydB9GCotoRi71bVdkU'', ''原味鸡'', 1, ''123481181993225211'', '''', '''', 1, ''召唤师峡谷'', 8, 1, ''123'', 2, ''1,2,3'', ''阿斯加德'', 2, 2, 1, ''2020-7-10 09:01:42'', 1, 3);
INSERT INTO `t_talent` VALUES (3, ''oQetQ1SMJgI2-lfQ7Yvm6r2KjOqY'', ''oNuP70dABMHPA7gMY9tyNeflC1EU'', ''崔浩亮'', 1, ''130635198708151233'', '''', '''', 1, ''东南研究院'', 10, NULL, ''123'', 13, NULL, ''浙江省,衢州市,衢江区'', 1, 1, 2, ''2020-7-10 09:06:07'', 1, 1);
INSERT INTO `t_talent` VALUES (4, ''oQetQ1Rn09yebuwWRF1PtGmiRaGU'', ''oNuP70ajndc7BTugliwzsAcZUk0s'', ''黎黎'', 1, ''339808554862210008'', '''', '''', 1, '''', 0, NULL, ''13011245827'', 1, NULL, '''', NULL, 1, 2, ''2020-7-10 09:11:53'', 1, 1);
INSERT INTO `t_talent` VALUES (5, ''oQetQ1Qe4bIp5h7mmsmNOwVXAn3U'', ''oNuP70QdNF0u6TCUMl5EkE5rzlSc'', ''郑加'', 2, ''330821199807125252'', '''', '''', 1, '''', 0, NULL, ''123'', 6, NULL, '''', NULL, 1, 2, ''2020-7-10 09:15:07'', 1, 0);
INSERT INTO `t_talent` VALUES (13, ''gaojiyonghu'', ''oNuP70dABMHPA7gMY9tyNeflC1EU'', ''崔浩亮'', 1, ''130635198708151234'', '''', '''', 1, '''', 0, NULL, ''123'', 1, ''1'', '''', 0, 2, 1, ''2020-7-11 22:06:22'', 1, 0);



INSERT INTO `t_user_card` VALUES (1, 2, 1, ''衢州有礼 康养衢江'', ''010000001'', ''000001'', ''2020-7-10 09:01:42'', 3);
INSERT INTO `t_user_card` VALUES (2, 2, 2, ''衢州有礼 康养衢江'', ''C010000001'', ''000001'', ''2020-7-10 09:02:58'', 2);
INSERT INTO `t_user_card` VALUES (3, 3, 1, ''衢州有礼 康养衢江'', ''010000002'', ''000002'', ''2020-7-10 09:06:07'', 2);
INSERT INTO `t_user_card` VALUES (4, 4, 1, ''衢州有礼 康养衢江'', ''010000003'', ''000003'', ''2020-7-10 09:11:54'', 2);
INSERT INTO `t_user_card` VALUES (5, 5, 1, ''衢州有礼 康养衢江'', ''010000004'', ''000004'', ''2020-7-10 09:15:08'', 2);
INSERT INTO `t_user_card` VALUES (6, 13, 1, ''衢州有礼 康养衢江'', ''010000005'', ''000005'', ''2020-7-11 22:06:23'', 3);
INSERT INTO `t_user_card` VALUES (7, 13, 2, ''活力衢江衢江人才卡'', ''Q010000005'', ''000005'', ''2020-7-11 22:07:03'', 2);

INSERT INTO `t_user_current_info` VALUES (1, 1, 100, 100, ''100'', 100, ''100'', 100, ''100'', 100, ''100'', ''100'', NULL, NULL, NULL);
INSERT INTO `t_user_current_info` VALUES (2, 2, 2, 1, ''测试测试测试'', 1, ''测试测试测试'', 1, ''测试测试测试'', 1, ''测试测试测试'', ''1,2,3'', 1, NULL, ''2020-08-05'');
INSERT INTO `t_user_current_info` VALUES (3, 3, 13, 0, '''', NULL, '''', 0, '''', 0, '''', NULL, 1, NULL, '''');
INSERT INTO `t_user_current_info` VALUES (4, 4, 1, 6, ''jgv'', 1, ''jgv'', 0, '''', 0, '''', NULL, 0, NULL, ''2020-06-01'');
INSERT INTO `t_user_current_info` VALUES (5, 5, 6, 6, ''公众号填写学校'', 2, ''公众号填写专业'', 0, '''', 0, '''', NULL, 0, NULL, ''2017-07-10'');
INSERT INTO `t_user_current_info` VALUES (6, 13, 1, 6, ''北京邮电大学'', 1, ''计算机科学与技术'', 0, '''', 0, '''', ''1'', 0, NULL, ''2020-07-11'');



INSERT INTO `t_certification` VALUES (1, 2, 2, ''2020-7-10 09:01:42'', 9, NULL, 1);
INSERT INTO `t_certification` VALUES (2, 2, NULL, ''2020-7-10 09:02:08'', 10, 4, 2);
INSERT INTO `t_certification` VALUES (3, 2, NULL, ''2020-7-10 09:02:44'', 1, 4, 2);
INSERT INTO `t_certification` VALUES (4, 3, 13, ''2020-7-10 09:06:07'', 5, NULL, 1);
INSERT INTO `t_certification` VALUES (5, 4, 1, ''2020-7-10 09:11:54'', 5, NULL, 1);
INSERT INTO `t_certification` VALUES (6, 5, 6, ''2020-7-10 09:15:08'', 5, NULL, 1);


INSERT INTO `t_education` VALUES (1, 5, ''战争学院'', 1, ''上单霸主'', NULL, 1, 2, 9, 10, ''2020-08-05'');
INSERT INTO `t_education` VALUES (2, 1, ''测试测试测试'', 1, ''测试测试测试'', ''/talentcard//education/education1594342928454QrlJQ8Or.'', 2, 2, 10, 2, NULL);
INSERT INTO `t_education` VALUES (4, 0, '''', NULL, '''', NULL, 4, 3, 5, 10, '''');
INSERT INTO `t_education` VALUES (5, 6, ''jgv'', 1, ''jgv'', NULL, 5, 4, 5, 10, ''2020-06-01'');
INSERT INTO `t_education` VALUES (6, 6, ''公众号填写学校'', 2, ''公众号填写专业'', NULL, 6, 5, 5, 10, ''2017-07-10'');


INSERT INTO `t_prof_quality` VALUES (1, 0, NULL, '''', 1, 2, 9, 10);
INSERT INTO `t_prof_quality` VALUES (2, 1, ''/talentcard//profQuality/profQuality1594342928456LuDEAqGv.'', ''测试测试测试'', 2, 2, 10, 2);
INSERT INTO `t_prof_quality` VALUES (4, 0, NULL, '''', 4, 3, 5, 10);
INSERT INTO `t_prof_quality` VALUES (5, 0, NULL, '''', 5, 4, 5, 10);
INSERT INTO `t_prof_quality` VALUES (6, 0, NULL, '''', 6, 5, 5, 10);

INSERT INTO `t_prof_title` VALUES (1, 0, '''', NULL, 1, 2, 9, 10);
INSERT INTO `t_prof_title` VALUES (2, 1, ''测试测试测试'', ''/talentcard//profTitle/profTitle1594342928456BSRb47hP.'', 2, 2, 10, 2);
INSERT INTO `t_prof_title` VALUES (4, 0, '''', NULL, 4, 3, 5, 10);
INSERT INTO `t_prof_title` VALUES (5, 0, '''', NULL, 5, 4, 5, 10);
INSERT INTO `t_prof_title` VALUES (6, 0, '''', NULL, 6, 5, 5, 10);

INSERT INTO `t_talent_honour` VALUES (1, 10, NULL, NULL, 1, 2, 9, 10);
INSERT INTO `t_talent_honour` VALUES (2, 1, ''/talentcard//talentHonourDir/talentHonour1594342928456l5Qa8HNk.png'', NULL, 2, 2, 10, 2);
INSERT INTO `t_talent_honour` VALUES (4, 1, NULL, NULL, 4, 3, 5, 10);
INSERT INTO `t_talent_honour` VALUES (5, 0, NULL, NULL, 5, 4, 5, 10);
INSERT INTO `t_talent_honour` VALUES (6, 0, NULL, NULL, 6, 5, 5, 10);




INSERT INTO `t_certification` VALUES (20, 13, 1, ''2020-7-11 22:06:23'', 9, NULL, 1);
INSERT INTO `t_certification` VALUES (21, 13, NULL, ''2020-7-11 22:06:40'', 1, 4, 2);

INSERT INTO `t_cert_approval` VALUES (1, 21, ''2020-7-11 22:06:40'', 1, NULL, NULL, NULL, ''2020-7-11 22:07:04'', NULL, NULL);
INSERT INTO `t_cert_approval` VALUES (2, 21, ''2020-7-11 22:07:03'', 2, 2, ''1'', 1, ''2020-7-11 22:07:03'', 1, ''test'');



INSERT INTO `t_talent_json_record` VALUES (1, ''{\"talentCertificationRecordVO\":{\"certApprovalPO\":{\"category\":\"1,2,3\",\"certId\":2,\"createTime\":1594342954442,\"opinion\":\"aaa\",\"result\":2,\"type\":2,\"updateTime\":1594342954442},\"certId\":2,\"educationPO\":{\"certId\":2,\"educId\":2,\"educPicture\":\"/talentcard//education/education1594342928454QrlJQ8Or.\",\"education\":1,\"firstClass\":1,\"ifCertificate\":2,\"major\":\"测试测试测试\",\"school\":\"测试测试测试\",\"status\":10,\"talentId\":2},\"profQualityPO\":{\"category\":1,\"certId\":2,\"ifCertificate\":2,\"info\":\"测试测试测试\",\"picture\":\"/talentcard//profQuality/profQuality1594342928456LuDEAqGv.\",\"pqId\":2,\"status\":10,\"talentId\":2},\"profTitlePO\":{\"category\":1,\"certId\":2,\"ifCertificate\":2,\"info\":\"测试测试测试\",\"picture\":\"/talentcard//profTitle/profTitle1594342928456BSRb47hP.\",\"ptId\":2,\"status\":10,\"talentId\":2},\"talentHonourPO\":{\"certId\":2,\"honourId\":1,\"honourPicture\":\"/talentcard//talentHonourDir/talentHonour1594342928456l5Qa8HNk.png\",\"ifCertificate\":2,\"status\":10,\"talentId\":2,\"thId\":2}},\"type\":1}'', ''oQetQ1ULYaj1Cg5UwNGbQ2hEGIQQ'', ''2020-7-10 09:02:35'');
INSERT INTO `t_talent_json_record` VALUES (2, ''{\"talentCertificationRecordVO\":{\"certApprovalPO\":{\"cardId\":2,\"category\":\"1,2,3\",\"certId\":3,\"createTime\":1594342978186,\"opinion\":\"aaa\",\"result\":1,\"type\":2,\"updateTime\":1594342978186},\"certId\":3,\"educationPO\":{\"certId\":3,\"educId\":3,\"educPicture\":\"/talentcard//education/education1594342964082yHzsHNek.\",\"education\":1,\"firstClass\":1,\"ifCertificate\":1,\"major\":\"测试测试测试\",\"school\":\"测试测试测试\",\"status\":4,\"talentId\":2},\"profQualityPO\":{\"category\":1,\"certId\":3,\"ifCertificate\":1,\"info\":\"测试测试测试\",\"picture\":\"/talentcard//profQuality/profQuality1594342964083sswuttpa.\",\"pqId\":3,\"status\":4,\"talentId\":2},\"profTitlePO\":{\"category\":1,\"certId\":3,\"ifCertificate\":1,\"info\":\"测试测试测试\",\"picture\":\"/talentcard//profTitle/profTitle1594342964083wDIn4ZtN.\",\"ptId\":3,\"status\":4,\"talentId\":2},\"talentHonourPO\":{\"certId\":3,\"honourId\":1,\"honourPicture\":\"/talentcard//talentHonourDir/talentHonour1594342964083wMF2Ha7N.png\",\"ifCertificate\":1,\"status\":4,\"talentId\":2,\"thId\":3}},\"type\":1}'', ''oQetQ1ULYaj1Cg5UwNGbQ2hEGIQQ'', ''2020-7-10 09:02:59'');

INSERT INTO `t_cert_examine_record` VALUES (1, 2, 2, ''原味鸡'', 1, 1, 1, 1, 1, 2, ''2020-7-10 09:02:08'');
INSERT INTO `t_cert_examine_record` VALUES (2, 2, 3, ''原味鸡'', 1, 1, 1, 1, 1, 1, ''2020-7-10 09:02:44'');



INSERT INTO t_talent_certification_info (`tci_id`, `talent_id`, `education`, `pt_category`, `pq_category`, `talent_category`, `honour_id`) VALUES (''1'', ''13'', '''', ''0'', ''0'', ''1'', ''0'');

-- INSERT INTO `t_edit_talent_record` VALUES (1, 2, NULL, 2, 1, ''2020-7-10 09:03:31'', '''');
-- INSERT INTO `t_edit_talent_record` VALUES (2, 2, NULL, 2, 2, ''2020-7-10 09:03:33'', '''');
-- INSERT INTO `t_edit_talent_record` VALUES (3, 2, NULL, 2, 3, ''2020-7-10 09:03:35'', '''');
-- INSERT INTO `t_edit_talent_record` VALUES (4, 2, NULL, 2, 4, ''2020-7-10 09:03:37'', '''');
--
--


