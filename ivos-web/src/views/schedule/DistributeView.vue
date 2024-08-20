<!--车辆分配管理页-->
<template>
  <div style="height: 6vh;background-color:#fff;padding:10px 20px;">
    <span style="line-height:60px;font-size:20px;">车辆分配</span>
  </div>
  <!-- 申请单搜索卡片 -->
  <el-card style="margin:20px;height: 70px;">
    <el-form :inline="true">
      <el-form-item label="工单申请人">
        <el-input placeholder="请输入申请人" style="width:220px;"
        v-model="search.username"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="resetSearch">重置</el-button>
        <el-button type="primary" @click="loadApplication">查询</el-button>
      </el-form-item>
    </el-form>
  </el-card>

  <!-- 工单信息列表 -->
  <el-card  style="margin:20px;">
    <el-radio-group style="margin-bottom: 15px;" v-model="type">
      <el-radio-button value="50" size="large">待分配</el-radio-button>
      <el-radio-button value="60" size="large">已分配</el-radio-button>
    </el-radio-group>
    <el-table :data="appTableData">
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
          <el-button type="primary" link v-if="type==50" @click="loadGeo(scope.row.id)">分配用车</el-button>
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
            <el-input placeholder="请输入" v-model="searchGeofenceForm.name"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="围栏状态">
            <el-select placeholder="请选择" v-model="searchGeofenceForm.status">
              <el-option label="启用" value="1"/>
              <el-option label="禁用" value="0"/>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="4"></el-col>
        <el-col :span="4">
          <el-form-item>
            <el-button-group>
              <el-button @click="resetSearchGeo">重置</el-button>
              <el-button type="primary"  @click="loadGeo">查询</el-button>
            </el-button-group>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <el-table style="margin:20px 0" :data="geoTableData">
      <el-table-column label="编号" type="index" width="80" align="center"></el-table-column>
      <el-table-column label="围栏名称" prop="name" width="200" align="center"></el-table-column>
      <el-table-column label="车辆总数" prop="totalNum" width="200" align="center"></el-table-column>
      <el-table-column label="可用车辆" prop="availableNum" align="center"></el-table-column>
      <el-table-column label="围栏状态" prop="status" align="center" :formatter="geoStatusFormatter"></el-table-column>
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
import {onMounted, ref} from "vue";
import qs from "qs";
import {ElMessage} from "element-plus";
import axios from "axios";
//用于控制围栏车辆概览弹窗是否可见
const dialogVisible = ref(false);
//用于控制分配车辆弹窗是否可见
const vehicleDialogVisible = ref(false);

//初始化查询对象  50 已审核完毕 待分配用车  60 已分配用车
const type = ref(50)
const search = ref({
  username:'' //工单的申请人
})
//数据 存放查询结果
const appTableData = ref([]);
//定义查询函数
const loadApplication = ()=>{
  search.value.type = type.value;
  let data = qs.stringify(search.value);
  console.log(data)
  axios.get(BASE_URL+'/v1/application/select?'+data).then((response)=>{
    if (response.data.code ==2000){
      appTableData.value= response.data.data;
    }else {
      ElMessage.error(response.data.msg)
    }
  })
}
//页面启动的时候默认加载
onMounted(()=>{
  loadApplication();
})
//初始化 重置函数
const resetSearch = ()=>{
  search.value.username = '';
  //重新加载申请单数据
  loadApplication();
}
//初始化查询对象
const searchGeofenceForm = ref({
  name:'',//围栏名称
  status:'' //围栏状态
})
//数据 存放查询结果
const geoTableData = ref([]);
//加载围栏数据 当前申请id
const loadGeo = (id)=>{
  dialogVisible.value=true;
  let data = qs.stringify(searchGeofenceForm.value);
  axios.get(BASE_URL+'/v1/geofence/select?'+data).then((response)=>{
    if (response.data.code ==2000){
      //记得改为电子围栏对象
      geoTableData.value= response.data.data;
    }else {
      ElMessage.error(response.data.msg)
    }
  })
}
//初始化 重置函数
const resetSearchGeo = ()=>{
  searchGeofenceForm.value={};
  //重新加载电子围栏数据  后期优化 传参
  loadGeo();
}
//对围栏状态显示文字进行转换
const geoStatusFormatter = (row, column,cellValue,index) => {
  if (cellValue==1){
    cellValue= '启用';
  }else if (cellValue==0){
    cellValue= '禁用';
  }
  return cellValue;
}
</script>

<style scoped>

</style>