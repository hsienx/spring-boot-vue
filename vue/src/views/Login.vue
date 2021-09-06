<template>
<body id="page">
  <el-row type="flex" class="row-bg" justify="center">

    <el-col :xl="6" :lg="7">
      <div class="grid-content bg-purple">
        <h2>歡迎來到系統</h2>
        <el-image :src="require('@/assets/logo.png')"></el-image>
      </div>
    </el-col>

    <el-col :span="1">
      <el-divider direction="vertical"></el-divider>
    </el-col>

    <el-col :xl="6" :lg="7">
      <div class="grid-content bg-purple-light">
        <!--       model=form表單               rules=規則      ref=綁定 script methods ref[formname]      --->
        <el-form :model="loginForm" status-icon :rules="rules" ref="loginForm" label-width="80px" class="demo-ruleForm">

          <el-form-item label="帳號" prop="account" style="width : 380px">
            <el-input  v-model="loginForm.username"  ></el-input>
          </el-form-item>


          <el-form-item label="密碼" prop="password" style="width : 380px">
            <el-input type='password' v-model="loginForm.password"  ></el-input>
          </el-form-item>

          <el-form-item label="認證碼" prop="code" style="width : 380px">
            <el-input  v-model="loginForm.code" style=" width: 170px; float: left"></el-input>
             <el-image :src="captchImg" class="captchImg" @click="getCaptchImg()"></el-image>
          </el-form-item>

          <el-form-item>
            <router-link to="/signup" style="float: left" >註冊</router-link>
            <el-button type="primary" @click="submitForm('loginForm')">提交</el-button>
            <el-button @click="resetForm('loginForm')">重置</el-button>
          </el-form-item>

        </el-form>
      </div>
    </el-col>

  </el-row>

</body>
</template>

<script>
import qs from 'qs'
  export default {
    name: "login",

    data() { 
      return {
        loginForm: {
        username:"",
        password: '',
        code: '',
        token:''
        },
        rules: {
          username: [
            { required: true, message: '請輸入帳號', trigger: 'blur' },
          ],
          password: [
            { required: true, message: '請輸入密碼', trigger: 'blur' },
          ],
          code: [
            { required: true, message: '請輸入驗證碼', trigger: 'blur' },
            { min: 5, max: 5, message: '長度為 5 個字符', trigger: 'blur' }
          ],
        },
        captchImg :null,
      };
    },
      created(){
        this.getCaptchImg()
      },

    methods: {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            console.log('submitForm')
            this.$axios.post('/login?' +qs.stringify(this.loginForm)).then(res =>{
              console.log(res)
              const jwt=res.headers['authorization']
              
              this.$store.commit('SET_TOKEN',jwt)
              this.$router.push('/users')
            },error=>{
              this.getCaptchImg();
            })
            
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
      getCaptchImg(){
        console.log('getCaptchImg')
         this.$axios.get("/captcha").then(res =>{
           // console.log( res.data.data)
              this.loginForm.token= res.data.data.token
              this.captchImg= res.data.data.captchImg
              this.loginForm.code=''
            })

      }
      
     //res=data{code ,msg,data{}}
    }
  }

</script>

<style >


.el-row{
    background-color:#fafafa;
    height: 100%;
    display: flex;
    align-items: center;
    text-align: center;
}


.el-divider{
    height: 200px;
}

.captchImg{
  float: left;
  margin-left: 8px;
  border-radius: 4px;
}
</style>
