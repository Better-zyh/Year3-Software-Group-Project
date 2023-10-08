import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Login',
    component: () => import('../views/Login') //相当于先import 再写在这里。这个直接把import写这里了
  },
  {
    path: '/home',
    name: 'Home',
    component: () => import('../views/Home') //相当于先import 再写在这里。这个直接把import写这里了
  },
  {
    path: '/post',
    name: 'Post',
    component: () => import('../views/Post') //相当于先import 再写在这里。这个直接把import写这里了
  },
  {
    path: '/postHeader',
    name: 'postHeader',
    component: () => import('../components/MainHeader') //相当于先import 再写在这里。这个直接把import写这里了
  },
  {
    path: '/identifying',
    name: 'identifying',
    component: () => import('../views/identifying') //相当于先import 再写在这里。这个直接把import写这里了
  },
  {
    name: "postDetail",
    path: "/post/:id",
    component: () => import("../views/Detail"),
  },
  {
    name: 'search',
    path: '/search',
    component: () => import('../views/Search'),
  },
  {
    name: 'tag',
    path: '/tag/:name',
    component: () => import('../views/tagSearch'),
  },
  {
    path: '/Profile',
    name: 'Profile',
    component: () => import('../views/Profile') //相当于先import 再写在这里。这个直接把import写这里了
  },
]

const router = new VueRouter({

  routes
})

export default router

