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
    <el-table :data="tableData" border style="width: 100%;" height="calc(100vh - 200px)">
      <!--      带排序-->
      <el-table-column prop="newId" label="新闻ID" sortable width="100" />
      <el-table-column prop="type" label="标签" width="150"/>
      <el-table-column prop="author" label="作者" width="200"/>
      <el-table-column prop="title" label="新闻标题" />
      <el-table-column prop="pubtime" label="发布时间" width="200"/>
      <!--      功能区域-->
      <el-table-column fixed="right" label="操作" width="150">
        <template #default="scope">
          <el-button link type="primary" size="small" @click="details(scope.row.content)">详情</el-button>
          <el-button link type="primary" size="small" @click="handleEdit(scope.row)">编辑</el-button>
          <el-popconfirm
              @confirm="handleDelete(scope.row.newId)"
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
        <el-form-item label="新闻标题" prop="title">
          <el-input v-model="form.title" style="width: 50%"></el-input>
        </el-form-item>
        <el-form-item label="新闻分类">
          <el-input v-model="form.type" style="width: 50%"></el-input>
        </el-form-item>
        <el-form-item label="作者">
          <el-input v-model="form.author" style="width: 50%"></el-input>
        </el-form-item>
        <el-form-item label="新闻内容" prop="content">
          <el-input type="textarea" v-model="form.content" style="width: 80%"></el-input>
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
  name: 'HomeView',
  components: {
  },
  data() {
    return {
      form:{},
      //表单验证
      rules:{
        title:[
          { required: true, message: '请输入新闻标题', trigger:'blur'},
        ],
        content:[
          { required: true, message: '请输入新闻内容', trigger:'blur'},
        ]
      },
      search:'',//搜索词
      total:0,//数据总数
      currentPage:1,//当前页
      pageSize:20,//默认页均数据
      disabled:false,//是否禁用分页
      small:false,//是否用小样式
      background:false,//是否为分页按钮添加背景色
      dialogVisible:false,//弹窗默认为关闭
      vis:false,          //详情弹窗默认为关闭
      //表格数据
      tableData:[],
      detail:"",
    }
  },
  //页面启动时自动加载表格
  created() {
    this.load()
  },
  methods:{
    //查询
    load(){
      request.get("/news",{
        params:{
          pageNum:this.currentPage,
          pageSize:this.pageSize,
          search:this.search
        }
      }).then(res => {
        console.log(res)
        //日期格式化处理
        // let dat = res.data.records[0].pubtime
        // let arr = dat.split("-")
        // let day = arr[2].split("T")
        // let time = day[1].split(".")
        // let date = arr[0] + "年" + arr[1] + "月" + day[0] + "日 " + time[0]
        // res.data.records[0].pubtime = date
        this.tableData = res.data.records
        console.log(res.data)
        console.log(res.data.records)
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
        if(valid){
          if(this.form.newId){
            console.log(this.form.newId)
            // 更新
            request.put("/news",this.form).then(res => {
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
            console.log(this.form.newId)
            request.post("/news",this.form).then(res => {
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
    details(row){
      this.detail = row
      this.vis = true
    },
    handleEdit(row){
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible = true
    },
    handleDelete(newId){
      console.log(newId)
      request.delete("/news/" + newId).then(res => {
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
<style scoped>
/* 设置浏览器滚动条的样式 */
::-webkit-scrollbar {
  width: 0px;
}
/* 滚动槽 */
/*::-webkit-scrollbar-track {*/
/*  -webkit-box-shadow: inset006pxrgba(0, 0, 0, 0.3);*/
/*  border-radius: 10px;*/
/*}*/
/*!* 滚动条滑块 *!*/
/*::-webkit-scrollbar-thumb {*/
/*  border-radius: 10px;*/
/*  background: rgba(0, 0, 0, 0.1);*/
/*  -webkit-box-shadow: inset006pxrgba(0, 0, 0, 0.5);*/
/*}*/
::-webkit-scrollbar-thumb:window-inactive {
  background: rgba(255, 0, 0, 0.4);
}
</style>