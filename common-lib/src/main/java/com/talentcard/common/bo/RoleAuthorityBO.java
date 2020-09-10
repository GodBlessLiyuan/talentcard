package com.talentcard.common.bo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 18:40 2020/4/9
 * @version: 1.0.0
 * @description: 所有权限清单实体类
 */
@Data
@Component
public class RoleAuthorityBO implements Serializable {
    private static final long SerialVersionUID = 1L;
    /**
     * 系统管理权限, 1开放，2关闭
     * roleQuery 查看角色
     * userCreate 新建用户
     * userUpdate 修改用户
     * userDelete 删除用户
     * userQuery 查看用户
     * logQuery 日志查看
     */
    private Byte roleQuery;
    private Byte userCreate;
    private Byte userUpdate;
    private Byte userDelete;
    private Byte userQuery;
    private Byte logQuery;
    /**
     * 卡片管理权限，1开放，2关闭
     * userCardUpdate 修改卡片
     * userCardDelete 删除卡片
     * userCardQuery 查看卡片
     * userCardCreate 新建卡片
     */
    private Byte userCardUpdate;
    private Byte userCardDelete;
    private Byte userCardQuery;
    private Byte userCardCreate;

    /**
     * 人才管理权限，1开放，2关闭
     * generalTalentQuery 普通用户查看
     * verifiedTalentQuery 认证用户查看
     * verifiedTalentUpdate 认证用户修改
     * approvalTalentQuery 认证审批查看
     * approvalTalentAction 认证审批审批
     */
    private Byte generalTalentQuery;
    private Byte verifiedTalentQuery;
    private Byte verifiedTalentUpdate;
    private Byte approvalTalentQuery;
    private Byte approvalTalentAction;

    /**
     * 人才政策管理权限，1开放，2关闭
     * policyCreate 新建政策
     * policyUpdate 修改政策
     * policyDelete 删除政策
     * policyQuery 查看政策
     * approvalPolicyQuery 政策审批查看
     * approvalPolicyAction 政策审批审批
     * createPolicyType 创建政策类型
     * editPolicyType 编辑政策类型
     * policyTypeQuery 政策类型查询
     */
    private Byte policyCreate;
    private Byte policyUpdate;
    private Byte policyDelete;
    private Byte policyQuery;
    private Byte approvalPolicyQuery;
    private Byte approvalPolicyAction;
    private Byte createPolicyType;
    private Byte editPolicyType;
    private Byte policyTypeQuery;

    /**
     * banner配置，1开放，2关闭
     * bannerQuery  banner查看
     * bannerCreate 新建banner
     */
    private Byte bannerQuery;
    private Byte bannerCreate;

    /**
     * 意见反馈，1开放，2关闭
     * commentQuery  意见反馈查看
     */
    private Byte commentQuery;

    /**
     * 免费旅游，1开放，2关闭
     * tripQuery 查看
     * tripCreate 新建景区
     * tripEdit 编辑景区
     * tripDataQuery 数据查看
     * tripSetTripNumber 配置旅游数量
     * tripSetRelatedInfo 配置关于信息
     * tripGetData 导出数据
     */
    private Byte tripQuery;
    private Byte tripCreate;
    private Byte tripEdit;
    private Byte tripDataQuery;
    private Byte tripSetTripNumber;
    private Byte tripSetRelatedInfo;
    private Byte tripGetData;

    /**
     * 农家乐，1开放，2关闭
     * farmHouseQuery 查看
     * farmHouseCreate 新建农家乐
     * farmHouseEdit 编辑农家乐
     * farmHouseDataQuery 数据查看
     * farmHouseSetRelatedInfo 配置关于信息
     * farmHouseGetData 导出数据
     */
    private Byte farmHouseQuery;
    private Byte farmHouseCreate;
    private Byte farmHouseEdit;
    private Byte farmHouseDataQuery;
    private Byte farmHouseSetRelatedInfo;
    private Byte farmHouseGetData;

    /**
     * 员工绑定信息，1开放，2关闭
     * staffBindInfoQuery 查看
     * staffBindInfoDelete 删除
     */
    private Byte staffBindInfoQuery;
    private Byte staffBindInfoDelete;

    /**
     * 问题收集，1开放，2关闭
     * questionCollectQuery 查看
     */
    private Byte questionCollectQuery;

    /**
     * 人才类别管理，1开放，2关闭
     * talentCategoryQuery 查看
     * talentCategoryAdd 新增人才类别
     * talentCategoryEdit 编辑人才类别
     */
    private Byte talentCategoryQuery;
    private Byte talentCategoryAdd;
    private Byte talentCategoryEdit;

    /**
     * 人才荣誉管理，1开放，2关闭
     * TalentHonourQuery 查看
     * TalentHonourAdd 新增人才荣誉
     * TalentHonourEdit 编辑人才荣誉
     */
    private Byte talentHonourQuery;
    private Byte talentHonourAdd;
    private Byte talentHonourEdit;

    /**
     * 活动管理
     * eventQuery 活动查询
     * eventAdd 活动新增
     * eventEdit 活动编辑
     * eventApproval 活动审批
     */
    private Byte eventQuery;
    private Byte eventAdd;
    private Byte eventEdit;
    private Byte eventApproval;

    /**
     * 一级目录权限
     * systemManage 系统管理
     * cardManage 卡片管理
     * talentManage 人才管理
     * policyManage 政策管理
     * tagManage 标签管理
     * otherService 其他服务
     * extraFunction 扩展功能
     */
    private Byte systemManage;
    private Byte cardManage;
    private Byte talentManage;
    private Byte policyManage;
    private Byte tagManage;
    private Byte otherService;
    private Byte extraFunction;
    /**
     * 将角色权限表实体类转换为其VO表示实体类
     * @param bos
     * @return
     */
    public static RoleAuthorityBO convert(List<RoleAuthorityAddNameBO> bos) {
        RoleAuthorityBO vo = new RoleAuthorityBO();
        for (RoleAuthorityAddNameBO bo : bos) {
            String RoleAuthortyName = bo.getAuthorityName();
            switch (RoleAuthortyName){
                case "roleQuery": vo.setRoleQuery(bo.getStatus());break;
                case "userCreate": vo.setUserCreate(bo.getStatus());break;
                case "userUpdate": vo.setUserUpdate(bo.getStatus());break;
                case "userDelete": vo.setUserDelete(bo.getStatus());break;
                case "userQuery": vo.setUserQuery(bo.getStatus());break;
                case "userCardUpdate": vo.setUserCardUpdate(bo.getStatus());break;
                case "userCardDelete": vo.setUserCardDelete(bo.getStatus());break;
                case "userCardQuery": vo.setUserCardQuery(bo.getStatus());break;
                case "userCardCreate": vo.setUserCardCreate(bo.getStatus());break;
                case "generalTalentQuery": vo.setGeneralTalentQuery(bo.getStatus());break;
                case "verifiedTalentQuery": vo.setVerifiedTalentQuery(bo.getStatus());break;
                case "verifiedTalentUpdate": vo.setVerifiedTalentUpdate(bo.getStatus());break;
                case "approvalTalentQuery": vo.setApprovalTalentQuery(bo.getStatus());break;
                case "approvalTalentAction": vo.setApprovalTalentAction(bo.getStatus());break;
                case "policyCreate": vo.setPolicyCreate(bo.getStatus());break;
                case "policyUpdate": vo.setPolicyUpdate(bo.getStatus());break;
                case "policyDelete": vo.setPolicyDelete(bo.getStatus());break;
                case "policyQuery": vo.setPolicyQuery(bo.getStatus());break;
                case "approvalPolicyQuery": vo.setApprovalPolicyQuery(bo.getStatus());break;
                case "approvalPolicyAction": vo.setApprovalPolicyAction(bo.getStatus());break;
                /*第九期*/
                case "bannerQuery": vo.setBannerQuery(bo.getStatus());break;
                case "bannerCreate": vo.setBannerCreate(bo.getStatus());break;
                case "commentQuery": vo.setCommentQuery(bo.getStatus());break;
                case "tripQuery": vo.setTripQuery(bo.getStatus());break;
                case "tripCreate": vo.setTripCreate(bo.getStatus());break;
                case "tripEdit": vo.setTripEdit(bo.getStatus());break;
                case "tripDataQuery": vo.setTripDataQuery(bo.getStatus());break;
                case "tripSetTripNumber": vo.setTripSetTripNumber(bo.getStatus());break;
                case "tripSetRelatedInfo": vo.setTripSetRelatedInfo(bo.getStatus());break;
                case "tripGetData": vo.setTripGetData(bo.getStatus());break;
                case "farmHouseQuery": vo.setFarmHouseQuery(bo.getStatus());break;
                case "farmHouseCreate": vo.setFarmHouseCreate(bo.getStatus());break;
                case "farmHouseEdit": vo.setFarmHouseEdit(bo.getStatus());break;
                case "farmHouseDataQuery": vo.setFarmHouseDataQuery(bo.getStatus());break;
                case "farmHouseSetRelatedInfo": vo.setFarmHouseSetRelatedInfo(bo.getStatus());break;
                case "farmHouseGetData": vo.setFarmHouseGetData(bo.getStatus());break;
                case "staffBindInfoQuery": vo.setStaffBindInfoQuery(bo.getStatus());break;
                case "staffBindInfoDelete": vo.setStaffBindInfoDelete(bo.getStatus());break;
                case "questionCollectQuery": vo.setQuestionCollectQuery(bo.getStatus());break;
                case "talentCategoryQuery": vo.setTalentCategoryQuery(bo.getStatus());break;
                case "talentCategoryAdd": vo.setTalentCategoryAdd(bo.getStatus());break;
                case "talentCategoryEdit": vo.setTalentCategoryEdit(bo.getStatus());break;
                case "talentHonourQuery": vo.setTalentHonourQuery(bo.getStatus());break;
                case "talentHonourAdd": vo.setTalentHonourAdd(bo.getStatus());break;
                case "talentHonourEdit": vo.setTalentHonourEdit(bo.getStatus());break;
                case "createPolicyType": vo.setCreatePolicyType(bo.getStatus());break;
                case "editPolicyType": vo.setEditPolicyType(bo.getStatus());break;
                case "policyTypeQuery": vo.setPolicyTypeQuery((bo.getStatus()));break;
                case "logQuery": vo.setLogQuery(bo.getStatus());break;
                /*第十一期*/
                case "eventQuery": vo.setEventQuery(bo.getStatus());break;
                case "eventAdd": vo.setEventAdd(bo.getStatus());break;
                case "eventEdit": vo.setEventEdit((bo.getStatus()));break;
                case "eventApproval": vo.setEventApproval(bo.getStatus());break;
                /*一级目录权限*/
                case "systemManage": vo.setSystemManage(bo.getStatus());break;
                case "cardManage": vo.setCardManage(bo.getStatus());break;
                case "talentManage": vo.setTalentManage((bo.getStatus()));break;
                case "policyManage": vo.setPolicyManage(bo.getStatus());break;
                case "tagManage": vo.setTagManage(bo.getStatus());break;
                case "otherService": vo.setOtherService((bo.getStatus()));break;
                case "extraFunction": vo.setExtraFunction(bo.getStatus());break;
            }
        }
        return vo;
    }
}
