
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


### 需要执行的旧数据导入到新的表格的数据 ###

1. t_talent_type表
2. po_compliance表