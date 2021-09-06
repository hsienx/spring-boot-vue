 <template>


   <el-menu default-active="0" class="el-menu-vertical" background-color="#545c64" text-color="#fff"
     active-text-color="#ffd04b">
     <router-link to="/index">
       <el-menu-item index="0">
         <template slot="title">
           <i class="el-icon-location"></i>
           <span>首頁</span>
         </template>
       </el-menu-item>
     </router-link>


     <el-submenu index="1">
       <template slot="title">
         <i class="el-icon-location"></i>
         <span>系統管理</span>
       </template>
       <router-link to="/users">
         <el-menu-item index="1-1">
           <template slot="title">
             <i class="el-icon-s-custom"></i>
             <span>用戶管理</span>
           </template>
         </el-menu-item>
       </router-link>
       <router-link to="/role">
         <el-menu-item index="1-2">
           <template slot="title">
             <i class="el-icon-s-operation"></i>
             <span>角色管理</span>
           </template>
         </el-menu-item>
       </router-link>
       <router-link to="/menu">
         <el-menu-item index="1-3">
           <template slot="title">
             <i class="el-icon-rank"></i>
             <span>菜單管理</span>
           </template>
         </el-menu-item>
       </router-link>


     </el-submenu>
   </el-menu>

 </template>
 <script>
   export default {
     name: 'SideMenu'
   }

 </script>

 <style scoped>
   .el-menu-vertical {
     height: 100%;
   }

 </style>
