
import store from './store'
import Vue from 'vue'
import App from './App.vue'
import router from './router'

import Element from 'element-ui'
import axios from 'axios'
import VueAxios from 'vue-axios'

import './assets/css/main.css'

import "element-ui/lib/theme-chalk/index.css"
import 'babel-polyfill'


import 'element-ui/lib/theme-chalk/index.css'
Vue.use(Element);
//显示声明使用router 这里是import router的
Vue.use(router);
//挂载axios
Vue.use(VueAxios,axios)
// Buefy
import Buefy from 'buefy'
import 'buefy/dist/buefy.css'
// ElementUI
import ElementUI from 'element-ui';

import VueCookies from 'vue-cookies'
Vue.use(VueCookies)


//import './assets/app.css'
Vue.use(Buefy);
Vue.use(ElementUI);

axios.defaults.withCredentials = true;  //允许跨域
//Content-Type 响应头
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8';
//基础url
axios.defaults.baseURL = "http://localhost:8000";

import relativeTime from 'dayjs/plugin/relativeTime';
// 国际化
import 'dayjs/locale/zh-cn'
const dayjs = require('dayjs');

// 相对时间插件
dayjs.extend(relativeTime)
dayjs.locale('zh-cn') // use locale globally
dayjs().locale('zh-cn').format() // use locale in a specific instance

Vue.prototype.dayjs = dayjs;//可以全局使用dayjs

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
