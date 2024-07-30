<template>
<h1>文本相关练习</h1>
<!--  插值表达式{{参数}}-->
  <p>{{info}}</p>
<!--  字符串拼接-->
  <b>{{info+':7月29'}}</b>
<!-- v-text v-html 绑定值 -->
  <hr>
  <i v-text="info"></i> <br>
  <u v-html="info"></u>
<!--text把我们变量里面的内容 直接回显到页面上
html先加载变量里面的内容，检查是否携带标签
如果携带标签就让标签生效 如果不携带标签就正常的回显  -->
  <hr>
  <p v-text="str"></p>
  <p v-html="str"></p>
</template>

<!--vue3的特性 需要在script标签中叫一个属性setup-->
<script setup>
import {ref} from "vue";

//定义一个变量[常量]
const info = ref("今天天气真冷")
const str = ref("今天下<b>大雨</b>")

</script>

<style scoped>

</style>