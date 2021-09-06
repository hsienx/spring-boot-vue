<template>

  <el-row type="flex" class="row-bg" justify="center" align="center">

    <el-col>
        <div>Main</div>
    </el-col>

    <el-col>
      <el-image :src="require('@/assets/logo.png')"></el-image>
    </el-col>
    
  </el-row>

</template>

<script>
  export default {
    name: 'index',

  }

</script>

<style scoped>
 .el-col {
   padding: 50px;
 }
</style>
