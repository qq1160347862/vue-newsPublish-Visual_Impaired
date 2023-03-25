<template>
  <div>
    <router-view/>
  </div>

</template>


<script>
import {provide} from "vue"
import * as echarts from "echarts"
export default {
  setup(){
    //将echarts组件传入子页
    provide("echarts",echarts)
  },
  name:"App",

}
</script>

