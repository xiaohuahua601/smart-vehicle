<!--用户管理页-->
<template>
  <div style="height: 6vh;background-color:#fff;padding:10px 20px;">
    <span style="line-height:60px;font-size:20px;">用户管理</span>
    <el-button @click="dialogVisible=true" type="primary"
               style="float:right;margin-top:13px;">新建用户</el-button>
  </div>
  <!-- 新增用户弹窗 -->
  <!-- :before-close="handleClose"在关闭前调用此函数，为了避免点击弹窗外部关闭弹窗 注意方法没有小括号，否则表示调用 -->
  <el-dialog :title="dialogTitle" style="width:1000px;padding:40px;" v-model="dialogVisible" :before-close="handleClose">
    <el-form label-width="80px" label-position="top">
      <el-row :gutter="30">
        <el-col :span="12">
          <el-form-item label="用户名">
            <el-input placeholder="请输入用户名" v-model="saveUserForm.username" :disabled="saveUserForm.id!=null" ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="手机号">
            <el-input placeholder="请输入手机号" v-model="saveUserForm.phone"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="30">
        <el-col :span="12">
          <el-form-item label="邮箱">
            <el-input placeholder="请输入邮箱" v-model="saveUserForm.email"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="年龄">
            <el-input placeholder="请输入年龄" v-model="saveUserForm.age"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="30">
        <el-col :span="6">
          <el-form-item label="职级">
            <el-select placeholder="请选择" v-model="saveUserForm.level" @change="loadLeader">
              <!-- <el-option label="职员" value="10"></el-option>-->
              <!-- <el-option label="经理" value="20"></el-option>-->
              <!-- <el-option label="总监" value="30"></el-option>-->
              <!-- <el-option label="总裁" value="40"></el-option>-->
              <el-option v-for="item in levelOptions" :label="item.label" :value="item.value"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="直属领导">
            <el-select placeholder="请选择" v-model="saveUserForm.parentId">
              <!-- <el-option label="shaoyun" value="1"></el-option> -->
              <!-- <el-option label="mike" value="2"></el-option> -->
              <el-option v-for="item in leaderOption" :label="item.username" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="4">
          <el-form-item label="性别">
            <el-radio-group v-model="saveUserForm.gender">
              <el-radio label="1" border style="margin: 0;">男</el-radio>
              <el-radio label="0" border>女</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="用户状态">
            <el-radio-group v-model="saveUserForm.status">
              <el-radio value="1" border style="margin: 0;">启用</el-radio>
              <el-radio value="0" border>禁用</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <template #footer>
      <el-button  @click="handleClose()">取消</el-button>
      <el-button type="primary" @click="saveUser">保存</el-button>
    </template>
  </el-dialog>
  <!-- 用户查询卡片 -->
  <el-card style="margin:20px;height: 70px;">
    <el-form :inline="true">
      <el-form-item label="用户搜索">
        <el-input placeholder="输入用户名" style="width:220px;"
                  v-model="searchUserForm.username" @keydown.enter="loadUser"></el-input>
      </el-form-item>
      <el-form-item label="用户状态" style="width:290px;">
        <el-select placeholder="请输入用户状态"
                   v-model="searchUserForm.status" @change="loadUser">
          <el-option label="启用" value="1"/>
          <el-option label="禁用" value="0"/>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button @click="resetSearch">重置</el-button>
        <el-button type="primary" @click="loadUser">查询</el-button>
      </el-form-item>
    </el-form>
  </el-card>
  <!-- 用户信息表格 -->
  <el-card style="margin:20px;">
    <el-table :data="userArr">
      <el-table-column type="index" label="编号" width="80" align="center"/>
      <el-table-column prop="username" label="用户名" align="center"/>
      <el-table-column prop="phone" label="手机号" align="center"/>
      <el-table-column prop="createTime" label="加入时间" align="center"/>
      <el-table-column prop="status" label="用户状态" align="center">
        <template #default="scope">
          <!--active-value="1" inactive-value="0"设置开关激活的值为1,关闭的值为0开关提交的值与status双向绑定-->
          <el-switch @change="changeStatus(scope.row.id,scope.row.status)" active-value="1" inactive-value="0" v-model="scope.row.status"
                     :disabled="scope.row.level==40"></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button link type="primary" size="small" :disabled="scope.row.level==40"
                     @click="resetPassword(scope.row.id)">密码重置</el-button>
          <el-button link type="primary" size="small" :disabled="scope.row.level==40"
                     @click="editUser(scope.row.id)">编辑</el-button>
          <el-button link type="primary" size="small" :disabled="scope.row.level==40"
                     @click="deleteUser(scope.row.id)">删除</el-button>
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

//控制新增用户弹窗是否显示
const dialogVisible = ref(false);
//定义变量控制dialog的标题
const dialogTitle = ref('新建用户');
//员工职级
const levelOptions = ref([
  {label: '职员', value: '10'},
  {label: '经理', value: '20'},
  {label: '总监', value: '30'},
  {label: '总裁', value: '40'}
])
//定义数组保存直属领导数据
const leaderOption = ref([
  {username: 'shaoyun',id: '1'},
  {username: 'mike', id: '2'}
])
//根据职级查询可选的直属领导
const loadLeader = ()=>{
  //优化:如果已经勾选了直属领导，再想重新选择职级的话，需要将之前的直属领导数组和已选择的直属领导数据清空
  leaderOption.value = [];
  saveUserForm.value.parentId='';
  //获取当前选中的职级+10即为领导的职级
  let level = parseInt(saveUserForm.value.level)+10;
  console.log(level)
  axios.get(BASE_URL+'/v1/user/select?level='+level).then((response) => {
    if (response.data.code === 2000) {
      console.log(response.data.data)
      leaderOption.value = response.data.data;
    } else {
      ElMessage.error(response.data.msg);
    }
  })
}
//准备对象用来收集新增/修改员工的表单信息
const saveUserForm = ref({
  username: '',
  phone: '',
  email: '',
  age: '',
  level: '',
  parentId: '',
  gender: '1',
  status: '1'
});
//新增+更新员工
const saveUser = () => {
  let data = qs.stringify(saveUserForm.value);
  console.log(data);
  axios.post(BASE_URL+'/v1/user/save',data).then((response) => {
    if (response.data.code === 2000) {
      ElMessage.success('保存成功!');
      dialogVisible.value = false;//关闭弹窗
      saveUserForm.value={};//清空输入的数据,防止新增成功后打开弹窗还是之前的数据
      loadUser();//新增与更新后都需要重新加载用户数据!
    } else {
      ElMessage.error(response.data.msg);
    }
  })
}
//实现取消按钮的功能
const handleClose = ()=>{
  if(confirm('是否关闭本窗口?')){
    //用户确认关闭后,将审批弹窗与确认驳回弹窗一起关掉
    dialogVisible.value = false;
    //取消时把填的数据都清空，表单回复到初始状态
    saveUserForm.value={};
  }
}
//定义数组用来装用户列表信息
const userArr = ref([]);
//定义对象保存查询条件
const searchUserForm = ref({username: '', status: ''});
const loadUser = () => {
  //如果传入了查询条件，则根据条件查询，没有条件，默认查询所有员工信息
  let data = qs.stringify(searchUserForm.value);
  console.log(data)
  axios.get(BASE_URL+'/v1/user/select?'+data).then((response) => {
    if (response.data.code === 2000) {
      userArr.value = response.data.data;
      console.log(userArr.value);
    } else {
      ElMessage.error(response.data.msg);
    }
  })
}

//页面一加载立即调用
onMounted(() => {
  loadUser();//加载员工列表
})

//重置用户搜索信息
const resetSearch = ()=>{
  searchUserForm.value={};
  loadUser();
}
//密码重置
const resetPassword = (userId)=>{
  axios.post(BASE_URL+'/v1/user/update/password/'+userId).then((response) => {
    if (response.data.code === 2000) {
      ElMessage.success('重置成功!');
    } else {
      ElMessage.error(response.data.msg);
    }
  })
}
//编辑员工
const editUser = (id) =>{
  //修改弹窗标题并打开弹窗
  dialogTitle.value = '编辑员工';
  dialogVisible.value = true;
  //根据id查询要修改的当前员工的信息
  axios.get(BASE_URL+'/v1/user/select?id='+id).then((response) => {
    if (response.data.code === 2000) {
      //若请求成功，赋值给saveUserForm，因为后端返回的是list，所以这里要data[0]
      saveUserForm.value = response.data.data[0];
      //准备当前员工直属领导的职级(+10)
      let level = parseInt(saveUserForm.value.level)+10;
      //第一次打开编辑窗口也需要加载当前职级的可选领导
      axios.get(BASE_URL+'/v1/user/select?level='+level).then((response) => {
        if (response.data.code === 2000) {
          console.log(response.data.data)
          leaderOption.value = response.data.data;
        } else {
          ElMessage.error(response.data.msg);
        }
      })
    } else {
      ElMessage.error(response.data.msg);
    }
  })
}
//修改用户状态
const changeStatus = (userId,status) => {
  axios.post(BASE_URL+'/v1/user/update/status/'+userId+'/'+status).then((response) => {
    if (response.data.code === 2000) {
      ElMessage.success('修改成功!');
    } else {
      ElMessage.error(response.data.msg);
      //数据库没修改成功的话,需要将页面当前用户状态重置为修改前的状态
      user.status = !user.status;
    }
  })
}
//删除用户
const deleteUser = (userId)=>{
  if(confirm('您确认要删除此用户吗?')){
    axios.post(BASE_URL+'/v1/user/delete/'+userId).then((response) => {
      if (response.data.code === 2000) {
        ElMessage.success('删除成功!');
        loadUser();
      } else {
        ElMessage.error(response.data.msg);
      }
    })
  }
}
</script>

<style scoped>

</style>