<template>
  <div  style="display: flex;justify-content: center;
        margin: 60px 10px 0 10px;padding: 5px;
        position: relative;
        border-radius: 1%;background-color: #ffffff;"
        :style="{
         boxShadow: `var(${'--el-box-shadow-dark'})`
       }">
    <div class="tip" style="position: absolute;z-index: 0;
                    width: 150px;height: 300px;
                    top: 30%;left: 28.8%;transform: translate(0,-50%);
                    border-radius: 5%"
         :style="{
                    boxShadow: `var(${'--el-box-shadow-dark'})`}">
      <div  style="width: 50px;height: 50px;border-radius: 50%;"></div>
      <div style="font-size: 14px;text-align: center;
            height: 50px;line-height: 50px">
        <p>生活就像咖啡,自信如同沸腾的热水,两者融合就能香浓四溢~</p>
      </div>
    </div>

<!--    <div style="height: calc(100vh - 100px);width: 400px;-->
<!--         border-radius: 2.5%;margin-left: 5px;"-->
<!--         :style="{-->
<!--         boxShadow: `var(${'&#45;&#45;el-box-shadow-dark'})`}">-->
<!--    </div>-->

    <div class="chartArea"  style="border-radius: 2.5%;height: calc(100vh - 100px);margin-left:20px;
                            position: relative;z-index: 1"
         :style="{
                            boxShadow: `var(${'--el-box-shadow-dark'})`}">
      <div id="chart1" style="width: 900px;height:600px;"></div>
      <div class="daterangepicker" style="position: absolute;left: 50%;transform: translate(-50%,-50%)">
        <el-date-picker
            v-model="Daterangepickvalue"
            format="YYYY-MM-DD HH:mm:ss"
            value-format="YYYY-MM-DD HH:mm:ss"
            type="datetimerange"
            range-separator="To"
            start-placeholder="起始时间"
            end-placeholder="终止时间"
            :size="DatepickShape"
            :default-value="[
              new Date(2022, 5, 1, 12, 0, 0),
              new Date(2022, 7, 1, 8, 0, 0),
            ]"
            @change="this.getTimerangeselected"
        />
      </div>
    </div>

  </div>
</template>

<script>
import { ref } from 'vue'
import {inject,onMounted} from "vue";
import themeJSON from '@/assets/js/westeros.js'
import request from "../utils/request";
import bus from "../assets/js/index"
import $ from "jquery"

export default {
  name: "StatisticsView",
  setup(){

  },
  data() {
    return {
      Flag:false,

      Daterangepickvalue:[],
      DatepickShape:'large',//时间选择器尺寸大小
      statisticsType:1,
      chart:null,     //统计表格

      search:'',          //搜索新闻类型关键字
      tableData:[],
      typeData:'',


      nowDateTime:new Date().getFullYear()+'-'+(new Date().getMonth()+1)+'-'+
          new Date().getDate(),
      tomorrowDateTime:new Date().getFullYear()+'-'+(new Date().getMonth()+1)+'-'+
          (new Date().getDate()+1),
    }
  },
  //页面启动时且在DOM加载完毕时自动加载数据
  mounted() {
    this.getJson()
    let _this = this
    bus.$on('isClose',function (msg){
      _this.Flag = msg
    })
    // this.$nextTick(() => {
    //
    // })
  },
  beforeUnmount() {
    console.log('beforeUnmount')
    if (!this.chart) {
      return
    }
    // window.removeEventListener('resize', this.__resizeHandler)
    this.chart.dispose()
    this.chart = null
  },
  methods:{

    getTimerangeselected(){
      console.log(this.Daterangepickvalue)
      request.get('/statistics',{
        params:{
          statisticsType:this.statisticsType,
          Daterangepickvalue:this.Daterangepickvalue.toString()
        }
      }).then(res => {
        console.log(res.data)
        this.chart.setOption({
          aria: {
            show: true,
            enabled:true,
            label:{
              //可自定义无障碍语音描述
              description:'这是一个新闻统计图表'
            },
            decal: {    //无障碍花纹
              show: false
            }
          },
          title: {
            text: this.Daterangepickvalue.toString().split(',')[0]+' 至 '+
                  this.Daterangepickvalue.toString().split(',')[1]+' 内的不同类型新闻数量',
            x: 'center'
          },
          axisLabel: {
            interval:0,
            // rotate:40,
            formatter:function(value){
              let ret = "";  //拼接加\n返回的类目项
              let maxLength = 2;  //每项显示文字个数
              let valLength = value.length;  //X轴类目项的文字个数
              let rowN = Math.ceil(valLength / maxLength); //类目项需要换行的行数
              if (rowN > 1) { //如果类目项的文字大于3,
                for (let i = 0; i < rowN; i++) {
                  let temp = "";//每次截取的字符串
                  let start = i * maxLength;//开始截取的位置
                  let end = start + maxLength;//结束截取的位置
                  //这里也可以加一个是否是最后一行的判断，但是不加也没有影响，那就不加吧
                  temp = value.substring(start, end) + "\n";
                  ret += temp; //凭借最终的字符串
                }
                return ret;
              }else {
                return value;
              }
            }
          },
          xAxis: {
            data: res.data[0]
          },
          yAxis: {},
          series: [
            {
              type: 'bar',
              data: res.data[1],
              label: {
                show: true,
                position: 'top',
                textStyle: {
                  fontSize: 16
                }
              },
            }
          ]
        },true)
      })
    },
    //获取新闻分类
    getJson(){
      let echarts = inject("echarts")
      this.Daterangepickvalue=[this.nowDateTime,this.tomorrowDateTime]
      request.get("/statistics",{
        params:{
          statisticsType:this.statisticsType,
          Daterangepickvalue:this.Daterangepickvalue.toString()
        }
      }).then(res => {
        console.log(res)
        this.chart = echarts.init(document.getElementById('chart1'),'westeros',{
          width: 900,
          height: 600
        })
        // this.chart.showLoading()
        this.chart.setOption({
          aria: {
            show: true,
            enabled:true,
            label:{
              //可自定义无障碍语音描述
              description:'这是一个新闻统计图表'
            },
            decal: {    //无障碍花纹
              show: false
            }
          },
          title: {
            text: this.nowDateTime+'  不同类型新闻数量',
            x: 'center'
          },
          axisLabel: {
            interval:0,
            // rotate:40,
            formatter:function(value){
              let ret = "";  //拼接加\n返回的类目项
              let maxLength = 2;  //每项显示文字个数
              let valLength = value.length;  //X轴类目项的文字个数
              let rowN = Math.ceil(valLength / maxLength); //类目项需要换行的行数
              if (rowN > 1) { //如果类目项的文字大于3,
                for (let i = 0; i < rowN; i++) {
                  let temp = "";//每次截取的字符串
                  let start = i * maxLength;//开始截取的位置
                  let end = start + maxLength;//结束截取的位置
                  //这里也可以加一个是否是最后一行的判断，但是不加也没有影响，那就不加吧
                  temp = value.substring(start, end) + "\n";
                  ret += temp; //凭借最终的字符串
                }
                return ret;
              }else {
                return value;
              }
            }
          },
          xAxis: {
            data: res.data[0]
          },
          yAxis: {},
          series: [
            {
              type: 'bar',
              data: res.data[1],
              label: {
                show: true,
                position: 'top',
                textStyle: {
                  fontSize: 16
                }
              },
            }
          ]
        })
        // this.chart.hideLoading()
        //可用于无障碍语音
        console.log(document.getElementById('chart1').ariaLabel)
      })
    },
  },
  watch:{

    Flag:{
      deep:true,
      immediate:true,
      handler(newValue,oldValue){
        if (newValue == true){
          $('.tip').css("opacity","0")
        }else if (newValue == false){
          $('.tip').css("opacity","1")
        }
      }
    }

  }
}
</script>

<style scoped>
.tip {
  opacity: 0;
  transition-property: opacity;
  transition-duration: .4s;
  transition-timing-function: ease-in-out;
}
</style>