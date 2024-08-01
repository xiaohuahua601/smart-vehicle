<template>
<h1>循环和隐藏</h1>
<!--  v-for="元素 in 数组"-->
  <ul>
    <li v-for="name in arr">{{name}}</li>
  </ul>
  <!--  v-for="(元素,下标) in 数组"-->
  <ul>
    <li v-for="(name2,index) in arr">{{index+":"+name2}}</li>
  </ul>
<hr>
  <table border="1px">
    <caption>商品列表</caption>
<!--    四列表格-->
    <tr>
      <th>序号</th>
      <th>商品标题</th>
      <th>商品价格</th>
      <th>商品库存</th>
      <th>操作</th>
    </tr>
    <tr v-for="(p,index) in productArr">
      <td>{{index+1}}</td>
      <td>{{p.title}}</td>
      <td>{{p.price}}</td>
      <td>{{p.num}}</td>
      <td><button @click="del(index)">删除</button></td>
    </tr>
  </table>
  <hr>
<!--  尝试 输入商品信息然后回显在表格中-->
  <h2>请输入信息</h2>
  商品标题 <input type="text" v-model="product.title"><br>
  商品价格 <input type="text" v-model="product.price"><br>
  商品库存 <input type="text" v-model="product.num"><br>
  <button @click="add()">提交</button><br>
  <hr>
<!--  隐藏  判断 成不成立 v-if="" true false v-else  js-->
  <p v-if="true">张三</p>
  <p v-if="false">李四</p>
  <p v-if="info">王五</p>
<!--  下面三个是一组的-->
  <p v-if="false">太阳</p>
  <p v-if="false">月亮</p>
  <p v-else>星星</p>
<!--  v-show 控制我们的元素是否显示 css -->
  <p v-show="false">小红</p>
  <p v-show="true">小绿</p>
</template>

<script setup>
//遍历对象---》数组对象
import {ref} from "vue";
//[v,v,v,v]  基本数据类型
//[{},{},{},{}]  对象
const arr = ref(["张三","李四","王五"])

const productArr = ref([
  {title:'小米手机',price:5000,num:800},
  {title:'华为手机',price:6000,num:900},
  {title:'苹果手机',price:7000,num:100},
  {title:'三星手机',price:8000,num:200},
  {title:'三星手机',price:8000,num:200}
]);
const del = (index)=>{
  //splice函数 删除数组的内容 下标 删除的长度
  productArr.value.splice(index,1)
}
const info = ref(true)

const product = ref({
  title:"",
  price:"",
  num:""
})
const add = ()=>{
//  push 给指定数组追加元素
  productArr.value.push(product.value)
  //清空子对象
  product.value={
    title:"",
    price:"",
    num:""}
}
</script>

<style scoped>

</style>