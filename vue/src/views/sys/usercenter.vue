<template>

  <body id="page">

    <el-form :model="updateForm" status-icon :rules="rules" ref="updateForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="帳號" prop="username">
        <el-input type="username" v-model="updateForm.username" autocomplete="off" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="密碼" prop="pass">
        <el-input type="password" v-model="updateForm.password" autocomplete="off"></el-input>
      </el-form-item>

      <el-form-item label="確認密碼" prop="checkPass">
        <el-input type="password" v-model="updateForm.checkPass" autocomplete="off"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submitForm('updateForm')">提交</el-button>
        <el-button @click="resetForm('updateForm')">重置</el-button>
      </el-form-item>

    </el-form>
  </body>
</template>

<script>
  export default {
    name: "usercenter",
    data() {

      var validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('請輸入密碼'));
        } else {
          if (this.updateForm.checkPass !== '') {
            this.$refs.updateForm.validateField('checkPass');
          }
          callback();
        }
      };
      var validatePass2 = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('請再次輸入密碼'));
        } else if (value !== this.updateForm.password) {
          callback(new Error('兩次密碼不一致!'));
        } else {
          callback();
        }
      };
      return {

        updateForm: {
          username: '',
          password: '',
        },
        rules: {
          pass: [{
            validator: validatePass,
            trigger: 'blur'
          }],
          checkPass: [{
            validator: validatePass2,
            trigger: 'blur'
          }]
        }
      };
    },
    created() {
      this.getinfo();
    },
    methods: {
      getinfo() {
        this.$axios.get("/getuserinfo").then(res => {
          console.log(res.data)
          this.updateForm.username = res.data.data.username

        })
      },
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          console.log( this.updateForm.username)
          console.log( this.updateForm.pass)
          if (valid) {
            this.$axios.put("/updatepwd",this.updateForm
            ).then(res => {
              console.log(res.data)
              this.updateForm.username = res.data.data.username
              this.$message({
                message: res.data.message,
                type: 'success',
              })
              this.$router.push('/users')
            })
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      }
    }
  }

</script>

<style scoped>
  .el-input {
    width: 500px;
  }

</style>
