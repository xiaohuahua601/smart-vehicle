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
        <el-input placeholder="请输入用车人" style="width:220px;"
        v-model="search.username"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="resetSearch">重置</el-button>
        <el-button type="primary" @click="loadAudit">查询</el-button>
      </el-form-item>
    </el-form>
  </el-card>

  <!-- 审批单主体 -->
  <el-card style="margin:20px;">
    <!--  审批状态项  -->
    <el-radio-group style="margin-bottom: 15px;" v-model="type" @change="loadAudit">
      <el-radio-button value="10" size="large">待我审核</el-radio-button>
      <el-radio-button value="20" size="large">待他人审核</el-radio-button>
      <el-radio-button value="30" size="large">已审核</el-radio-button>
      <el-radio-button value="40" size="large">驳回</el-radio-button>
    </el-radio-group>
    <!--  审批列表  -->
    <el-table :data="AuditArr">
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
          <el-button type="primary" link v-if="type==10" @click="Auditing(scope.row.id)">审批</el-button>
          <!-- 查看40：在驳回页签下显示，可以查看已驳回申请的驳回原因 -->
          <el-button type="primary" link v-if="type==40">查看</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>

  <!-- 审批/查看弹窗 -->
  <el-dialog :title="dialogTitle" v-model="auditDialogVisible">
    <el-descriptions direction="horizontal" :column="2" border>
      <el-descriptions-item label="用车人">{{auditDialogData.username}}</el-descriptions-item>
      <el-descriptions-item label="用车事由">{{ auditDialogData.reason  }}</el-descriptions-item>
      <el-descriptions-item label="使用开始时间">{{auditDialogData.startTime}}</el-descriptions-item>
      <el-descriptions-item label="使用结束时间">{{ auditDialogData.endTime}}</el-descriptions-item>
      <el-descriptions-item label="车辆出发地">{{auditDialogData.departureAddr }}</el-descriptions-item>
      <el-descriptions-item label="车辆目的地">{{auditDialogData.destinationAddr}}</el-descriptions-item>
      <el-descriptions-item label="驾照图片">
        <img :src="BASE_URL+auditDialogData.imgUrl" style="width:150px;">
      </el-descriptions-item>
      <el-descriptions-item label="备注">{{auditDialogData.remark}}</el-descriptions-item>
      <!--  驳回原因要在点击驳回才显示 -->
      <!--  <el-descriptions-item label="驳回原因">暂无可用车辆</el-descriptions-item> -->
    </el-descriptions>
    <template #footer>
      <el-button>取消</el-button>
      <el-button type="primary" plain @click="rejectInnerDialogVisible=true">驳回</el-button>
      <el-button type="primary" @click="auditPass">通过</el-button>
    </template>
  </el-dialog>

  <!-- 驳回原因弹窗 -->
  <el-dialog title="驳回 查看" v-model="rejectInnerDialogVisible" style="margin-top: 37vh;">
    <el-descriptions direction="horizontal" border>
      <el-descriptions-item label="驳回原因">
        <el-input placeholder="请输入驳回原因" v-model="rejectReason"></el-input>
      </el-descriptions-item>
    </el-descriptions>
    <template #footer>
      <el-button type="primary" plain>取消</el-button>
      <el-button type="primary" @click="auditReject">确定</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import {onMounted, ref} from "vue";
import qs from "qs";
import axios from "axios";
import {ElMessage} from "element-plus";
//控制审批弹窗标题
const dialogTitle = ref("待审批详情");
//控制审批弹窗是否显示
const auditDialogVisible = ref(false);
//控制驳回原因弹窗是否显示
const rejectInnerDialogVisible = ref(false);
//查询数据  1、用车人  2、全查【状态】
const search = ref({
  username:'',//绑定搜索卡片中工单申请人姓名
  auditUserId:'',//预先准备 接下来要查属于当前用户的审批单
  auditStatus:''//预先准备 查不同状态的审批单数据
})
//获取当前登录的用户对象--》查询跟他相关的审批工单
const user = ref(getUser());
const type = ref(10);
//接受查询的集合
const AuditArr = ref([]);
//加载表格数据
const loadAudit=()=>{
  //只查询跟自己相关的申请单
  search.value.auditUserId = user.value.id;
  search.value.auditStatus = type.value;
  let data = qs.stringify(search.value);
  console.log(data)
  //发起申请
  axios.get(BASE_URL+'/v1/audit/select?'+data).then((response)=>{
    if (response.data.code==2000){
      AuditArr.value = response.data.data;
    }else {
      ElMessage.error(response.data.msg)
    }
  })
}
//页面一加载立即加载数据
onMounted(()=>{
  loadAudit();
});
//resetSearch重置
const resetSearch = ()=>{
  search.value.username='';
  loadAudit();
}
//初始化查询对象
//定义变量保存审批弹窗数据
const auditDialogData = ref({
  username: "",//申请人姓名
  reason: "",//用车事由
  startTime: "",//开始时间
  endTime: "",//结束时间
  departureAddr: "", //出发地
  destinationAddr: "",//目的地
  imgUrl: "",//驾照图片
  remark: ""//备注
})
//Auditing(scope.row.id)审批  --查看
const Auditing = (id)=>{
  auditDialogVisible.value = true;
  axios.get(BASE_URL+'/v1/audit/select?id='+id).then((response)=>{
    console.log(response)
    if (response.data.code == 2000){
      auditDialogData.value = response.data.data[0];
    }
  })
}
//auditPass
const auditPass = ()=>{
  auditDialogData.value.auditStatus = 30;
  let data = qs.stringify(auditDialogData.value);
  axios.post(BASE_URL+'/v1/audit/update',data).then((response)=>{
    if (response.data.code == 2000){
      ElMessage.success('审批已通过!');
      //关闭审批通过弹窗
      auditDialogVisible.value = false;
      //将审批弹窗数据对象置空
      auditDialogData.value = {};
      //审批通过后可以切换页签至"已审核"
      //type.value = 30;
      //也可以重新加载审批数据
      loadAudit();
    }
  })
}
//rejectReason
const rejectReason = ref();
//auditReject
const auditReject =()=>{
  if (rejectReason.value==null){
    ElMessage.error('驳回原因不能为空!');
    return;
  }
  rejectInnerDialogVisible.value = false;
  auditDialogVisible.value=false
  auditDialogData.value.auditStatus = 40;
  auditDialogData.value.rejectReason = rejectReason.value
  let data = qs.stringify(auditDialogData.value);
  axios.post(BASE_URL+'/v1/audit/update',data).then((response)=>{
    if (response.data.code == 2000){
      ElMessage.success('驳回成功!');
      //关闭审批通过弹窗
      auditDialogVisible.value = false;
      //将审批弹窗数据对象置空
      auditDialogData.value = {};
      //审批通过后可以切换页签至"已审核"
      //type.value = 30;
      //也可以重新加载审批数据
      loadAudit();
    }
  })

}
//取消 自己初始化
</script>

<style>

</style>