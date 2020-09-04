### 修改表 ###

### 新添加表 ###
1. ev_event表：后台活动表
2. ev_event_enjoy表：后台活动享受群体表
3. ev_frontend_event表：前台活动表
4. ev_frontend_event_approval表：前台活动审批表
5. ev_event_field表：活动场地表（目前直接数据库人工写入）
6. ev_event_query表：活动查询表（优化后台查询的记录表）
7. ev_event_talent：人才活动表
8. ev_event_log：人才活动历史记录表
9. ta_social_security：社保信息记录表

### 活动状态 status ###

1：提交待审批；2：已同意（已通过）；3：已驳回； 4：管理员取消；5：用户取消

### 需要执行的旧数据导入到新的表格的数据 ###


### application.properties ###
1. 添加wechat.sendToEventPass
2. 添加wechat.sendToEventReject