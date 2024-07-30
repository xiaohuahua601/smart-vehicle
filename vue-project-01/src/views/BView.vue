<template>
<h1>属性相关的练习</h1>
<!--  属性相关的绑定 v-bind:属性名="变量名称"-->
   <input type="text" value="tom"><br>
  <input type="text" v-bind:value="info"><br>
<!-- v-bind可以省略 -->
  <input type="text" :value="info"><br>
  <hr>
  <a :href="url">超链接</a>
</template>

<script setup>
import {ref} from "vue";

const info = ref("Jerry3333")
const url = ref("https://www.baidu.com/")
</script>

<style scoped>

</style>