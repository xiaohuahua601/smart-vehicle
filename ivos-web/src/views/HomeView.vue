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
          unique-opened router>
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
      <router-view/>
    </div>

  </div>

</template>

<script setup>
import {ref} from "vue";
//定义变量控制菜单是否折叠  false不折叠
const isCollapse=ref(false)
</script>
