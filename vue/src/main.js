// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
//引入ElementUI
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import store from './store'
import axios from './axios'
// 全域性註冊，之後可在其他元件中通過 this.$axios 傳送資料
Vue.prototype.$axios = axios
Vue.prototype.$store = store 
Vue.config.productionTip = false

//mock.js 創建後端回端資料用
// require('./mock.js')

/* eslint-disable no-new */

Vue.use(ElementUI)
//鉤子函式，訪問路由前呼叫
// router.beforeEach((to, from, next) => {
//   //路由需要認證
//   if (to.meta.requireAuth) {

//     //判斷store裡是否有token
//     if (store.state.token) {
//       next()
//     } else {
//       next({
//         path: 'login',
//         query: {
//     redirect: to.fullPath }
//       })
//     }
//   } else {
//     next()
//   }
// }
// )

new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})