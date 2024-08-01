<!--用户管理页-->
<template>
  <div style="height: 6vh;background-color:#fff;padding:10px 20px;">
    <span style="line-height:60px;font-size:20px;">用户管理</span>
    <el-button @click="dialogVisible=true" type="primary"
               style="float:right;margin-top:13px;">新建用户</el-button>
  </div>
  <!-- 新增用户弹窗 -->
  <el-dialog :title="dialogTitle" style="width:1000px;padding:40px;" v-model="dialogVisible">
    <el-form label-width="80px" label-position="top">
      <el-row :gutter="30">
        <el-col :span="12">
          <el-form-item label="用户名">
            <el-input placeholder="请输入用户名"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="手机号">
            <el-input placeholder="请输入手机号"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="30">
        <el-col :span="12">
          <el-form-item label="邮箱">
            <el-input placeholder="请输入邮箱"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="年龄">
            <el-input placeholder="请输入年龄"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="30">
        <el-col :span="6">
          <el-form-item label="职级">
            <el-select placeholder="请选择">
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
            <el-select placeholder="请选择">
              <!-- <el-option label="shaoyun" value="1"></el-option> -->
              <!-- <el-option label="mike" value="2"></el-option> -->
              <el-option v-for="item in leaderOption" :label="item.username" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="4">
          <el-form-item label="性别">
            <el-radio-group>
              <el-radio label="1" border style="margin: 0;">男</el-radio>
              <el-radio label="0" border>女</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="用户状态">
            <el-radio-group>
              <el-radio value="1" border style="margin: 0;">启用</el-radio>
              <el-radio value="0" border>禁用</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <template #footer>
      <el-button>取消</el-button>
      <el-button type="primary">保存</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import {ref} from "vue";

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
</script>

<style scoped>

</style>