import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

//1.引入Element框架相关内容
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
//2.引入EL图标相关内容 https://element-plus.org/zh-CN/component/icon.html#注册所有图标
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

//3.createApp(App)创建一个vue实例
const app = createApp(App)

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

//4.app.use(ElementPlus)添加EL .use(store)添加全局存储vuex .use(router)添加路由router
//mount挂载,将前面.use的内容放到/public/index.html页面中
app.use(ElementPlus).use(store).use(router).mount('#app')


