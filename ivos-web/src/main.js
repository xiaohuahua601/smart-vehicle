import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

//1.引入Element框架相关内容
import ElementPlus,{ElMessage} from 'element-plus'
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
//13.axios相应拦截器,处理Security的响应状态码
axios.interceptors.response.use(
    response => response,
    error => {
        console.log(error);
        ElMessage.error(error.response.data.msg);
        //授权失败的时候重新加载页面  会清空user对象信息  然后结合index.js会跳转到login
        if (error.response.status == 401 || error.response.status == 403) {
            localStorage.removeItem('user');
            localStorage.removeItem('breadcrumb');
            //window.location.href = '/login';
             window.location.reload()
        }else if(error.response.data.path.indexOf('login')!=-1&&error.response.status == 404){
            window.location.reload()
        }
    }
)

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

//8.解决ResizeObserver Error
//防抖函数 它接受一个函数 fn 和一个延迟时间 delay
//通过包装 ResizeObserver 的回调函数到一个防抖（debounce）函数中，
//你减少了回调函数的执行频率，从而可能提高了应用的性能
const debounce = (fn, delay) => {
    let timer = null;
    return function () {
        let context = this;
        let args = arguments;
        clearTimeout(timer);
        timer = setTimeout(function () {
            fn.apply(context, args);
        }, delay);
    }
}
//覆盖
const _ResizeObserver = window.ResizeObserver;
window.ResizeObserver = class ResizeObserver extends _ResizeObserver {
    constructor(callback) {
        callback = debounce(callback, 16);
        super(callback);
    }
}

