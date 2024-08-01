<!--车辆分配管理页-->
<template>
  <div style="height: 6vh;background-color:#fff;padding:10px 20px;">
    <span style="line-height:60px;font-size:20px;">车辆分配</span>
  </div>
  <!-- 申请单搜索卡片 -->
  <el-card style="margin:20px;height: 70px;">
    <el-form :inline="true">
      <el-form-item label="工单申请人">
        <el-input placeholder="请输入申请人" style="width:220px;"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button>重置</el-button>
        <el-button type="primary">查询</el-button>
      </el-form-item>
    </el-form>
  </el-card>

  <!-- 工单信息列表 -->
  <el-card  style="margin:20px;">
    <el-radio-group style="margin-bottom: 15px;">
      <el-radio-button value="50" size="large">待分配</el-radio-button>
      <el-radio-button value="60" size="large">已分配</el-radio-button>
    </el-radio-group>
    <el-table>
      <el-table-column label="编号" type="index" width="100" align="center"></el-table-column>
      <el-table-column label="工单申请人" prop="username" align="center"></el-table-column>
      <el-table-column label="申请时间" prop="createTime" align="center"></el-table-column>
      <el-table-column label="出发地" prop="departureAddr" align="center"></el-table-column>
      <el-table-column label="目的地" prop="destinationAddr" align="center"></el-table-column>
      <el-table-column label="用车事由" prop="reason" align="center"></el-table-column>
      <el-table-column label="使用开始时间" prop="startTime" align="center"></el-table-column>
      <el-table-column label="使用结束时间" prop="endTime" align="center"></el-table-column>
      <el-table-column label="操作" width="100" align="center">
        <template #default="scope">
          <el-button type="primary" link v-if="type==50">分配用车</el-button>
          <el-button type="primary" link v-if="type==60">还车</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>

  <!-- 围栏车辆概览弹窗 -->
  <el-dialog title="围栏列表" v-model="dialogVisible" style="padding:40px;">
    <el-form>
      <el-row :gutter="10">
        <el-col :span="8">
          <el-form-item label="围栏名称:">
            <el-input placeholder="请输入"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="围栏状态">
            <el-select placeholder="请选择">
              <el-option label="启用" value="1"/>
              <el-option label="禁用" value="0"/>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="4"></el-col>
        <el-col :span="4">
          <el-form-item>
            <el-button-group>
              <el-button>重置</el-button>
              <el-button type="primary">查询</el-button>
            </el-button-group>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <el-table style="margin:20px 0">
      <el-table-column label="编号" type="index" width="80" align="center"></el-table-column>
      <el-table-column label="围栏名称" prop="name" width="200" align="center"></el-table-column>
      <el-table-column label="车辆总数" prop="totalNum" width="200" align="center"></el-table-column>
      <el-table-column label="可用车辆" prop="availableNum" align="center"></el-table-column>
      <el-table-column label="围栏状态" prop="status" align="center"></el-table-column>
      <el-table-column label="操作" width="100" align="center">
        <template #default="scope">
          <el-button type="primary" link>查看车辆</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-dialog>

  <!-- 分配车辆弹窗 -->
  <el-dialog title="分配车辆" style="padding:40px;" v-model="vehicleDialogVisible">
    <el-table style="margin:20px 0">
      <el-table-column label="编号" type="index" width="80" align="center"></el-table-column>
      <el-table-column label="车辆品牌" prop="brand" width="200" align="center"></el-table-column>
      <el-table-column label="车牌号" prop="license" width="200" align="center"></el-table-column>
      <el-table-column label="车辆类型" prop="type" align="center"></el-table-column>
      <el-table-column label="车辆状态" prop="status" align="center"></el-table-column>
      <el-table-column label="操作" width="100" align="center">
        <template #default="scope">
          <el-button type="primary" link>分配</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-dialog>
</template>

<script setup>
import {ref} from "vue";
//用于控制围栏车辆概览弹窗是否可见
const dialogVisible = ref(false);
//用于控制分配车辆弹窗是否可见
const vehicleDialogVisible = ref(false);
</script>

<style scoped>

</style>