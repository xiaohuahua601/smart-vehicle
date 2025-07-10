<template>
  <div style="height: 100vh;
  background-image: linear-gradient(135deg,#1352F2,#3C82F5);">
    <el-container style="padding:30px">
      <el-header>
        <el-row>
          <el-col :span="1">
            <img src="/imgs/login/auto.png"  style="height: 50px">
          </el-col>
          <el-col :span="1">
            <img src="/imgs/login/verticalLine.png"  style="height: 50px">
          </el-col>
          <el-col :span="19" style="text-align: left">
            <span style="color: #fff;font-size: 30px;font-weight: bold"> 智链行 </span>
          </el-col>
        </el-row>
      </el-header>
      <el-main>
        <el-row>
          <el-col :span="16">
            <img src="/imgs/login/bg.png" style="width: 100%;margin-top: 60px">
          </el-col>
          <el-col :span="8">
            <el-card style="max-width: 480px">
              <h3 style="text-align: center">用户登录</h3>
              <el-form label-position="top">
                <el-form-item label="用户名">
                  <el-input placeholder="请输入用户名" v-model="user.username"></el-input>
                </el-form-item>
                <el-form-item label="密码">
                  <el-input placeholder="请输入密码" type="password" v-model="user.password"
                   @keydown.enter="login()"></el-input>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" style="width:100%;" @click="login()">登录</el-button>
                </el-form-item>
              </el-form>
            </el-card>
          </el-col>
        </el-row>
      </el-main>
    </el-container>
  </div>
</template>

<script setup>
import {ref} from "vue";
import axios from "axios";
import {ElMessage} from "element-plus";
import qs from "qs";
import router from "@/router";
//登录的user对象
const user = ref({username:'',password:''})
//登录方法
const login = ()=>{
  console.log(user)
  //给后端发请求 axios get post
  //url  http://localhost:8080/v1/user/login
  // data  {}--->''--->自动映射---》qs
  //作用：将JS对象转为查询字符串格式  username=tom&password=123456
  let data = qs.stringify(user.value);
  axios.post(BASE_URL+'/login',data).then(response=>{
    console.log(response)
    if (response.data.code == 2000){
      //从顶部出现，3秒消失
      ElMessage.success('登陆成功');
      //localStorage以字符串键值对的方式管理数据
      //我们可以将登录成功的用户信息存入,方便其它地方使用
      //user是JS对象,不是字符串,所以需要先转换成字符串再存入JSON.stringify(user)
      // k [user] : v [response.data.data]
      localStorage.user = JSON.stringify(response.data.data);
      //
      router.push('/user');
    }else {
      ElMessage.error(response.data.msg);
    }
  })
}
</script>

<style scoped>

</style>