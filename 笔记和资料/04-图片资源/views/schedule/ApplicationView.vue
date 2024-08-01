<!--用车申请页-->
<template>
  <div style="height: 6vh;background-color:#fff;padding:10px 20px;">
    <span style="line-height:60px;font-size:20px;">申请单列表</span>
    <el-button type="primary" style="float:right;margin-top:13px;" @click="addApplicationDialogVisible=true">申请用车</el-button>
  </div>
  <!-- 申请用车弹窗 -->
  <el-dialog title="创建申请单" v-model="addApplicationDialogVisible" style="width:1000px;padding:40px;">
    <el-form label-width="80px" label-position="top">
      <el-row :gutter="30">
        <el-col :span="12">
          <el-form-item label="用车人">
            <el-input readonly></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="使用时间">
            <el-date-picker
                type="datetimerange"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                value-format="YYYY-MM-DD HH:mm:ss"
                format="YYYY-MM-DD HH:mm:ss"
            ></el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="30">
        <el-col :span="12">
          <el-form-item label="车辆出发地">
            <el-input placeholder="请输入"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="车辆目的地">
            <el-input placeholder="请输入"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="30">
        <el-col :span="12">
          <el-form-item label="用车事由">
            <el-input placeholder="请输入" type="textarea" resize="none" :rows="3"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="备注">
            <el-input placeholder="请输入" type="textarea" resize="none" :rows="3"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="30">
        <el-col :span="12">
          <el-form-item label="驾照">

          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="审批人">
            <el-row :gutter="10">
              <el-col :span="12">
                <el-tag type="primary">
                  <el-icon style="margin-right:5px;position:relative;top:2px;">
                    <User/>
                  </el-icon>
                  <span>审批人姓名1</span>
                </el-tag>
              </el-col>
              <el-col :span="12">
                <el-tag type="primary">
                  <el-icon style="margin-right:5px;position:relative;top:2px;">
                    <User/>
                  </el-icon>
                  <span>审批人姓名2</span>
                </el-tag>
              </el-col>
            </el-row>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <template #footer>
      <el-button>取消</el-button>
      <el-button type="primary">确定</el-button>
    </template>
  </el-dialog>

  <!-- 用车申请列表 搜索卡片 -->
  <el-card style="margin: 20px;">
    <el-form style="padding-top:10px;">
      <el-row :gutter="30">
        <el-col :span="5">
          <el-form-item label="出发地">
            <el-input placeholder="请输入出发地"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="5">
          <el-form-item label="目的地">
            <el-input placeholder="请输入目的地"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="5">
          <el-form-item label="申请单状态">
            <el-select placeholder="请选择">
              <el-option label="已发起" value="10"></el-option>
              <el-option label="撤销" value="20"></el-option>
              <el-option label="审核中" value="30"></el-option>
              <el-option label="驳回" value="40"></el-option>
              <el-option label="已通过" value="50"></el-option>
              <el-option label="分配用车" value="60"></el-option>
              <el-option label="工单结束" value="70"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="5">
          <el-form-item>
            <el-button>重置</el-button>
            <el-button type="primary">查询</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </el-card>
  <!-- 用车申请表格 -->
  <el-card style="margin:20px">
    <el-table>
      <el-table-column type="index" width="80" align="center" prop="id" label="编号"></el-table-column>
      <el-table-column align="center" prop="username" label="申请人"></el-table-column>
      <el-table-column align="center" prop="departureAddr" label="出发地"></el-table-column>
      <el-table-column align="center" prop="destinationAddr" label="目的地"></el-table-column>
      <el-table-column align="center" prop="reason" label="用车原因"></el-table-column>
      <el-table-column align="center" prop="auditUsernameList" label="审批人"></el-table-column>
      <el-table-column align="center" prop="startTime" label="使用开始时间"></el-table-column>
      <el-table-column align="center" prop="endTime" label="使用结束时间"></el-table-column>
      <el-table-column align="center" prop="status" label="申请单状态"></el-table-column>
      <el-table-column label="操作" width="120" align="center">
        <template #default="scope">
          <el-button type="primary" size="small" link>撤销</el-button>
        </template>
      </el-table-column>
    </el-table>

  </el-card>
</template>
<script setup>
import {onMounted, ref} from "vue";
//定义变量控制创建申请单弹窗是否出现
const addApplicationDialogVisible = ref(false);


</script>

<style scoped>
</style>