<template>

  <body id="page">
    <el-container>
           <el-aside width="200px">
               <SideMenu></SideMenu>
  
           </el-aside>

      <el-container>
          <el-header>
           <strong>系統管理</strong>

        <div class="header-avatar">

         
          <el-dropdown>
            <span class="el-dropdown-link">
             選單<i class="el-icon-arrow-down el-icon--right"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>
               <router-link to="/usercenter">
                  個人中心
                </router-link>
              </el-dropdown-item>
              <el-dropdown-item @click.native="logout()">登出</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>

          <el-link href="http://yahoo.com.tw">yahoo!</el-link>
          <el-link href="http://google.com.tw">google</el-link>
        </div>
      </el-header>

        <el-main>
          <div style="margin:0 15px;">

          </div>
          <router-view/>
        </el-main>

      </el-container>
    </el-container>
  </body>
</template>

<script>

import SideMenu from './inc/sideMenu.vue'

  export default {
    name: 'Home',
    components :{
        SideMenu
    },data(){
      return {
        userInfo:{
          id:"",
          username:"",
          avatar:""
        }
      }

    },created(){
      //douctment.ready(){}
    
    },
    methods:{
      logout(){
        console.log('logout')
        this.$axios.post("/logout").then(res=>{
          
          localStorage.clear()
          sessionStorage.clear()
          this.$store.commit('restState');
         this.$router.replace({path: '/login'})

        })      
        }

    }
  }

</script>

<style scoped>

  .header-avatar {
    float: right;
    width: 210px;
    display: flex;
    justify-content: space-around;
    align-items: center;
  }


  .el-header {
    background-color: rgb(76, 193, 247);
    color: #333;
    text-align: center;
    line-height: 60px;
  }

  .el-aside {
    background-color: #D3DCE6;
    color: #333;
    text-align: center;
    line-height: 200px;
  }

  .el-main {
    background-color: #E9EEF3;
    color: #333;
    
  }

  body>.el-container {
    padding: 0;
    margin: 0;
    height: 100%;
    
  }

  



</style>
