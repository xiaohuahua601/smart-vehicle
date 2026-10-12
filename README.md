# smart-vehicle｜车辆申请、审批与调度管理系统

> 一个前后端分离的车辆管理练习项目，围绕“申请用车 → 按顺序审批 → 分配车辆 → 归还车辆”组织业务数据和页面。

## 面试官 30 秒了解

| 项目要点 | 说明 |
| --- | --- |
| 场景 | 管理用户、车辆和电子围栏，并让用车申请进入审批与调度流程。 |
| 核心实现 | 提交申请时创建对应审批记录；审批通过后推进下一位审批人，全部通过后更新申请状态；调度时关联车辆并改变车辆状态，归还时恢复空闲。 |
| 技术栈 | Java/Spring Boot、Spring Security、MyBatis、MySQL；Vue 3、Vue Router、Vuex、Element Plus、Axios。 |
| 项目边界 | 仓库含前后端源码，但缺少后端 Maven/Gradle 构建文件和数据库初始化脚本，暂不能按本文直接一键复现后端。 |

## 业务流程与代码入口

1. **用户提交申请**：[ApplicationServiceImpl](ivos/src/main/java/cn/tedu/ivos/application/service/impl/ApplicationServiceImpl.java) 保存申请并调用审批服务建立审批链。
2. **顺序审批**：[AuditServiceImpl](ivos/src/main/java/cn/tedu/ivos/audit/service/impl/AuditServiceImpl.java) 将首位审批人设为待我审核，通过后推进下一位；驳回则更新申请状态。
3. **车辆分配与归还**：申请服务更新申请与车辆状态，形成从申请到归还的状态流转。
4. **围栏管理**：[GeofenceServiceImpl](ivos/src/main/java/cn/tedu/ivos/geofence/service/impl/GeofenceServiceImpl.java) 处理围栏位置数据；删除前检查是否仍有车辆关联。
5. **前端操作**：[路由配置](ivos-web/src/router/index.js) 对应用户、车辆、围栏、申请、审批和分配页面；页面调用后端接口展示和修改数据。

## 仓库结构

- `ivos/`：Spring Boot 后端，按 `application`、`audit`、`vehicle`、`geofence`、`user` 分模块，含 Controller、Service、Mapper 与 XML。
- `ivos-web/`：Vue 管理端；`npm install` 后运行 `npm run serve`，开发端口由脚本设为 9090。
- [后端配置](ivos/src/main/resources/application.properties)：默认端口 8080，数据库为本地 MySQL 的 `ivos`。

## 当前限制

这是源码展示版本，尚未提供完整的后端构建描述与建库脚本。前端路由中的登录检查依赖浏览器本地存储；后端安全配置放行了所有路径，因此当前实现不应作为生产级权限方案。运行前还需要更换仓库中的示例数据库凭据和文件路径。本介绍基于静态源码核查，未进行本机联调。
