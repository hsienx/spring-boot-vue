<template>

  <body id="page">

    <!-- 收尋框 -->
    <el-form :inline="true" :model="searchForm" class="searchForm">
      <el-form-item label="帳號">
        <el-input v-model="searchForm.account" placeholder="帳號" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="getAlldata()">查询</el-button>
      </el-form-item>

    </el-form>

    <!-- table -->

    <el-table :data="tableData" style="width: 100%">
      <el-table-column label="姓名" width="180" prop="cname">
      </el-table-column>
      <el-table-column label="帳號" width="180" prop="username">
      </el-table-column>
      <el-table-column label="地址" width="180" prop="address">
      </el-table-column>
      <el-table-column label="電話" width="180" prop="tel">
      </el-table-column>

      <el-table-column label="狀態" width="180" prop="type">

        <template slot-scope="scope">
          <el-tag size='small' v-if="scope.row.status === '0' " type='info'>使用</el-tag>
          <el-tag size='small' v-else-if="scope.row.status === '1' " type='success'>VIP</el-tag>
          <el-tag size='small' v-else-if="scope.row.status === '2' " type='danger'>停用</el-tag>
        </template>
      </el-table-column>

      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.row.id)">編輯</el-button>

          <template>
            <el-popconfirm title="確認刪除?" @confirm="handleDelete(scope.row.id)">
              <el-button slot="reference" type="danger" size="mini">删除</el-button>
            </el-popconfirm>
          </template>

        </template>
      </el-table-column>
    </el-table>

    <!-- 分頁 -->
    <el-pagination background layout="prev, pager, next" :page-count="pagetotal" @current-change="handleCurrentChange">
    </el-pagination>

    <!-- 彈出視窗 -->
    <el-dialog title="修改" :visible.sync="dialogVisible" width="40%" :before-close="handleClose" :append-to-body="true">
      <el-form :model="updateForm" status-icon :rules="rules" ref="updateForm" label-width="100px" class="updateForm">
        <el-form-item label="帳號" prop="username">
          <el-input type="account" v-model="updateForm.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="cname">
          <el-input type="username" v-model="updateForm.cname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="電話" prop="tel">
          <el-input type="tel" v-model="updateForm.tel" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input type="address" v-model="updateForm.address" autocomplete="off"></el-input>
        </el-form-item>


        <el-form-item>
          <el-button type="primary" @click="submitUpdateForm('updateForm')">提交</el-button>

        </el-form-item>
      </el-form>

    </el-dialog>
  </body>
</template>

<script>
  export default {
    name: "users",
    data() {
      return {
        //對話框不顯示
        dialogVisible: false,

        tableData: [],

        total: 0,
        current: 1,
        size: 9,
        pagetotal:0,

        updateForm: {
          username: '',
          cname: '',
          tel: '',
          address: '',

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
          }, ]

        },

        searchForm: {
          account: ''
        },

      };
    },
    created() {
      this.getAlldata();
    },
    methods: {
      getAlldata() {
        this.$axios.get("/getall", {
          params: {
            account: this.searchForm.account,
            current: this.current,
            size: this.size
          }
        }).then(res => {
          console.log(res.data)
          this.tableData = res.data.data.members
          this.size = res.data.data.size
          this.current = res.data.data.current
          this.total = res.data.data.total
          this.pagetotal=res.data.data.total
          console.log( this.pagetotal)
        })
      },

      handleEdit(id) {
        console.log('handleEdit')
        this.$axios.get("/getbyid/" + id).then(res => {
          console.log(res.data)
          this.updateForm = res.data.data
          this.dialogVisible = true
        })

      },
      handleDelete(id) {
        console.log('delete');
        this.$axios.delete('/delete/' + id).then(res => {
          this.$message({
            message: res.data.message,
            type: 'success',
          })
          this.getAlldata()
        })

      },
      submitUpdateForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            console.log("submit")
            this.$axios.put('/update', this.updateForm).then(res => {
              this.$message({
                message: res.data.message,
                type: 'success'
              })

              this.dialogVisible = false
              this.getAlldata()
            })
          }
        })
      },
      // handleSizeChange(val) {
      //   console.log(`每頁 ${val} 筆`);
      //   this.size = val
      //   this.getAlldata()
      // },
      handleCurrentChange(val) {
        console.log(`當前頁: ${val}`);
        this.current = val
        this.getAlldata()
      },
      resetform() {
        this.dialogVisible = false
        this.updateForm = {}
      },
      handleClose() {
        this.resetform();
      },
    }
  };

</script>

<style scoped>

</style>
