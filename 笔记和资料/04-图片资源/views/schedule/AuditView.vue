<!--申请审批页-->
<template>
  <!-- 顶部条 -->
  <div style="height: 6vh;background-color:#fff;padding:10px 20px;">
    <span style="line-height:60px;font-size:20px;">审批列表</span>
  </div>
  <!-- 审批搜索卡片 -->
  <el-card style="margin:20px;height: 70px;">
    <el-form :inline="true">
      <el-form-item label="用车人">
        <el-input placeholder="请输入用车人" style="width:220px;"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button>重置</el-button>
        <el-button type="primary">查询</el-button>
      </el-form-item>
    </el-form>
  </el-card>

  <!-- 审批单主体 -->
  <el-card style="margin:20px;">
    <!--  审批状态项  -->
    <el-radio-group style="margin-bottom: 15px;">
      <el-radio-button value="10" size="large">待我审核</el-radio-button>
      <el-radio-button value="20" size="large">待他人审核</el-radio-button>
      <el-radio-button value="30" size="large">已审核</el-radio-button>
      <el-radio-button value="40" size="large">驳回</el-radio-button>
    </el-radio-group>
    <!--  审批列表  -->
    <el-table>
      <el-table-column label="编号" prop="id" align="center" width="55" type="index"></el-table-column>
      <el-table-column label="用车人" prop="username" align="center" width="110"></el-table-column>
      <el-table-column label="开始时间" prop="startTime"  align="center"></el-table-column>
      <el-table-column label="结束时间" prop="endTime"  align="center"></el-table-column>
      <el-table-column label="用车事由" prop="reason"  align="center"></el-table-column>
      <el-table-column label="审批人" prop="auditUsernameList"  align="center"></el-table-column>
      <el-table-column label="出发地" prop="departureAddr"  align="center"></el-table-column>
      <el-table-column label="目的地" prop="destinationAddr"  align="center"></el-table-column>
      <el-table-column label="操作" width="100" align="center" v-if="type==10||type==40" :key="audit">
        <template #default="scope">
          <!-- 审批10：在待我审核页签下显示，需要当前登录用户进行审批 -->
          <el-button type="primary" link v-if="type==10">审批</el-button>
          <!-- 查看40：在驳回页签下显示，可以查看已驳回申请的驳回原因 -->
          <el-button type="primary" link v-if="type==40">查看</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>

  <!-- 审批/查看弹窗 -->
  <el-dialog :title="dialogTitle" v-model="auditDialogVisible">
    <el-descriptions direction="horizontal" :column="2" border>
      <el-descriptions-item label="用车人">rose</el-descriptions-item>
      <el-descriptions-item label="用车事由">物料运输</el-descriptions-item>
      <el-descriptions-item label="使用开始时间">2024-05-28 08:00:00</el-descriptions-item>
      <el-descriptions-item label="使用结束时间">2024-06-01 08:00:00</el-descriptions-item>
      <el-descriptions-item label="车辆出发地">北京市海淀区</el-descriptions-item>
      <el-descriptions-item label="车辆目的地">北京市昌平区</el-descriptions-item>
      <el-descriptions-item label="驾照图片">
        <img src="/imgs/admin/drivingLicense.png" style="width:150px;">
      </el-descriptions-item>
      <el-descriptions-item label="备注">因仓储不够，急需调用车辆进行物料运输</el-descriptions-item>
      <!--  驳回原因要在点击驳回才显示 -->
      <!--  <el-descriptions-item label="驳回原因">暂无可用车辆</el-descriptions-item> -->
    </el-descriptions>
    <template #footer>
      <el-button>取消</el-button>
      <el-button type="primary" plain>驳回</el-button>
      <el-button type="primary">通过</el-button>
    </template>
  </el-dialog>

  <!-- 驳回原因弹窗 -->
  <el-dialog title="驳回 查看" v-model="rejectInnerDialogVisible" style="margin-top: 37vh;">
    <el-descriptions direction="horizontal" border>
      <el-descriptions-item label="驳回原因">
        <el-input placeholder="请输入驳回原因"></el-input>
      </el-descriptions-item>
    </el-descriptions>
    <template #footer>
      <el-button type="primary" plain>取消</el-button>
      <el-button type="primary">确定</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import {ref} from "vue";

//控制审批弹窗标题
const dialogTitle = ref("待审批详情");
//控制审批弹窗是否显示
const auditDialogVisible = ref(false);
//控制驳回原因弹窗是否显示
const rejectInnerDialogVisible = ref(false);
</script>

<style>

</style>