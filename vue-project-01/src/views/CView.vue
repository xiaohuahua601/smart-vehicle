<template>
<h1>双向绑定练习</h1>
  <h2>用户信息的收集</h2>
<!--  v-model双向绑定-->
  <input type="text" placeholder="请输入用户名" v-model="user.username"><br>
  <input type="password" placeholder="请输入密码" v-model="user.password"><br>
  性别<br>
  <input type="radio" name="sex" value="1" v-model="user.sex">男
  <input type="radio" name="sex" value="2" v-model="user.sex">女
  <input type="radio" name="sex" value="3" v-model="user.sex">未知
  <br>
<!--  绑定触发函数-->
  <button @click="f()">注册</button>
</template>

<script setup>
//类 和 属性
//定义对象 {属性:属性值}  map
// {
//   username:"tom",
//       age:18
//
// }
import {ref} from "vue";

const user = ref({
  username:"tom",
  password:"123456",
  sex:"1"
})
//()=>{}  简化版本的函数定义方式
const f = ()=>{
//  输出对应的user变量
  console.log(user.value)
}
</script>

<style scoped>

</style>