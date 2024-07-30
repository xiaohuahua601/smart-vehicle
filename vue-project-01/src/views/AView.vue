<template>
<h1>文本相关练习</h1>
<!--  插值表达式{{参数}}-->
  <p>{{info}}</p>
<!--  字符串拼接-->
  <b>{{info+':7月29'}}</b>
</template>

<!--vue3的特性 需要在script标签中叫一个属性setup-->
<script setup>
import {ref} from "vue";

//定义一个变量[常量]
const info = ref("今天天气真冷")

</script>

<style scoped>

</style>