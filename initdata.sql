SET SESSION FOREIGN_KEY_CHECKS = 0;

use talentcard;

/*Truncate Tables*/
TRUNCATE TABLE t_user;
TRUNCATE TABLE t_role;
TRUNCATE TABLE t_authority;
TRUNCATE TABLE t_role_authority;


/*用户：超级管理员角色*/
INSERT INTO `t_user`(`user_id`, `username`, `name`, `password`, `create_time`, `dr`, `extra`, `role_id`) VALUES
 (1, 'admin', 'admin', '15a162a79e4f467350be58833cdd8c66', '2020-04-22 10:57:40', 1, '222', 1);
/*用户：运营人员角色*/
INSERT INTO `t_user`(`user_id`, `username`, `name`, `password`, `create_time`, `dr`, `extra`, `role_id`) VALUES 
(2, 'Operation', 'Operation', '15a162a79e4f467350be58833cdd8c66', '2020-04-22 16:09:32', 1, 'fawaikuantu', 2);


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
INSERT INTO `t_role_authority`(`ra_id`, `status`, `authority_id`, `role_id`) VALUES (1, 1, 3, 1);
INSERT INTO `t_role_authority`(`ra_id`, `status`, `authority_id`, `role_id`) VALUES (2, 1, 4, 1);
INSERT INTO `t_role_authority`(`ra_id`, `status`, `authority_id`, `role_id`) VALUES (3, 1, 3, 2);
INSERT INTO `t_role_authority`(`ra_id`, `status`, `authority_id`, `role_id`) VALUES (4, 1, 1, 1);
INSERT INTO `t_role_authority`(`ra_id`, `status`, `authority_id`, `role_id`) VALUES (5, 1, 2, 1);
INSERT INTO `t_role_authority`(`ra_id`, `status`, `authority_id`, `role_id`) VALUES (6, 1, 6, 2);
INSERT INTO `t_role_authority`(`ra_id`, `status`, `authority_id`, `role_id`) VALUES (7, 1, 5, 1);
INSERT INTO `t_role_authority`(`ra_id`, `status`, `authority_id`, `role_id`) VALUES (8, 1, 7, 1);





