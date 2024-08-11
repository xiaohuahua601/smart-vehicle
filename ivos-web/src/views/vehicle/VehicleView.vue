<!--车辆管理页-->
<template>
  <div style="height: 6vh;background-color:#fff;padding:10px 20px;">
    <span style="line-height:60px;font-size:20px;">车辆管理</span>
    <el-button @click="dialogVisible=true" type="primary" style="float:right;margin-top:13px;">新建车辆</el-button>
  </div>
  <!-- 新增车辆弹窗 -->
  <el-dialog :title="dialogTitle" v-model="dialogVisible" style="width:1000px;padding:40px;" :before-close="handleClose">
    <el-form label-width="80px" label-position="top">
      <el-row :gutter="30">
        <el-col :span="12">
          <el-form-item label="车辆品牌">
            <el-input placeholder="请输入内容" v-model="saveVehicleForm.brand"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="车辆牌号">
            <el-input placeholder="请输入内容" v-model="saveVehicleForm.license"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="30">
        <el-col :span="12">
          <el-form-item label="车辆型号">
            <el-input placeholder="请输入内容" v-model="saveVehicleForm.model"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="车辆识别码">
            <el-input placeholder="请输入内容" v-model="saveVehicleForm.code"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="30">
        <el-col :span="12">
          <el-form-item label="车辆排量">
            <el-select placeholder="请选择" v-model="saveVehicleForm.displacement">
              <el-option label="1.6" value="1"></el-option>
              <el-option label="2.5" value="2"></el-option>
              <el-option label="4" value="3"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="车辆类型">
            <el-select placeholder="请选择"  v-model="saveVehicleForm.type">
              <el-option label="轿车" value="10"></el-option>
              <el-option label="货车" value="20"></el-option>
              <el-option label="客车" value="30"></el-option>
              <el-option label="挂车" value="40"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="30">
        <el-col :span="12">
          <el-form-item label="车身颜色">
            <el-select placeholder="请选择" v-model="saveVehicleForm.color">
              <el-option label="黑" value="10"></el-option>
              <el-option label="白" value="20"></el-option>
              <el-option label="蓝" value="30"></el-option>
              <el-option label="灰" value="40"></el-option>
              <el-option label="银" value="50"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="里程数">
            <el-input placeholder="请输入内容" v-model="saveVehicleForm.kilometers"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="30">
        <el-col :span="12">
          <el-form-item label="购买时间">
            <el-date-picker type="date" format="YYYY-MM-DD" value-format="YYYY-MM-DD"
                            v-model="saveVehicleForm.buyTime"/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="上牌时间">
            <el-date-picker type="date" format="YYYY-MM-DD" value-format="YYYY-MM-DD"
                            v-model="saveVehicleForm.regTime"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="30">
        <el-col :span="12">
          <el-form-item label="购买价格">
            <el-input placeholder="请输入内容" v-model="saveVehicleForm.buyMoney"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="电池类型">
            <el-select placeholder="请选择" v-model="saveVehicleForm.batteryType">
              <el-option label="铅酸蓄电池" value="10"></el-option>
              <el-option label="镍氢电池" value="20"></el-option>
              <el-option label="钠硫电池" value="30"></el-option>
              <el-option label="二次锂电池" value="40"></el-option>
              <el-option label="空气电池" value="50"></el-option>
              <el-option label="三元锂电池" value="60"></el-option>
              <el-option label="碱性燃料电池" value="70"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <template #footer>
      <el-button @click="handleClose">取消</el-button>
      <el-button type="primary" @click="saveVehicle">确定</el-button>
    </template>
  </el-dialog>
  <!-- 车辆搜索卡片 -->
  <el-card style="margin:20px;height: 70px;">
    <el-form :inline="true">
      <el-form-item label="车辆品牌">
        <el-input placeholder="请输入内容" style="width:220px;" v-model="searchVehicleForm.brand"></el-input>
      </el-form-item>
      <el-form-item label="车牌号" style="width:290px;">
        <el-input placeholder="请输入内容" style="width:220px;" v-model="searchVehicleForm.license"></el-input>
      </el-form-item>
      <el-form-item>
<!--        v-on:click=  @click-->
        <el-button @click="resetSearch">重置</el-button>
        <el-button type="primary" @click="loadVehicle">查询</el-button>
      </el-form-item>
    </el-form>
  </el-card>
  <!-- 车辆列表 -->
  <el-card style="margin:20px;">
    <el-table :data="vehicleArr">
      <el-table-column type="index" label="编号" width="80" align="center"/>
      <el-table-column prop="brand" label="车辆品牌" align="center"/>
      <el-table-column prop="license" label="车牌号" align="center"/>
      <el-table-column prop="code" label="车辆识别码" align="center"/>
      <el-table-column prop="type" label="车辆类型" align="center"
                       :formatter="vehicleTypeFormatter"/>
      <el-table-column prop="buyMoney" label="购买价格" align="center"/>
      <el-table-column prop="buyTime" label="购买时间" align="center"/>
      <el-table-column prop="regTime" label="上牌时间" align="center"/>
      <el-table-column prop="batteryType" label="电池类型" align="center"
                       :formatter="batteryTypeFormatter"/>
      <el-table-column prop="status" label="车辆状态" align="center"
                       :formatter="vehicleStatusFormatter"/>
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button link type="primary" size="small" @click="editVehicle(scope.row.id)">编辑</el-button>
          <el-button link type="primary" size="small" @click="deleteVehicle(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>
<script setup>
import {onMounted, ref} from "vue";
import qs from "qs";
import {ElMessage} from "element-plus";

//控制新增车辆弹窗是否显示
let dialogVisible = ref(false);
//定义变量控制dialog的标题
const dialogTitle = ref('新增车辆');
//定义查询对象
const searchVehicleForm = ref({brand:'',license:''});
//定义数组保存查询的车辆列表数据
const vehicleArr = ref([]);
//查询基本信息函数
const loadVehicle = ()=>{
  let data = qs.stringify(searchVehicleForm.value);
  //两个参数 url请求的路径  data 查询的条件
  //http://localhost:8080/v1/vehicle/select?data
  axios.get(BASE_URL+'/v1/vehicle/select?'+data).then((response)=>{
    //处理响应的结果
    if (response.data.code == 2000){
      vehicleArr.value = response.data.data;
      console.log(vehicleArr.value)
    }else {
      //查询失败 --提示对应的结果
      ElMessage.error(response.data.msg);
    }

  })
}
//当页面加载的时候 触发的函数
onMounted(()=>{
  //一进入页面就需要加载车辆列表
  loadVehicle();
})
//重置车辆搜素的信息
const resetSearch = ()=>{
  //清空已填的搜索数据,并且重新加载所有车辆信息
  searchVehicleForm.value={};
  loadVehicle();
}
//定义一个新增的车辆对象
const saveVehicleForm = ref({
  brand:'',
  license:'',
  model:'',
  code:'',
  displacement:'',
  type:'',
  color:'',
  kilometers:'',
  buyTime:'',
  regTime:'',
  buyMoney:'',
  batteryType:''
});
//定义保存车辆的方法
  const saveVehicle = ()=>{
    //注意 .value
    let data = qs.stringify(saveVehicleForm.value);
   //发起后端请求
    axios.post(BASE_URL+'/v1/vehicle/save',data).then(response=>{
      console.log(response);
      if (response.data.code==2000){
        //修改逻辑
        ElMessage.success("保存成功");
        //关闭新增弹框
        dialogVisible.value = false;
        //清空表单数据
        saveVehicleForm.value = {};
        //重新加载车辆信息
        loadVehicle();
      }else {
        ElMessage.error(response.data.msg);
      }
    })
  }
// 实现新增中 取消和关闭的一个效果
  const handleClose = ()=>{
    //误触 做判断
    if (confirm('确定是否关闭本窗口')){
      dialogVisible.value = false;
      saveVehicleForm.value= {};
    }
  }
  //编辑车辆信息 editVehicle(scope.row.id)
  const editVehicle = (id)=>{
    //显示弹框
    dialogVisible.value = true;
    //修改弹框标题
    dialogTitle.value = '编辑车辆信息'
    //回显车辆信息
    axios.get(BASE_URL+'/v1/vehicle/select?id='+id).then(response=>{
      if (response.data.code == 2000){
        // 将我们查询的数据 会回显弹框表单里面
        saveVehicleForm.value = response.data.data[0];
      }else {
        ElMessage.error(response.data.msg);
      }
    })
  }
  //deleteVehicle(scope.row.id) 初始化删除
const deleteVehicle = (id)=>{
  //误触 做判断
  if (confirm('确定是否删除车辆信息')){
    //回显车辆信息 /v1/vehicle/delete/1
    axios.post(BASE_URL+'/v1/vehicle/delete/'+id).then(response=>{
      if (response.data.code == 2000){
        ElMessage.success('删除成功');
        loadVehicle();
      }else {
        ElMessage.error(response.data.msg);
      }
    })
  }
}
// 数值  车辆类型 10 20 30 40
//车辆类型显示转换
const vehicleTypeFormatter = (row, column,cellValue,index) => {
  if(cellValue==10){
    cellValue= '轿车';
  }else if(cellValue==20){
    cellValue= '货车';
  }else if(cellValue==30){
    cellValue= '客车';
  }else if(cellValue==40){
    cellValue= '挂车';
  }
  return cellValue;
}
//电池类型显示转换
const batteryTypeFormatter = (row, column,cellValue,index) => {
  if (cellValue==10){
    cellValue= '铅酸蓄电池';
  }else if (cellValue==20){
    cellValue= '镍氢电池';
  }else if (cellValue==30){
    cellValue= '钠硫电池';
  }else if (cellValue==40){
    cellValue= '二次锂电池';
  }else if (cellValue==50){
    cellValue= '空气电池';
  }else if (cellValue==60){
    cellValue= '三元锂电池';
  }else if (cellValue==70){
    cellValue= '碱性燃料电池';
  }
  return cellValue;
}
//车辆状态显示转换
const vehicleStatusFormatter = (row, column,cellValue,index) => {
  if(cellValue==1){
    cellValue= '空闲';
  }else if(cellValue==2){
    cellValue= '占用';
  }
  return cellValue;
}
</script>