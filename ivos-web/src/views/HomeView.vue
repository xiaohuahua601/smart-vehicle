<template>
<!--  template 只能包含一个最层的div-->
<!--  div 独占一行-->
<!--  html  div分区标签 -->
  <!-- 1.display: flex;使用弹性布局，
  让父元素中的子元素横置，且父元素会根据子元素浮动后的实际高度自动填充高度   -->
  <div style="display:flex;">
<!--    左侧布局-->
<!--2.isCollapse为true:折叠左侧菜单栏,左侧菜单栏宽度为64px,右侧主体宽度为calc(100% - 64px)-->
    <div :style="{width:( isCollapse ? '64px' : '208px')}">
<!--      激活-->
      <el-menu
          background-color="#3c82f5"
          style="width:100%;height:100%;min-height:100vh;"
          text-color="#fff"
          active-text-color="#ff0"
          :collapse="isCollapse"
          :collapse-transition="false"
          unique-opened router
          @select="selectMenu">
        <!--4.1 左侧菜单栏的顶部条 注意:顶部条也要写在el-menu里！-->
        <el-row style="padding-top: 10px;">
          <el-col :span="2">
          </el-col>
          <el-col :span="7" style="padding-left:7px;">
            <el-avatar src="/imgs/admin/logo.png" style="width:35px;height:35px;"></el-avatar>
          </el-col>
          <el-col :span="isCollapse ? 0 : 13">
            <router-link to="/" style="color:#fff;text-decoration:none;position:relative;top:8px;left:2px;font-weight: bold;">
              智慧车辆
            </router-link>
          </el-col>
        </el-row>
        <el-sub-menu index="1">
          <!--      #title表示是标题的意思  -->
          <template #title>
            <el-icon style="font-size:21px;"><User/></el-icon><span>用户管理</span>
          </template>
          <el-menu-item index="/user">用户列表</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="2">
          <template #title>
            <el-icon style="font-size:21px;"><Van/></el-icon>
            <span>车辆管理</span>
          </template>
          <el-menu-item index="/vehicle">基本信息</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="3">
          <template #title>
            <el-icon style="font-size:21px;"><Place/></el-icon>
            <span>调度管理</span>
          </template>
          <el-menu-item index="/application">申请列表</el-menu-item>
          <el-menu-item index="/audit">调度审核</el-menu-item>
          <el-menu-item index="/distribute">车辆分配</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="4">
          <template #title>
            <el-icon style="font-size:21px;"><Location/></el-icon>
            <span>电子围栏</span>
          </template>
          <el-menu-item index="/geofence">围栏管理</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="5">
          <template #title>
            <el-icon style="font-size:21px;"><MessageBox/></el-icon>
            <span>数据字典</span>
          </template>
          <el-menu-item index="/dict">字典管理</el-menu-item>
        </el-sub-menu>
      </el-menu>
    </div>
<!--    右侧布局-->
<!--3.isCollapse为false:不折叠左侧菜单栏,左侧菜单栏宽度为208px,右侧主体宽度为calc(100% - 208px)-->
    <div :style="{width:( isCollapse ? 'calc(100% - 64px)': 'calc(100% - 208px)')}">
      <!--5.右侧:顶部菜单栏-->
      <el-header style="background-color:#fff;padding-top:22px;height:10vh;">
        <!--5.1 折叠图标与退出登录-->
        <el-row :gutter="10">
          <el-col :span="18">
            <el-icon style="font-size:20px;position:relative;top:3px;" @click="changeCollapsed"><Expand/></el-icon>
          </el-col>
          <el-col :span="6" style="position: relative;">
            <el-dropdown trigger="click" style="position:absolute;right:20px;">
                     <span style="font-size:19px;font-weight: bold;">
                        {{ user.username }}
                         <el-icon><arrow-down/></el-icon>
                     </span>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item @click="logout">退出登录</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </el-col>
        </el-row>
        <!--5.2 面包屑导航-->
        <el-breadcrumb separator="/" style="position: relative;top:20px;">
          <el-breadcrumb-item>首页</el-breadcrumb-item>
          <el-breadcrumb-item v-for="item in breadCrumb">{{item}}</el-breadcrumb-item>
        </el-breadcrumb>
      </el-header>
      <!--6.右侧:主体内容-->
      <el-main style="padding:0;min-height:90vh;overflow-y: auto;">
        <router-view/>
      </el-main>
      <router-view/>
    </div>

  </div>

</template>

<script setup>
import {ref} from "vue";
//定义变量控制菜单是否折叠  false不折叠
const isCollapse=ref(false)
//修改折叠状态
const changeCollapsed = () => {
  isCollapse.value = !isCollapse.value;
}


//退出登录功能
const logout = () => {
  if (confirm('确定要退出吗?')){
    localStorage.removeItem('user');
    //退出时,面包屑也需要清空!
    localStorage.removeItem('breadCrumb');
    window.location.href = '/login';
    user.value = '';
  }
}
//设置变量保存当前的面包屑导航的值，默认为用户列表界面
//'["用户管理", "用户列表"]'作为默认值。
const breadCrumb = ref(JSON.parse('["用户管理", "用户列表"]'));
//定义数组维护不同页面的面包屑值
let map = {
  '/user':['用户管理','用户列表'],
  '/vehicle': ['车辆管理','基本信息'],
  '/application': ['调度管理','申请列表'],
  '/audit': ['调度管理','调度审核'],
  '/distribute':['调度管理','车辆分配'],
  '/geofence':['电子围栏','围栏管理'],
  '/dict':['数据字典','字典管理']
}
//选择菜单项时触发
const selectMenu = (index) => {
  //根据选择的菜单项设置面包屑导航的值
  breadCrumb.value = map[index];

};
//获取登录的用户信息
const user = ref(getUser());
</script>
