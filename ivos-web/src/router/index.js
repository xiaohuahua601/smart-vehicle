import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import {ElMessage} from "element-plus";


const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView,
  //  新增对应的子路径
    redirect:'/user',
    children:[
      {path: '/user', component: () => import('../views/user/UserView.vue')},
      {path: '/vehicle', component: () => import('../views/vehicle/VehicleView.vue')},
      {path: '/geofence', component: () => import('../views/geofence/GeofenceView.vue')},
      {path: '/geofenceMap', component: () => import('../views/geofence/GeofenceMapView.vue')},
      {path: '/application', component: () => import('../views/schedule/ApplicationView.vue')},
      {path: '/audit', component: () => import('../views/schedule/AuditView.vue')},
      {path: '/distribute', component: () => import('../views/schedule/DistributeView.vue')}
    ]
  },
  {
    path: '/login',
    component: () => import( '../views/LoginView.vue')
  }

]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

/**
 * 在路由切换之前判断用户是否已登录。
 * 如果用户未登录(localStorage里没登录的用户数据)且试图访问需要登录
 * @param {Object} to - 将要进入的目标路由对象
 * @param {Object} from - 当前的路由对象
 * @param {Function} next - 前进到下一个路由的函数
 */
router.beforeEach((to, from, next) => {
  // 从本地存储中获取用户信息
  let user = localStorage.user;
  // 判断如果用户未登录且试图访问的页面不是登录页，则重定向到登录页，并提示需先登录
  if (to.path !== '/login' && !user && to.path !== '/') {
    next({ path: '/login', query: { redirect: to.fullPath } },
        ElMessage.error('请先登录'));
  } else {
    // 允许路由切换
    next();
  }
});

export default router
