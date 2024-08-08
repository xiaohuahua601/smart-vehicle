import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

//1.引入Element框架相关内容
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
//2.引入EL图标相关内容 https://element-plus.org/zh-CN/component/icon.html#注册所有图标
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
//手动引入
import axios from "axios";
//5.配置根路径
const BASE_URL = 'http://localhost:8080';
//window里面添加的内容是全局内容，可以在任意script标签内部使用
window.BASE_URL = BASE_URL

//3.createApp(App)创建一个vue实例
const app = createApp(App)

//6.在VUE实例中也要添加全局内容,BASE_URL才可以在template标签内部使用
app.config.globalProperties.BASE_URL = BASE_URL

//11.开启跨域携带 Cookie
window.axios = axios;
axios.defaults.withCredentials = true;
//12.替换全局axios  注意之后不需要在自己的前端vue中直接使用axios即可  无需再次引入
app.config.globalProperties.$axios = axios;

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

//4.app.use(ElementPlus)添加EL .use(store)添加全局存储vuex .use(router)添加路由router
//mount挂载,将前面.use的内容放到/public/index.html页面中
app.use(ElementPlus).use(store).use(router).mount('#app')

//7.定义获取用户信息的全局方法
window.getUser = ()=>{
    return localStorage.user?JSON.parse(localStorage.user):null
}


