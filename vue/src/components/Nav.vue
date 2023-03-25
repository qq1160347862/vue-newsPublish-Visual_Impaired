<template>
  <el-button class="navButton" @click="navFlag">隐藏侧栏</el-button>
  <div class="navMain">

    <el-menu
        style="min-height: 100vh;"
        :default-active="$route.path.substring(1)"
        router
        :collapse=isCollapse
        :collapse-transition="true"
        text-color="#fff"
        active-text-color="#ffd04b"
        background-color="#3f6576"
        mode="vertical"
        class="el-menu-vertical-demo">

      <div class="navTitle"><p>后台管理</p></div>
      <div style="height: 50px"></div>

      <el-menu-item index="News">
        <el-icon><document /></el-icon>
        <span>新闻管理</span>
      </el-menu-item>
      <el-menu-item index="User">
        <svg viewBox="0 0 1536 1024" xmlns="http://www.w3.org/2000/svg"
             data-v-78e17ca8=""><path fill="currentColor" d="M628.736 528.896A416 416 0 0 1 928 928H96a415.872 415.872 0 0 1 299.264-399.104L512 704l116.736-175.104zM720 304a208 208 0 1 1-416 0 208 208 0 0 1 416 0z"></path></svg>
        <span>用户管理</span>
      </el-menu-item>
      <div style="height: calc(100vh - 500px)"></div>
      <el-menu-item index="Statistics">
        <svg viewBox="0 0 1536 1024" xmlns="http://www.w3.org/2000/svg" data-v-78e17ca8="">
          <path fill="currentColor" d="m665.216 768 110.848 192h-73.856L591.36 768H433.024L322.176 960H248.32l110.848-192H160a32 32 0 0 1-32-32V192H64a32 32 0 0 1 0-64h896a32 32 0 1 1 0 64h-64v544a32 32 0 0 1-32 32H665.216zM832 192H192v512h640V192zM352 448a32 32 0 0 1 32 32v64a32 32 0 0 1-64 0v-64a32 32 0 0 1 32-32zm160-64a32 32 0 0 1 32 32v128a32 32 0 0 1-64 0V416a32 32 0 0 1 32-32zm160-64a32 32 0 0 1 32 32v192a32 32 0 1 1-64 0V352a32 32 0 0 1 32-32z"></path></svg>
        <span>新闻统计</span>
      </el-menu-item>
      <el-menu-item index="UserStatistics">
        <svg viewBox="0 0 1536 1024" xmlns="http://www.w3.org/2000/svg" data-v-78e17ca8="">
          <path fill="currentColor" d="m665.216 768 110.848 192h-73.856L591.36 768H433.024L322.176 960H248.32l110.848-192H160a32 32 0 0 1-32-32V192H64a32 32 0 0 1 0-64h896a32 32 0 1 1 0 64h-64v544a32 32 0 0 1-32 32H665.216zM832 192H192v512h640V192zM352 448a32 32 0 0 1 32 32v64a32 32 0 0 1-64 0v-64a32 32 0 0 1 32-32zm160-64a32 32 0 0 1 32 32v128a32 32 0 0 1-64 0V416a32 32 0 0 1 32-32zm160-64a32 32 0 0 1 32 32v192a32 32 0 1 1-64 0V352a32 32 0 0 1 32-32z"></path></svg>
        <span>用户浏览</span>
      </el-menu-item>
      <el-menu-item index="BrowserTrend">
        <svg viewBox="0 0 1536 1024" xmlns="http://www.w3.org/2000/svg" data-v-78e17ca8="">
          <path fill="currentColor" d="M359.168 768H160a32 32 0 0 1-32-32V192H64a32 32 0 0 1 0-64h896a32 32 0 1 1 0 64h-64v544a32 32 0 0 1-32 32H665.216l110.848 192h-73.856L591.36 768H433.024L322.176 960H248.32l110.848-192zM832 192H192v512h640V192zM342.656 534.656a32 32 0 1 1-45.312-45.312L444.992 341.76l125.44 94.08L679.04 300.032a32 32 0 1 1 49.92 39.936L581.632 524.224 451.008 426.24 342.656 534.592z"></path></svg>
        <span>浏览趋势</span>
      </el-menu-item>
      <el-menu-item index="PublishTrend">
        <svg viewBox="0 0 1536 1024" xmlns="http://www.w3.org/2000/svg" data-v-78e17ca8="">
          <path fill="currentColor" d="M359.168 768H160a32 32 0 0 1-32-32V192H64a32 32 0 0 1 0-64h896a32 32 0 1 1 0 64h-64v544a32 32 0 0 1-32 32H665.216l110.848 192h-73.856L591.36 768H433.024L322.176 960H248.32l110.848-192zM832 192H192v512h640V192zM342.656 534.656a32 32 0 1 1-45.312-45.312L444.992 341.76l125.44 94.08L679.04 300.032a32 32 0 1 1 49.92 39.936L581.632 524.224 451.008 426.24 342.656 534.592z"></path></svg>
        <span>发布趋势</span>
      </el-menu-item>
      <el-menu-item style="position: relative">
        <el-button  class="exitButton" color="#3f6576" @click="clearSession" >
          <el-icon style="margin-right: 4px"><svg viewBox="0 0 1100 1024" xmlns="http://www.w3.org/2000/svg"
            data-v-78e17ca8=""><path fill="currentColor" d="M764.288 214.592 512 466.88 259.712 214.592a31.936 31.936 0 0 0-45.12 45.12L466.752 512 214.528 764.224a31.936 31.936 0 1 0 45.12 45.184L512 557.184l252.288 252.288a31.936 31.936 0 0 0 45.12-45.12L557.12 512.064l252.288-252.352a31.936 31.936 0 1 0-45.12-45.184z"></path></svg></el-icon>
          退出系统
        </el-button>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script>
import {
  Document,
  Menu as IconMenu,
  Location,
  Setting,
} from '@element-plus/icons-vue'
import $ from "jquery";
import bus from "../assets/js/index"

let isClose = true;
let num = 0;
export default {
  name: "Nav",
  components:{
    Document,
    IconMenu,
    Location,
    Setting,
  },
  data(){
    return {
      isCollapse:false,
      isOpacity:1,
    }
  },
  methods:{
    openNav(){
      //修改前端样式
      $(document).ready(function (){
        $('.navButton').click(function (){
          //点击后，向该对象增加Class属性/原来没有则添加，有则删除
          $('.navMain').toggleClass("active")
          $('.navButton').toggleClass("active")
          $('.el-menu-vertical-demo').toggleClass("active")
          $('.navTitle').toggleClass("active")
          $('.el-menu-item').toggleClass("active")
          if(num%2 == 0){
            $('.navButton span').text("打开侧栏")
            isClose = true
            num++
          }else{
            $('.navButton span').text("隐藏侧栏")
            isClose = false
            num++
          }
        });
      })
    },
    navFlag(){
      bus.$emit('isClose',isClose)
    },
    clearSession(){
      sessionStorage.removeItem('user')
      this.$router.push('/login')
    },
  },
  mounted() {
    this.openNav()
  }
}

</script>

<style scoped>
/*侧栏字体大小*/
span {
  font-size: 18px;
}
/*侧栏主控件*/
.navMain{
  position: relative;
  width: 200px;
  height: 100vh;
  margin-right: 2px;

  box-shadow: 1px 1px 10px #cccccc;

  transition: .4s;
  transition-timing-function: ease-in-out;
}
.active.navMain {
  width: 0;
}
/*侧栏菜单项*/
.el-menu-item {
  font-size: 18px;
  left: 0;
  width:179px;
  height: 56px;
  padding-right: 56px;

  transition-property: opacity,left;
  transition-duration: 2s,.4s;
}
.active.el-menu-item {
  opacity: 0;
  left: -800px;
}
.active.el-menu-item.is-active {
  opacity: 0;
  left: -800px;
}

/*离开按钮*/
.exitButton {
  font-size: 18px;
  position: absolute;
  left: 0;
  width:179px;
  height: 56px;
  padding-right: 56px;
  /*transition: opacity,left .4s;*/
}

/*侧栏标题*/
.navTitle {
  display: block;
  height: 50px;
  width: 179px;
  line-height: 50px;
  text-align: center;
  font-size: 30px;
  color: #eeeeee;
  z-index: 1;

  transition: 1s;
  transition-timing-function: ease-in-out;
}
.active.navTitle {
  opacity: 0;
}

/*侧栏标题文字*/
.navTitle p{
  letter-spacing:10px;
}
.active.navTitle p{
  opacity: 0;
  width: 0;
}

/*侧栏展开or关闭按钮*/
.navButton {
  position: absolute;
  top: 50%;
  left: 100px;
  transform: translate(0,-50%);
  width: 100px;
  height: 100px;
  border-radius: 50%;
  border-color: #3f6576;
  background-color: #10b1f3;
  box-shadow: 0px 0px 8px #10b1f3;
  color: #eeeeee;
  font-size: 18px;
  transition: .3s;
  transition-timing-function: ease-in-out;
  z-index: 4;
}
.active.navButton {

  width: 50px;
  height: 50px;
  border-radius: 50%;
  top: 25px;
  left: 25px;
  border-color: #3f6576;
  background-color: #10b1f3;
  color: #eeeeee;
  font-size: 10px;
  /*opacity: 0;*/
}
/* navButton的hover设置*/
.navButton:hover {
  color: #eeeeee;
  border-color: #3f6576;
  background-color: #10b1f3;
}
.navButton:focus {
  color: #eeeeee;
  border-color: #3f6576;
  background-color: #10b1f3;
}
.navButton:active {
  color: #eeeeee;
  border-color: #1b6ba8;
  background-color: #1b6ba8;
}
/* exitButton的hover设置50,81,94*/
.exitButton:hover {
  color: #eeeeee;
  border-color: #32515E;
  background-color: #32515E;
}
.exitButton:focus {
  color: #eeeeee;
  border-color: #32515E;
  background-color: #32515E;
}
.exitButton:active {
  border-color: #32515E;
  background-color: #32515E;
}

  /*修改el-menu按钮页面的层次为负1*/
.el-menu-vertical-demo {
  /*position: absolute;*/
  /*left: 0px;*/
  opacity: 1;
}
.active.el-menu-vertical-demo {
  z-index: -1;
}
/*修改高亮字体的颜色*/
.el-menu-item.is-active {
  color: var(--el-menu-active-color);
}





</style>