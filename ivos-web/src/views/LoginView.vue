<template>
  <div style="height: 100vh;
  background-image: linear-gradient(135deg,#1352F2,#3C82F5);">
    <el-container style="padding:30px">
      <el-header>
        <el-row>
          <el-col :span="3">
            <img src="/imgs/login/logo.png" style="height: 50px">
          </el-col>
          <el-col :span="1">
            <img src="/imgs/login/auto.png"  style="height: 50px">
          </el-col>
          <el-col :span="1">
            <img src="/imgs/login/verticalLine.png"  style="height: 50px">
          </el-col>
          <el-col :span="19" style="text-align: left">
            <span style="color: #fff;font-size: 30px;font-weight: bold"> 智慧车辆运营管理系统 </span>
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
                  <el-input placeholder="请输入用户名" ></el-input>
                </el-form-item>
                <el-form-item label="密码">
                  <el-input placeholder="请输入密码" type="password"></el-input>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" style="width:100%;">登录</el-button>
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

</script>

<style scoped>

</style>