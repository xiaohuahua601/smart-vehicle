# smart-vehicle｜车辆申请与调度管理系统

这是一个前后端分离的车辆管理项目。用户提交用车申请，审批人依次处理，调度人员在审批通过后分配车辆，归还时恢复车辆为空闲。系统同时提供用户、车辆和电子围栏管理页面。

## 主要流程

1. **提交申请**：保存用车信息，并为所选审批人生成按顺序排列的审批记录。
2. **依次审批**：第一位审批人处理后，下一位进入“待我审核”；全部通过后申请变为已通过，驳回则更新申请状态。
3. **分配车辆**：将车辆关联到申请，并把车辆状态改为使用中。
4. **归还车辆**：结束申请，将车辆状态恢复为空闲。

这条流程分别由 [申请服务](ivos/src/main/java/cn/tedu/ivos/application/service/impl/ApplicationServiceImpl.java) 和 [审批服务](ivos/src/main/java/cn/tedu/ivos/audit/service/impl/AuditServiceImpl.java) 实现。前端的申请、审批、分配、车辆和围栏页面可从 [路由配置](ivos-web/src/router/index.js) 进入。

## 其他功能

- **车辆与用户管理**：维护车辆、用户信息，供用车申请和分配使用。
- **电子围栏**：保存围栏位置数据；删除围栏前检查是否仍有车辆关联，见 [围栏服务](ivos/src/main/java/cn/tedu/ivos/geofence/service/impl/GeofenceServiceImpl.java)。
- **登录与界面**：管理端使用 Vue 3、Vue Router、Vuex、Element Plus；后端使用 Spring Boot、Spring Security、MyBatis 和 MySQL。

## 技术与代码结构

`ivos/` 是 Java 后端，按申请、审批、车辆、围栏和用户拆分模块；`ivos-web/` 是 Vue 管理端。前端使用 Vue 3、Vue Router、Vuex 和 Element Plus；后端使用 Spring Boot、Spring Security、MyBatis 与 MySQL。[申请服务](ivos/src/main/java/cn/tedu/ivos/application/service/impl/ApplicationServiceImpl.java) 和 [审批服务](ivos/src/main/java/cn/tedu/ivos/audit/service/impl/AuditServiceImpl.java) 是理解主流程的入口。
