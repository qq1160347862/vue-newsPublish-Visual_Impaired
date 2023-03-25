<template>
<div>
  <!--    头部-->
  <Header style="position: absolute;z-index: -1"/>
  <div style="display:flex;">
    <!--    侧边栏-->
    <Nav/>
    <!--    主体-->
    <div class="mainFrame" style="min-width: calc(100% - 182px);">
      <!--    内容-->
      <router-view style="flex: 1"/>
    </div>
  </div>
</div>
</template>

<script>
import Header from "@/components/Header";
import Nav from "@/components/Nav";
import bus from "../assets/js/index"
import $ from "jquery"
export default {
  name: "Layout",
  components:{
    Header,
    Nav,
  },
  data(){
    return{
      Flag:false
    }
  },
  mounted() {
    let _this = this
    bus.$on('isClose',function (msg){
      _this.Flag = msg
    })
  },
  watch:{

    Flag:{
      deep:true,
      immediate:true,
      handler(newValue,oldValue){
        if (newValue == true){
          $('.mainFrame').css("width","calc(100% + 180px)")
        }else if (newValue == false){
          $('.mainFrame').css("width","100%")
        }
      }
    }

  }
}
</script>

<style scoped>

</style>