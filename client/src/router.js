// client/src/router.js
import Vue from 'vue'
import VueRouter from 'vue-router'
import LoginView from '../views/LoginView.vue'
import HomeView from '../views/HomeView.vue'
import RegisterView from '../views/RegisterView.vue'
import SpeedMathsView from '../views/SpeedMathsView.vue'
import AdminView from '../views/AdminView.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Login',
    component: LoginView
  },
  {
    path: '/home',
    name: 'Home',
    component: HomeView
  },
  {
    path: '/register',
    name: 'Register',
    component: RegisterView
  },
  {
    path: '/speedmaths',
    name: 'SpeedMaths',
    component: SpeedMathsView
  },
  {
    path: '/admin',
    name: 'Admin',
    component: AdminView
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL || '/',
  routes
})

export default router // Automatically resolves to './router/index.js', hence why we named this file index.js
