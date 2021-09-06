<template>

  <body id='page'>

    <el-form :model="signUpForm" status-icon :rules="rules" ref="signUpForm" label-width="100px" class="signUpForm">
      <h3 class="login_title">新用戶註冊</h3>

      <el-form-item label="帳號" prop="username">
        <el-input type="account" v-model="signUpForm.username" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="姓名" prop="cname">
        <el-input type="username" v-model="signUpForm.cname" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="電話" prop="tel">
        <el-input type="tel" v-model="signUpForm.tel" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="地址" prop="address">
        <el-input type="address" v-model="signUpForm.address" autocomplete="off"></el-input>
      </el-form-item>


      <el-form-item label="密碼" prop="password">
        <el-input type="password" v-model="signUpForm.password" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="確認密碼" prop="checkPass">
        <el-input type="password" v-model="signUpForm.checkPass" autocomplete="off"></el-input>
      </el-form-item>
     
      <el-form-item>
        <el-button type="primary" @click="submitForm('signUpForm')">提交</el-button>
        <el-button @click="resetForm('signUpForm')">重置</el-button>
        <el-button @click="relogin()">取消</el-button>
      </el-form-item>
    </el-form>
  </body>

</template>

<script>
  export default {
    name: 'signup',
    data() {
     
      var validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('請輸入密碼'));
        } else {
          if (this.signUpForm.checkPass !== '') {
            this.$refs.signUpForm.validateField('checkPass');
          }
          callback();
        }
      };
      var validatePass2 = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('請再次輸入密碼'));
        } else if (value !== this.signUpForm.password) {
          callback(new Error('兩次輸入密碼不同!'));
        } else {
          callback();
        }
      };
      return {
        signUpForm: {
          username: '',
          cname: '',
          tel: '',
          password: '',
          address: ''
        },
        rules: {
          username: [{
            required: true,
            message: '請輸入帳號',
            trigger: 'blur'
          }, ],
          cname: [{
            required: true,
            message: '請輸入姓名',
            trigger: 'blur'
          }, ],

          password: [{
            validator: validatePass,
            trigger: 'blur'
          }],
          checkPass: [{
            validator: validatePass2,
            trigger: 'blur'
          }],
         
        }
      };
    },
    methods: {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
              console.log("submit")
              this.$axios.post('/add',this.signUpForm).then(res =>{
              this.$message({message: res.data.message,type: 'success' });
              this.$router.replace({path:'/login'})
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
      relogin(){
         this.$router.go(-1)
      }
    }
  }

</script>

<style scoped>

.signUpForm {
  border-radius: 15px;
  background-clip: padding-box;
  margin: 90px auto;
  width: 350px;
  padding: 35px 35px 15px 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}

  .login_title {
    margin: 0px auto 40px auto;
    text-align: center;
    color: #505458;
  }

</style>
