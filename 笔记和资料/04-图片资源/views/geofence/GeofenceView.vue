<!--电子围栏页-->
<template>
  <div style="height: 6vh;background-color:#fff;padding:10px 20px;">
    <span style="line-height:60px;font-size:20px;">围栏管理</span>
    <el-button type="primary" style="float:right;margin-top:13px;">新建围栏</el-button>
  </div>
  <!-- 围栏查询卡片 -->
  <el-card style="margin:20px;height: 70px;">
    <el-form :inline="true">
      <el-form-item label="围栏名称">
        <el-input placeholder="请输入" style="width:220px;"></el-input>
      </el-form-item>
      <el-form-item label="围栏状态" style="width:290px;">
        <el-select placeholder="请选择">
          <el-option label="启用" value="1"/>
          <el-option label="禁用" value="0"/>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button>重置</el-button>
        <el-button type="primary">查询</el-button>
      </el-form-item>
    </el-form>
  </el-card>
  <!-- 围栏表格数据 -->
  <el-card style="margin:20px;">
    <el-table stripe>
      <el-table-column label="编号" prop="id" type="index" align="center" width="100"></el-table-column>
      <el-table-column label="围栏名称" prop="name" align="center"></el-table-column>
      <el-table-column label="围栏坐标点" prop="position" align="center" show-overflow-tooltip="true" width="500"></el-table-column>
      <el-table-column label="创建时间" prop="createTime" align="center"></el-table-column>
      <el-table-column label="围栏状态" align="center">
        <template #default="scope">
          <el-switch inactive-value="0" active-value="1"></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button type="primary" link>管理车辆</el-button>
          <el-button type="primary" link>删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
  <!-- 已绑定车辆弹窗 -->
  <el-dialog title="已绑定车辆" v-model="bindVehicleDialogVisible" style="padding:40px;">
    <el-button type="primary" style="margin:10px 0;">添加绑定车辆</el-button>
    <el-table>
      <el-table-column prop="brand" label="车辆品牌" align="center"></el-table-column>
      <el-table-column prop="license" label="车牌号" align="center"></el-table-column>
      <el-table-column prop="type" label="车辆类型" align="center"></el-table-column>
      <el-table-column
          label="操作"
          fixed="right"
          width="200"
          align="center">
        <template #default="scope">
          <el-button link type="primary">移除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-dialog>
  <!-- 添加可绑定车辆列表弹窗 -->
  <!-- :before-close="makeSure"要在关闭新添加绑定车辆的弹窗后立即加载已绑定车辆的弹窗表格数据-->
  <el-dialog title="可绑定车辆列表" v-model="AddBindVehicleDialogVisible">
    <el-table>
      <el-table-column label="编号" type="index" align="center" width="80px"></el-table-column>
      <el-table-column prop="brand" label="车辆品牌" align="center"></el-table-column>
      <el-table-column prop="license" label="车牌号" align="center"></el-table-column>
      <el-table-column prop="type" label="车辆类型" align="center"></el-table-column>
      <el-table-column prop="status" label="车辆状态" align="center"></el-table-column>
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button link type="primary">绑定</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-dialog>
</template>
<script setup>
import {ref} from "vue";

//控制"已绑定车辆弹窗"是否显示
const bindVehicleDialogVisible = ref(false);
//控制"可绑定车辆列表"是否显示
const AddBindVehicleDialogVisible = ref(false);
</script>

<style scoped>
</style>