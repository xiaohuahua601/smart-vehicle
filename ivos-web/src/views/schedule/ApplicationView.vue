<!--用车申请页-->
<template>
  <div style="height: 6vh;background-color:#fff;padding:10px 20px;">
    <span style="line-height:60px;font-size:20px;">申请单列表</span>
    <el-button type="primary" style="float:right;margin-top:13px;" @click="addApplicationDialogVisible=true">申请用车</el-button>
  </div>
  <!-- 申请用车弹窗 -->
  <el-dialog title="创建申请单" v-model="addApplicationDialogVisible" style="width:1000px;padding:40px;"
  :beforl-close="handleClose">
    <el-form label-width="80px" label-position="top">
      <el-row :gutter="30">
        <el-col :span="12">
          <el-form-item label="用车人">
            <el-input readonly :value="user.username"></el-input>
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
                v-model="times"
            ></el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="30">
        <el-col :span="12">
          <el-form-item label="车辆出发地">
            <el-input placeholder="请输入"
            v-model="addForm.departureAddr"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="车辆目的地">
            <el-input placeholder="请输入"
              v-model="addForm.destinationAddr"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="30">
        <el-col :span="12">
          <el-form-item label="用车事由">
            <el-input placeholder="请输入" type="textarea" resize="none" :rows="3"
                      v-model="addForm.reason"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="备注">
            <el-input placeholder="请输入" type="textarea" resize="none" :rows="3"
                      v-model="addForm.remark"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="30">
        <el-col :span="12">
          <el-form-item label="驾照">
            <el-upload
                v-model:file-list="fileList"
                action="http://localhost:8080/v1/file/upload"
                name="file"
                limit="1"
                list-type="picture-card"
                :on-preview="handlePictureCardPreview"
                :on-remove="handleRemove"
            >
              <el-icon>
                <Plus/>
              </el-icon>
            </el-upload>
            <el-dialog v-model="dialogVisible">
              <img w-full :src="dialogImageUrl"/>
            </el-dialog>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="审批人">
            <!-- 审批人是固定的,上级 上上级,可以根据当前登录用户查出,有就显示,没有就消失 -->
            <el-row :gutter="10">
              <el-col :span="12" v-if="auditUserOpts.length>0">
                <el-tag type="primary">
                  <el-icon style="margin-right:5px;position:relative;top:2px;">
                    <User/>
                  </el-icon>
                  <span>{{auditUserOpts[0].username}}</span>
                </el-tag>
              </el-col>
              <el-col :span="12" v-if="auditUserOpts.length>1">
                <el-tag type="primary">
                  <el-icon style="margin-right:5px;position:relative;top:2px;">
                    <User/>
                  </el-icon>
                  <span>{{ auditUserOpts[1].username }}</span>
                </el-tag>
              </el-col>
            </el-row>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <template #footer>
      <el-button @click="handleClose">取消</el-button>
      <el-button type="primary" @click="addApplication">确定</el-button>
    </template>
  </el-dialog>

  <!-- 用车申请列表 搜索卡片 -->
  <el-card style="margin: 20px;">
    <el-form style="padding-top:10px;">
      <el-row :gutter="30">
        <el-col :span="5">
          <el-form-item label="出发地">
            <el-input placeholder="请输入出发地"  v-model="searchApplication.departureAddr"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="5">
          <el-form-item label="目的地">
            <el-input placeholder="请输入目的地" v-model="searchApplication.destinationAddr"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="5">
          <el-form-item label="申请单状态">
            <el-select placeholder="请选择" v-model="searchApplication.status">
<!--              <el-option label="已发起" value="10"></el-option>-->
<!--              <el-option label="撤销" value="20"></el-option>-->
<!--              <el-option label="审核中" value="30"></el-option>-->
<!--              <el-option label="驳回" value="40"></el-option>-->
<!--              <el-option label="已通过" value="50"></el-option>-->
<!--              <el-option label="分配用车" value="60"></el-option>-->
<!--              <el-option label="工单结束" value="70"></el-option>-->
              <el-option v-for="item in auditOptions" :label="item.label"
                         :value="item.value"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="5">
          <el-form-item>
            <el-button @click="resetSearch">重置</el-button>
            <el-button type="primary" @click="loadApplication">查询</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </el-card>
  <!-- 用车申请表格 -->
  <el-card style="margin:20px">
    <el-table :data="tableData">
      <el-table-column type="index" width="80" align="center" prop="id" label="编号"></el-table-column>
      <el-table-column align="center" prop="username" label="申请人"></el-table-column>
      <el-table-column align="center" prop="departureAddr" label="出发地"></el-table-column>
      <el-table-column align="center" prop="destinationAddr" label="目的地"></el-table-column>
      <el-table-column align="center" prop="reason" label="用车原因"></el-table-column>
      <el-table-column align="center" prop="auditUsernameList" label="审批人"></el-table-column>
      <el-table-column align="center" prop="startTime" label="使用开始时间"></el-table-column>
      <el-table-column align="center" prop="endTime" label="使用结束时间"></el-table-column>
      <el-table-column align="center" prop="status" label="申请单状态"
      :formatter="appStatusFormatter"></el-table-column>
      <el-table-column label="操作" width="120" align="center">
        <template #default="scope">
          <el-button type="primary" size="small" link
          :disabled ="scope.row.status!=10" @click="cancel(scope.row.id)">撤销</el-button>
        </template>
      </el-table-column>
    </el-table>

  </el-card>
</template>
<script setup>
import {onMounted, ref} from "vue";
import axios from "axios";
import {ElMessage} from "element-plus";
import qs from "qs";
//定义变量控制创建申请单弹窗是否出现
const addApplicationDialogVisible = ref(false);

/**图片上传相关代码开始**/
const fileList = ref([]);
const dialogImageUrl = ref('');
const dialogVisible = ref(false);
//移除图片
const handleRemove = (uploadFile, uploadFiles) => {
  console.log(uploadFile, uploadFiles);
}
//图片上传后预览
const handlePictureCardPreview = (uploadFile) => {
  dialogVisible.value = true;
  console.log(uploadFile);
  console.log(uploadFile.response.data);
  dialogImageUrl.value= BASE_URL+uploadFile.response.data
}
/**图片上传相关代码结束**/
//获取当前登陆的用户对象
const user = ref(getUser());
//定义数组用来保存页面上显示的表单审批人选项信息
const auditUserOpts = ref([]);
//定义数组用来保存审批人用户id,新增申请单时入库
const auditUserIdList = ref([]);
//定义加载审批人的方法
const loadAuditUser = ()=>{
  let parentId =  user.value.parentId;
  //查询 select/audit/{parentId}
  axios.get(BASE_URL+'/v1/user/select/audit/'+parentId).then((response)=>{
    console.log(response)
    if (response.data.code == 2000){
      auditUserOpts.value =  response.data.data;
      //遍历 auditUserOpts.value
      for (let i=0;i<auditUserOpts.value.length;i++){
        //依次取出上级与上上级审批人的id值,存入auditUserIdList中，在新增时一起传给后端
        auditUserIdList.value.push(auditUserOpts.value[i].id)
      }
    }else {
      ElMessage.error(response.data.msg)
    }
  })
  console.log(parentId)
}
onMounted(()=>{
  loadAuditUser();
  loadApplication();
})

//初始化时间对象 数组
const times = ref([]);
// 初始化对象 新增
const addForm = ref({
  departureAddr:'',
  destinationAddr:'',
  reason:'',
  remark:''
})
//addApplication 新增申请单
const addApplication = ()=>{
  if (fileList.value.length==0){
    ElMessage.error('请上传图片')
    return;
  }
  //做一个初始化
  ////驾照图片路径
  addForm.value.imgUrl = fileList.value[0].response.data;
  addForm.value.userId = user.value.id;
  addForm.value.username = user.value.username;
  addForm.value.startTime = times.value[0];
  addForm.value.endTime = times.value[1];
  addForm.value.auditUserIdList = auditUserIdList.value;
  console.log(addForm.value);
  let data = qs.stringify(addForm.value);
  axios.post(BASE_URL+'/v1/application/save', data).then((response)=>{
   if (response.data.code==2000){
     addApplicationDialogVisible.value= false;
     //清空表单数据
     addForm.value={};
     times.value=[];
     fileList.value=[];
     loadApplication();
   }else {
     ElMessage.error(response.data.msg)
   }
  })
}

//handleClose
const handleClose = ()=>{
  if (confirm('确定是否关闭')){
    addApplicationDialogVisible.value= false;
    //清空表单数据
    addForm.value={};
    times.value=[];
    fileList.value=[];
  }
}
//定义查询对象
const searchApplication = ref({departureAddr:'',destinationAddr:'',status:''});
//准备数组用来存放申请单列表表格数据
const tableData = ref([]);
const loadApplication=()=>{
 let data = qs.stringify(searchApplication.value);
 axios.get(BASE_URL+'/v1/application/select?'+data).then((response)=>{
   if(response.data.code==2000){
     tableData.value = response.data.data;
  }else{
     ElMessage.error(response.data.msg)
  }
 })
}
//resetSearch重置函数
const resetSearch=()=>{
  searchApplication.value = {};
  loadApplication();
}
//状态码搜索的下拉列表
const auditOptions = ref([
  { value:'10',label:'已发起' },
  { value:'20',label:'撤销' },
  { value:'30',label:'审核中' },
  { value:'40',label:'驳回' },
  { value:'50',label:'已通过' },
  { value:'60',label:'分配用车' },
  { value:'70',label:'工单结束' }
])
//状态码的格式化函数
const appStatusFormatter = (row, column,cellValue,index) => {
  if (cellValue==10){
    cellValue= '已发起';
  }else if (cellValue==20){
    cellValue= '撤销';
  }else if (cellValue==30){
    cellValue= '审核中';
  }else if (cellValue==40){
    cellValue= '驳回';
  }else if (cellValue==50){
    cellValue= '已通过';
  }else if (cellValue==60){
    cellValue= '分配用车';
  }else if (cellValue==70){
    cellValue= '工单结束';
  }
  return cellValue;
}
//撤销初始化 cancel(scope.row.id)
const  cancel = (id)=>{
  axios.post(BASE_URL+'/v1/application/cancel/'+id).then((response)=>{
    if (response.data.code == 2000) {
      ElMessage.success("已撤销申请单!");
      loadApplication();
    } else {
      ElMessage.error(response.data.msg);
    }
  })
}
</script>

<style scoped>
</style>