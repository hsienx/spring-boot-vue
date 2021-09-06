import Vue from 'vue'
import Router from 'vue-router'

import Login from '@/views/Login.vue'
import Home from '@/views/home.vue'

import Index from '@/views/Index.vue'
import User from '@/views/sys/User.vue'
import UserCenter from '@/views/sys/UserCenter.vue'
import Signup from '@/views/signUp.vue'
Vue.use(Router)

const router = new Router({
  routes: [{
    path: '/home',
    name: 'home',
    component: Home,
    children: [{
        path: '/index',
        name: 'index',
        component: Index , 
        meta: {
            requireAuth: true
          }
      },{
        path: '/users',
        name: 'SysUser',
        component: User   
      },{
        path: '/usercenter',
        name: 'usercenter',
        component: UserCenter   
      }
    ]
  },
  
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/signup',
    name: 'SignUp',
    component: Signup,  
   
  
  }
]
})

// //勾點函數，存取路由前呼叫
// router.beforeEach((to, from, next) => {
//   let token=localStorage.getItem('token')
//   //路由需要認證
//     if (to.path=='/login' || to.path=='/signup') {
//       next()
//     } else if(!token) {
//       next({
//         path: 'login'
//        })
//     }
// }

// )


export default router


 
  


