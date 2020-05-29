
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


-- 人才学历 中专/高中及以下 1

INSERT INTO `t_policy` VALUES ('1', '政策1', 'D000001', '水电站官方阿萨德', '', '', '', '', '', '1', '1', '#1677FF', '1', '1', '1', '1', '2', null, '2020-05-28 10:58:29', '1');
INSERT INTO `t_policy` VALUES ('2', '政策1', 'E00000006', '新增反反复复付测试', '', '', '', '', '', '1', '1', '#FF8F1F', '1', '1', '1', '1', '1', null, '2020-05-28 11:02:57', '1');
