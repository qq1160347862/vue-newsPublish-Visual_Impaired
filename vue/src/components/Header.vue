<template>
  <div class="headerMain"
       style="height: 50px;
       line-height: 50px;
       border-bottom: 1px solid #ccc;
       display: flex ;
       width: 100%;
       background-color: #eeeeee">
    <div style="flex: 1"></div>
    <div style="width: 100px;margin-right: 50px">

    </div>
  </div>
</template>

<script>
import { ArrowDown } from '@element-plus/icons-vue'
import $ from "jquery"
import {ref, watch} from "vue";
import bus from "../assets/js/index"

// watch(Flag,(newValue,oldValue) =>{
//   console.log('watch 已触发',newValue)
//   if(Flag == true){
//     console.log('Flag为true')
//   }else {
//     console.log('Flag为false')
//   }
// },{deep:true})

export default {
  name: "Header",
  components:{ArrowDown},
  methods:{

  },
  data(){
    return{
      // Flag:true
    }
  },
  mounted() {
    // let _this = this
    // bus.$on('isClose',function (msg){
    //   _this.Flag = msg
    //   console.log(msg)
    // })
  },
  watch:{

    // Flag:{
    //   deep:true,
    //   handler(newValue,oldValue){
    //     console.log("watch!!!!")
    //     if (newValue == true){
    //       $('.headerMain').css("width","calc(100% + 180px)")
    //     }else if (newValue == false){
    //       console.log("123")
    //       $('.headerMain').css("width","100%")
    //       // this.$refs.headerMain.style.width = '100%'
    //     }
    //   }
    // }

  }

}



</script>

<style scoped>
.headerMain {
  transition: .4s;
}
</style>