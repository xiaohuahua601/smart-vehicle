# 完整SQL  围栏没有绑定车辆
DROP DATABASE IF EXISTS ivos;
CREATE DATABASE ivos CHARACTER SET utf8;
USE ivos;

# 用户表
DROP TABLE IF EXISTS user;
CREATE TABLE user(
     id bigint AUTO_INCREMENT PRIMARY KEY COMMENT '用户id',
     username varchar(20) COMMENT '用户名',
     password varchar(20) COMMENT '密码',
     email varchar(50) COMMENT '邮箱',
     phone varchar(20) COMMENT '手机号',
     age int COMMENT '年龄',
     gender varchar(10) COMMENT '性别 女0 男1',
     create_time datetime COMMENT '创建时间',
     update_time datetime COMMENT '更新时间',
     status varchar(10) COMMENT '状态 启用0 禁用1',
     level varchar(10) COMMENT '员工职级',
     parent_id bigint COMMENT '上级id'
);

INSERT INTO user(username, password, email, phone, age, gender, create_time, update_time, status, level,parent_id) VALUES
    ('shaoyun', '123456', 'tom@example.org', '13600001234', 58, '1','2023-12-28 16:45:00', '2023-12-28 16:45:00', 1, 40,NULL),
    ('mike', '123456', 'tom@example.org', '13600001234', 38, '1','2023-12-28 16:45:00', '2023-12-28 16:45:00', 0, 30,1),
    ('tom', '123456', 'moly@example.org', '13622321234', 38, '1','2023-12-28 16:45:00', '2023-12-28 16:45:00', 0, 30,1),
    ('lucy', '123456', 'lucy@163.com', '13511112222', 43, '0','2023-12-28 16:45:00', '2023-12-29 09:15:00', 1, 20,2),
    ('lily', '123456', 'lily@example.co', '13433334444', 45, '1','2023-12-28 16:45:00', '2023-12-30 11:00:00', 1, 20,2),
    ('moly', '123456', 'mike@example.co', '13433332226', 40, '1','2023-12-28 16:45:00', '2023-12-30 11:00:00', 1, 20,3),
    ('rose', '123456', 'rose@qq.com', '13355556666', 31, '0','2023-12-28 16:45:00', '2023-12-31 17:30:00', 1, 10,4),
    ('linda', '123456', 'linda@qq.com', '13355556667', 14, '0', '2023-12-28 16:45:00','2023-10-31 17:30:00', 1, 10,5),
    ('baby', '123456', 'baby@gmail.com', '13277778888', 35, '1','2023-12-28 16:45:00', '2024-01-01 13:45:00', 0, 10,5),
    ('alice', '123456', 'alice@gmail.com', '13277778880', 25, '1','2023-12-28 16:45:00', '2024-01-01 13:45:00', 0, 10,6),
    ('bob', '123456', 'bob@gmail.com', '13277778880', 27, '1','2023-12-28 16:45:00', '2024-01-01 13:45:00', 1, 10,6),
    ('admin', 'admin', 'admin@163.com', '13600009999', 30, '1','2023-12-28 16:45:00', '2023-12-28 16:45:00', 1, 10,6);

# 车辆表
DROP TABLE IF EXISTS vehicle;
CREATE TABLE vehicle
(
    id                   bigint auto_increment comment '车辆id' primary key,
    brand                varchar(30)  null comment '车辆品牌',
    license              varchar(20)  null comment '车牌号',
    model                varchar(100) null comment '车辆型号',
    code                 varchar(100) null comment '车辆识别码',
    displacement         varchar(20)  null comment '车辆排量',
    status               varchar(10)  null comment '车辆状态：空闲1 占用2',
    type                 varchar(50)  null comment '车辆类型',
    color                varchar(10)  null comment '车辆颜色',
    kilometers           varchar(10)  null comment '里程数',
    reg_time             datetime     null comment '上牌时间',
    buy_time             datetime     null comment '购买时间',
    buy_money            varchar(10)  null comment '购买价格',
    battery_type         varchar(50)  null comment '电池类型',
    create_time          datetime     null comment '创建时间',
    update_time          datetime     null comment '更新时间',
    geofence_bind_status varchar(10)  null comment '电子围栏车辆绑定状态 已绑定1 未绑定0',
    geofence_id          bigint       null comment '电子围栏id',
    constraint code unique (code),
    constraint license unique (license)
);

INSERT INTO vehicle (id, brand, license, model, code, displacement, status, type, color, kilometers,
                     reg_time, buy_time, buy_money, battery_type, create_time, update_time, geofence_bind_status, geofence_id) VALUES
    (1, '奔驰', '京A22323', 'EV300', 'WBA9011', '2', '1', '10', '20', '35465', '2022-10-19 00:00:00', '2022-10-09 00:00:00', '350000', '60', '2022-10-09 00:00:00', '2024-05-19 19:37:28', '0', null),
    (2, '宝马', '京B28323', 'EV300', 'WBA9012', '2', '1', '10', '20', '35465', '2022-10-19 00:00:00', '2022-10-09 00:00:00', '350000', '60', '2022-10-09 00:00:00', '2024-05-19 19:44:50', '0', null),
    (3, '奥迪', '京C29323', 'EV300', 'WBA9013', '2', '1', '10', '20', '35465', '2022-10-19 00:00:00', '2022-10-09 00:00:00', '350000', '60', '2022-10-09 00:00:00', '2023-12-26 00:00:00', '0', null),
    (4, '宝马', '京D96552', '奔驰X5', 'WBA9014', '2', '1', '30', '20', '1200', '2023-06-28 00:00:00', '2023-06-28 00:00:00', '119999', '60', '2022-10-09 00:00:00', '2024-05-19 19:39:56', '0', null),
    (5, '标致', '京E56552', '标致X1', 'WBA9015', '3', '1', '20', '30', '12000', '2023-06-28 00:00:00', '2023-06-28 00:00:00', '1200000', '70', '2022-10-09 00:00:00', '2024-05-19 19:39:08', '0', null),
    (6, '宾利', '京F56552', 'MUV', 'WBA9016', '3', '1', '20', '30', '32321', '2023-07-19 00:00:00', '2023-07-11 00:00:00', '24324', '50', '2022-10-09 00:00:00', '2024-05-19 19:41:35', '0', null),
    (7, '特斯拉', '京G12345', 'model3', 'WBA9017', '3', '1', '10', '10', '2000', '2023-06-28 00:00:00', '2023-07-04 00:00:00', '200000', '10', '2022-10-09 00:00:00', '2024-05-19 19:42:59', '0', null),
    (8, '别克', '京H56552', 'SUV', 'WBA9018', '2', '1', '10', '10', '21', '2023-07-08 00:00:00', '2023-07-03 00:00:00', '2000000', '20', '2022-10-09 00:00:00', '2024-05-19 19:41:47', '0', null),
    (9, '奔驰', '京I56552', 'SUV', 'WBA9019', '2', '1', '10', '10', '20', '2023-07-26 00:00:00', '2023-07-19 00:00:00', '300000', '30', '2022-10-09 00:00:00', '2024-05-19 19:42:42', '0', null);

# 电子围栏表
DROP TABLE IF EXISTS geofence;
CREATE TABLE geofence
(
    id          bigint auto_increment comment '电子围栏id' primary key,
    name        varchar(50) null comment '电子围栏名称',
    status      varchar(10) null comment '电子围栏状态 启用1 禁用0',
    position    text        null comment '电子围栏坐标点',
    create_time datetime    null comment '创建时间'
);

INSERT INTO geofence (id, name, status, position, create_time) VALUES
   (1, '汽车零件运输围栏', '1', '{"type":"rectangle","recPoints":"116.37883388478262-39.91607152946697,116.38896677093518-39.91607152946697,116.38896677093518-39.91186556472842,116.37883388478262-39.91186556472842"}', '2024-05-21 18:04:52'),
   (2, '印刷厂物料运输围栏', '0', '{"type":"rectangle","recPoints":"116.37883388478262-39.91607152946697,116.38896677093518-39.91607152946697,116.38896677093518-39.91186556472842,116.37883388478262-39.91186556472842"}', '2024-05-21 18:04:52'),
   (3, '化工品物料运输围栏', '0', '{"type":"circle","latitude":"39.91059265566227","radius":"491","longitude":"116.43445696196046"}', '2024-05-21 18:05:42'),
   (4, '电气运输围栏', '1', '{"type":"circle","latitude":"40.02103907882358","radius":"346","longitude":"116.35887799248964"}', '2024-05-22 10:05:32');

# 用车申请表
DROP TABLE IF EXISTS application;
create table application
(
    id               bigint auto_increment comment '用车申请表id' primary key,
    user_id          bigint       null comment '申请人',
    username         varchar(10)  null comment '申请人姓名',
    start_time       datetime     null comment '用车开始时间',
    end_time         datetime     null comment '用车结束时间',
    img_url          varchar(100) null comment '驾照图片',
    departure_addr   varchar(100) null comment '出发地',
    destination_addr varchar(100) null comment '目的地',
    reason           varchar(100) null comment '用车原因',
    remark           varchar(100) null comment '其它备注信息',
    status           varchar(10)  null comment '申请表状态 已发起10 撤销20 审核中30 驳回40 已通过50 分配用车60 工单结束70',
    vehicle_id       bigint       null comment '分配的车辆信息',
    reject_reason    varchar(100) null comment '驳回原因',
    create_time      datetime     null comment '创建时间',
    update_time      datetime     null comment '更新时间'
);

INSERT INTO application (id, user_id, username, start_time, end_time, img_url, departure_addr,
                         destination_addr, reason, remark, status, vehicle_id, reject_reason, create_time, update_time) VALUES
    (1, 12, 'admin', '2024-05-08 00:00:00', '2024-05-09 00:00:00', '/imgs/drivingLicense.png', '北京', '上海', '出差', '没买到高铁票', '30', null, null, '2024-05-24 23:49:28', null),
    (2, 12, 'admin', '2024-05-15 00:00:00', '2024-05-16 00:00:00', '/imgs/drivingLicense.png', '上海', '北京', '出差返程', '顺路把车开回来', '10', null, null, '2024-05-24 23:50:08', null),
    (3, 12, 'admin', '2024-05-28 00:00:00', '2024-05-29 00:00:00', '/imgs/drivingLicense.png', '万寿路', '学知园', '搬家', '需要一辆卡车', '40', null, 'C1本不能开卡车', '2024-05-24 23:51:08', null),
    (4, 6, 'moly', '2024-05-08 10:58:43', '2024-05-08 16:58:43', '/imgs/drivingLicense.png', '学知园', '大钟寺', '去取之前的东西', '当天往返', '70', 1, null, '2024-05-24 23:59:33', null),
    (5, 6, 'moly', '2024-05-08 14:58:43', '2024-05-08 16:58:43', '/imgs/drivingLicense.png', '大钟寺', '学知园', '收拾完东西返程', '之前的车已经归还', '60', 2, null, '2024-05-25 00:01:12', null),
    (6, 3, 'tom', '2024-05-07 16:06:03', '2024-05-08 17:18:03', '/imgs/drivingLicense.png', '学知园', '荔枝大厦', '开会', '开会', '50', null, null, '2024-05-25 00:06:46', null),
    (7, 3, 'tom', '2024-05-15 00:07:44', '2024-05-16 00:00:00', '/imgs/drivingLicense.png', '荔枝大厦', '学知园', '回来上课', '上课ing', '20', null, null, '2024-05-25 00:08:32', null);


# 审批表
DROP TABLE IF EXISTS audit;
CREATE TABLE audit(
    id bigint PRIMARY KEY AUTO_INCREMENT COMMENT '审核表id',
    application_id bigint COMMENT '申请单id',
    audit_user_id bigint COMMENT '审核人id',
    audit_status varchar(10) COMMENT '审核状态 待我审核10 待他人审核20 已审核30 驳回40 ',
    audit_sort int(10) COMMENT '审核次序'
);

INSERT INTO audit (id, application_id, audit_user_id, audit_status, audit_sort) VALUES
    (1, 1, 6, '30', 0),
    (2, 1, 3, '10', 1),
    (3, 2, 6, '10', 0),
    (4, 2, 3, '20', 1),
    (5, 3, 6, '30', 0),
    (6, 3, 3, '40', 1),
    (7, 4, 3, '30', 0),
    (8, 4, 1, '30', 1),
    (9, 5, 3, '30', 0),
    (10, 5, 1, '30', 1),
    (11, 6, 1, '30', 0);

# 字典表
DROP TABLE IF EXISTS dict;
CREATE TABLE dict
(
    id          bigint auto_increment comment '自增ID' primary key,
    name        varchar(64) null comment '字典名称',
    code        varchar(64) null comment '字典编码',
    remark      text        null comment '字典说明',
    create_time datetime    null comment '创建时间'
);

INSERT INTO dict (id, name, code, remark, create_time) VALUES
    (1, '车身颜色', 'vehicle_color', '管理汽车颜色', '2024-04-28 23:32:28'),
    (2, '电池类型', 'battery_type', '管理电池类型', '2024-04-28 23:33:22'),
    (3, '车辆类型', 'vehicle_type', '管理车辆类型', '2024-04-28 23:34:16'),
    (4, '状态', 'general_status', '管理启用禁用状态', '2024-04-28 23:47:43'),
    (5, '申请单状态', 'application_status', '管理申请单状态', '2024-04-28 23:55:08'),
    (6, '审核单状态', 'audit_status', '管理审核单状态', '2024-04-29 00:05:36'),
    (7, '员工职级', 'user_level', '管理员工职级', '2024-04-28 23:32:28');

# 字典项表
DROP TABLE IF EXISTS dict_option;
CREATE TABLE dict_option
(
    id          bigint auto_increment comment '字典项ID' primary key,
    dict_id     bigint      null comment '字典表id',
    label       varchar(64) null comment '字典项名称',
    value       varchar(64) null comment '字典项字典值',
    sort        int         null comment '排序',
    remark      text        null comment '备注信息',
    create_time datetime    null comment '创建时间'
);

INSERT INTO dict_option (id, dict_id, label, value, sort, remark, create_time) VALUES
    (1, 1, '白', '10', 1, '车辆颜色', '2024-04-28 23:38:16'),
    (2, 1, '灰', '20', 2, '车辆颜色', '2024-04-28 23:38:16'),
    (3, 1, '黑', '30', 3, '车辆颜色', '2024-04-28 23:38:16'),
    (4, 1, '银', '40', 4, '车辆颜色', '2024-04-28 23:38:16'),
    (5, 1, '红', '50', 5, '车辆颜色', '2024-04-28 23:38:16'),
    (6, 1, '绿', '60', 6, '车辆颜色', '2024-04-28 23:38:16'),
    (7, 2, '铅酸蓄电池', '10', 1, '电池类型', '2024-04-28 23:38:16'),
    (8, 2, '镍氢电池', '20', 2, '电池类型', '2024-04-28 23:38:16'),
    (9, 2, '钠硫电池', '30', 3, '电池类型', '2024-04-28 23:38:16'),
    (10, 2, '二次锂电池', '40', 4, '电池类型', '2024-04-28 23:38:16'),
    (11, 2, '空气电池', '50', 5, '电池类型', '2024-04-28 23:38:16'),
    (12, 2, '三元锂电池', '60', 6, '电池类型', '2024-04-28 23:38:16'),
    (13, 2, '碱性燃料电池', '70', 7, '电池类型', '2024-04-28 23:38:16'),
    (14, 3, '轿车', '10', 1, '车辆类型', '2024-04-28 23:38:16'),
    (15, 3, '客车', '20', 2, '车辆类型', '2024-04-28 23:38:16'),
    (16, 3, '货车', '30', 3, '车辆类型', '2024-04-28 23:38:16'),
    (17, 3, '挂车', '40', 4, '车辆类型', '2024-04-28 23:38:16'),
    (18, 4, '启用', '1', 1, '通用状态', '2024-04-28 23:38:16'),
    (19, 4, '禁用', '0', 2, '通用状态', '2024-04-28 23:38:16'),
    (20, 5, '已发起', '10', 1, '师傅刚发起申请单，此时可撤销', '2024-04-28 23:38:16'),
    (21, 5, '撤销', '20', 2, '师傅撤销申请单', '2024-04-28 23:38:16'),
    (22, 5, '审核中', '30', 3, '至少有一位管理员审核通过', '2024-04-28 23:38:16'),
    (23, 5, '驳回', '40', 4, '管理员驳回后的状态', '2024-04-28 23:38:16'),
    (24, 5, '已通过', '50', 5, '所有管理员审核通过后可以分配用车', '2024-04-28 23:38:16'),
    (25, 5, '分配用车', '60', 6, '已给师傅分配了可用车辆', '2024-04-28 23:38:16'),
    (26, 5, '工单结束', '70', 7, '师傅已还车', '2024-04-28 23:38:16'),
    (27, 6, '待我审核', '10', 1, '申请待当前管理员审核', '2024-04-28 23:38:16'),
    (28, 6, '待他人审核', '20', 2, '需等待前面管理员审核后再由当前管理员审核', '2024-04-28 23:38:16'),
    (29, 6, '已审核', '30', 3, '当前管理员审核通过', '2024-04-28 23:38:16'),
    (30, 6, '驳回', '40', 4, '当前管理员驳回', '2024-04-28 23:38:16'),
    (31, 7, '职员', '10', 1, null, '2024-05-28 23:38:16'),
    (32, 7, '经理', '20', 2, '项目经理可审核普通职员申请单', '2024-05-28 23:38:16'),
    (33, 7, '总监', '30', 3, '总监可审核项目经理申请单', '2024-05-28 23:38:16'),
    (34, 7, '总裁', '40', 4, 'CEO可审核总监申请单', '2024-05-28 23:38:16');
