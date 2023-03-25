<template>
  <div class="home" style="padding-left: 10px;padding-right: 10px;padding-bottom: 10px; margin: 50px 10px 0 10px;">
    <!--    按钮区域-->
    <div style="display: flex;position: relative">
      <div style="margin: 10px 0;position: relative;">
        <el-button type="primary" @click="add()">新增</el-button>
      </div>
      <!--    搜索区-->
      <div style="margin: 10px 0;position: absolute;left: 80%">
        <el-input v-model="search" clearable placeholder="请输入内容" style="width: 60%"/>
        <el-button type="primary" @click="load" >查询</el-button>
      </div>
    </div>
    <!--    顶部分页-->
    <div >
      <!-- 页码和页尺寸使用v-model双向绑定，无需在判断中再次赋值-->
      <el-pagination
          v-model:currentPage="currentPage"
          v-model:page-size="pageSize"
          :small="small"
          :disabled="disabled"
          :background="background"
          :page-sizes="[5, 10, 20]"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"/>
    </div>
    <!--    表格-->
    <el-table :data="tableData" border style="width: 100%" height="calc(100vh - 200px)">
      <!--      带排序-->
      <el-table-column prop="userId" label="用户ID" sortable  />
      <el-table-column prop="userTel" label="用户手机号(账号)" />
      <el-table-column prop="userPwd" label="用户密码" />
      <el-table-column prop="userName" label="用户名字" />
      <!--      功能区域-->
      <el-table-column fixed="right" label="操作" width="120">
        <template #default="scope">
          <el-button link type="primary" size="small" @click="handleEdit(scope.row)">编辑</el-button>
          <el-popconfirm
              @confirm="handleDelete(scope.row.userId)"
              confirm-button-text="确认"
              cancel-button-text="取消"
              :icon="InfoFilled"
              icon-color="#626AEF"
              title="确认删除?">
            <template #reference>
              <el-button link type="primary" size="small" >删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <!--    底部分页-->
    <div >
      <!-- 页码和页尺寸使用v-model双向绑定，无需在判断中再次赋值-->
      <el-pagination
          v-model:currentPage="currentPage"
          v-model:page-size="pageSize"
          :small="small"
          :disabled="disabled"
          :background="background"
          :page-sizes="[5, 10, 20]"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"/>
    </div>



    <!--弹窗-->
    <el-dialog v-model="dialogVisible" title="Tips" width="50%">
      <el-form label-width="120px" :rules="rules" :model="form" ref="form">
        <el-form-item label="用户账号" prop="userTel">
          <el-input v-model="form.userTel" style="width: 50%"></el-input>
        </el-form-item>
        <el-form-item label="用户密码" prop="userPwd">
          <el-input v-model="form.userPwd" style="width: 50%"></el-input>
        </el-form-item>
        <el-form-item label="用户名字" prop="userName">
          <el-input v-model="form.userName" style="width: 50%"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="save">确认</el-button>
      </span>
      </template>
    </el-dialog>
    <!--弹窗2-->
    <el-dialog title="详情" v-model="vis" width="50%">
      <el-card>
        <div v-html="detail" style="min-height: 400px"></div>
      </el-card>
    </el-dialog>


  </div>
</template>

<script>
import request from "../utils/request";

export default {
  name: 'User',
  components: {
  },
  data() {
    return {
      form:{},
      //表单验证
      rules:{
        userTel:[
          { required: true, message: '请输入用户账号', trigger:'blur'},
        ],
        userPwd:[
          { required: true, message: '请输入用户密码', trigger:'blur'},
        ],
        userName:[
          { required: true, message: '请输入用户名', trigger:'blur'},
        ],
      },
      search:'',//搜索词
      total:0,//数据总数
      currentPage:1,//当前页
      pageSize:10,//默认页均数据
      disabled:false,//是否禁用分页
      small:false,//是否用小样式
      background:false,//是否为分页按钮添加背景色
      dialogVisible:false,//弹窗默认为关闭
      vis:false,          //详情弹窗默认为关闭
      //表格数据
      tableData:[],
    }
  },
  //页面启动时自动加载表格
  created() {
    this.load()
  },
  methods:{
    //查询
    load(){
      request.get("/user",{
        params:{
          pageNum:this.currentPage,
          pageSize:this.pageSize,
          search:this.search
        }
      }).then(res => {
        console.log(res.data.records)
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    //新增按钮
    add(){
      this.dialogVisible = true
      this.form={}
    },
    //新增保存按钮
    save(){
      this.$refs['form'].validate((valid) => {    //前端验证输入
        if (valid) {
          if(this.form.userId){
            console.log(this.form.userId)
            // 更新
            request.put("/user",this.form).then(res => {
              console.log(res)
              if(res.code === '0'){
                this.$message({
                  type:"success",
                  message:"更新成功"
                })
              }else {
                this.$message({
                  type:"error",
                  message:res.msg
                })
              }
            })
          }else{//新增
            console.log(this.form.userId)
            request.post("/user",this.form).then(res => {
              console.log(res)
              if(res.code === '0'){
                this.$message({
                  type:"success",
                  message:"新增成功"
                })
              }else {
                this.$message({
                  type:"error",
                  message:res.msg
                })
              }
            })
          }
          this.load();                  //重载当前页面，刷新表单
          this.dialogVisible = false;   //操作完成关闭窗口
        }
      })

    },
    handleEdit(row){
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible = true
    },
    handleDelete(userId){
      console.log(userId)
      request.delete("/user/" + userId).then(res => {
        if(res.code === '0'){
          this.$message({
            type:"success",
            message:"删除成功"
          })
        }else {
          this.$message({
            type:"error",
            message:res.msg
          })
        }
        this.load()   //删除后刷新表格
      })
    },
    handleSizeChange(){       //改变当前每页的数据个数
      this.load()
    },
    handleCurrentChange(){    //改变当前页数
      this.load()
    }
  }
}
</script>
