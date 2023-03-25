<template>
  <div style="width: 100%;height: 100vh;background-color: #142A31;overflow: hidden" class="bg">
    <div  style="display:flex;
          justify-content: space-between;
          align-items: center;
          width: 1000px;height: 500px;margin: 150px auto;
          background-color: rgba(255,255,255,0.85);border-radius: 2.5%;
          backdrop-filter: blur(8px)">
      <div>
        <div class="loginPic" style=" width: 410px;height:600px;background-color: #409EFF;border-radius: 2%"></div>
      </div>
      <div  style="margin-right: 42px;
            width:500px;
            height: 360px;
            border: 0px #eeeeee solid;
            border-radius: 2.5%;"
            :style="{boxShadow: `var(${'--el-box-shadow-dark'})`}"><!--            边框阴影-->
        <div style="width:500px;height: 100px;line-height: 100px;font-size: 30px;text-align: center;letter-spacing:10px;">欢迎登录</div>
          <div>
            <el-form :model="form"
                     size="large"
                     :rules="rules"
                     status-icon ref="form"
                     label-width="165px">
              <el-form-item prop="adminName" label="用户名">
                <el-input focus v-model="form.adminName"
                          placeholder="请输入用户名"
                          style="font-size: 15px;height: 40px;width: 200px"/>
              </el-form-item>
              <el-form-item prop="adminPwd" label="密码">
                <el-input v-model="form.adminPwd"
                          placeholder="请输入密码"
                          show-password
                          style="font-size: 15px;height: 40px;width: 200px"/>
              </el-form-item>
              <div  style="display:flex;
                    justify-content:center;align-items: center;">
                <div  style="width: 150px;height: 70px;
                      display: flex;justify-content: center;align-items: center">
                  <el-button color="#FC9F19FF"
                             style="display: inline-block ;
                             color: #fbfbfc;
                             font-size: 15px "
                             @click="login"
                             class="login"><div class="tip">登 录</div>
                    <div class="loading">
                      <div></div>
                      <div></div>
                      <div></div>
                    </div>
                  </el-button>
                </div>
              </div>
            </el-form>
          </div>
      </div>
    </div>
  </div>
</template>


<script>
import request from "../utils/request";
import $ from 'jquery'

export default {
  name: "Login",
  data(){
    return {
      form:{},
      rules:{
        adminName:[
          { required: true, message: '请输入账号', trigger:'blur'},
        ],
        adminPwd:[
          { required: true, message: '请输入密码', trigger:'blur'},
        ]
      }
    }
  },
  mounted(){
    this.loadingActive()
  },
  methods:{
    login(){
      this.$refs['form'].validate((valid) => {
        //如果输入不符合前端表单约束，则不发送request请求
        if(valid) {
          request.post('user/login',this.form).then(res => {
            if(res.code === '0'){
              this.$message({
                type:"success",
                message:"登录成功"
              })
              sessionStorage.setItem('user',JSON.stringify(res.data))  //缓存用户信息
              this.$router.push('/')    //登录成功后跳转主页
            }else {
              this.$message({
                type:"error",
                message:res.msg
              })
            }
          })
        }else {
          console.log("不合法")
        }
      })
    },
    loadingActive(){
      //修改前端样式
      $(document).ready(function (){
        $('.login').focus(function (){
          //点击后，向该对象增加Class属性/原来没有则添加，有则删除
          $('.login').toggleClass("active")
        });
        $('.login').blur(function (){
          //删除该Class属性
          $('.login').removeClass("active")
        })
      })
    }
  }
}

</script>

<style scoped>
.el-form-item {
  margin-bottom: 40px;
}
/*通过深度样式选择器选择修改不了的样式（如，当前未生成）*/
/*验证提示文字样式*/
.el-form-item >>> .el-form-item__error {
  color: var(--el-color-danger);
  font-size: 15px;
  line-height: 1;
  padding-top: 5px;
  position: absolute;
  top: 100%;
  left: 15px;
}
/*密码可见按钮大小*/
.el-form-item >>> .el-icon.el-input__icon.el-input__password {
  font-size: 15px;
}
.loginPic {
  background: url(../assets/images/loginpic.jpg) no-repeat center center;
  background-size: 100% 100%;
}
/*.bg1 {*/
/*  background: url(../assets/images/loginpic.jpg) no-repeat center center;*/
/*  background-size: 100% 100%;*/
/*}*/




/*  button起始状态  */
/*.el-button {*/
/*  position: relative;*/
/*  height:40px;*/
/*  width: 100px;*/
/*  text-align: center;*/
/*  border-radius: var(--el-border-radius-round);*/
/*  transition: .5s;*/
/*}*/
/*!*  button末状态  *!*/
/*.el-button:focus {*/
/*  position: relative;*/
/*  height: 75px;*/
/*  width: 75px;*/
/*  border-radius: var(--el-border-radius-circle);*/
/*}*/


/*.el-button:focus .tip {*/
/*  opacity: 0;*/
/*}*/
/*.el-button:focus .loading {*/
/*  position: absolute;*/
/*  top: 50%;*/
/*  left: 50%;*/
/*  transform: translate(-50%,-50%);*/
/*  display: flex;*/
/*  justify-content: space-around;*/
/*  align-items: center;*/
/*  opacity: 1;*/
/*  width: 40px;*/
/*  height: 40px;*/
/*}*/
/*.el-button:focus .loading div{*/
/*  width: .3rem;*/
/*  height: .3rem;*/
/*  background-color: #fbfbfc;*/
/*  border-radius: 50%;*/
/*  animation: .3s loading ease-in-out infinite alternate;*/
/*}*/
/*@keyframes loading {*/
/*  to{*/
/*    transform: translate(0,-.5rem);*/
/*  }*/
/*}*/
/*.el-button:focus .loading div:nth-child(2){*/
/*  animation-delay: .1s;*/
/*}*/
/*.el-button:focus .loading div:nth-child(3){*/
/*  animation-delay: .2s;*/
/*}*/

.login {
  position: relative;
  height:40px;
  width: 100px;
  text-align: center;
  border-radius: var(--el-border-radius-round);
  transition: .5s;
}
.active {
    position: relative;
    height: 75px;
    width: 75px;
    border-radius: var(--el-border-radius-circle);
}
.active .tip {
  opacity: 0;
}
.active .loading{
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%,-50%);
  display: flex;
  justify-content: space-around;
  align-items: center;
  opacity: 1;
  width: 40px;
  height: 40px;
}
.active .loading div{
    width: .3rem;
    height: .3rem;
    background-color: #fbfbfc;
    border-radius: 50%;
    animation: .3s loading ease-in-out infinite alternate;
}
@keyframes loading {
  to {
    transform: translate(0, -.5rem);
  }
}
.active .loading div:nth-child(2){
  animation-delay: .1s;
}
.active .loading div:nth-child(3){
  animation-delay: .2s;
}
</style>