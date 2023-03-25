<template>
  <div  style="display: flex;justify-content: space-between;
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
        <p>请善用搜索功能~</p>
      </div>
    </div>

    <div style="height: calc(100vh - 100px);width: 400px;
         border-radius: 2.5%;margin-left: 5px;"
         :style="{
         boxShadow: `var(${'--el-box-shadow-dark'})`}">
      <div style="margin: 10px 0;padding: 10px 10px;overflow: hidden">
        <el-input v-model="search" clearable placeholder="请输入内容" style="width: 60%;float: left"/>
        <el-button type="primary" @click="load" style="float: right" >查询</el-button>
      </div>
      <el-divider content-position="center" style="margin-top: 40px">用户列表</el-divider>
      <div style="width: 100%;">
        <el-table
            ref="singleTableRef"
            :data="tableData"
            highlight-current-row
            width="100"
            height="calc(100vh - 100px)"
            max-height="500"

            @current-change="selectUser"
        >
          <el-table-column type="index" width="100" />
          <el-table-column property="userId" label="用户ID" width="150" />
          <el-table-column property="userName" label="用户名字" width="150" />
        </el-table>
      </div>
    </div>

    <div class="chartArea"  style="border-radius: 2.5%;margin-left:20px;
                            position: relative;z-index: 1"
                            :style="{
                            boxShadow: `var(${'--el-box-shadow-dark'})`}">
      <div id="chart1" style="width: 900px;height: 600px"></div>
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
  name: "UserStatisticsView",
  data() {
    return {
      Flag:false,

      Daterangepickvalue:[],
      DatepickShape:'large',//时间选择器尺寸大小
      statisticsType:1,
      chart:null,             //统计表格

      search:'',          //搜索用户关键字
      tableData:[],      //用户表格
      userID:0,          //用户ID
      userName:"X",       //用户名字

      nowDateTime:new Date().getFullYear()+'-'+(new Date().getMonth()+1)+'-'+
          new Date().getDate(),
      tomorrowDateTime:new Date().getFullYear()+'-'+(new Date().getMonth()+1)+'-'+
          (new Date().getDate()+1),
    }
  },
  //页面启动时且在DOM加载完毕时自动加载数据
  mounted() {
    this.load()
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
    //加载用户名单
    load(){
      request.get("/userStatistics/user",{
        params:{
          search : this.search
        }
      }).then(res => {
        console.log(res)
        //将后台数据赋给表格
        this.tableData = res.data
      })
    },
    //获取表格选中行数据
    selectUser(val){
      if (val != null){
        this.userID = val.userId
        this.userName = val.userName
      }
      this.getTimerangeselected()
    },
    //更新统计表格chart1
    getTimerangeselected(){
      console.log(this.Daterangepickvalue)
      console.log(this.userID)
      request.get('/userStatistics',{
        params:{
          statisticsType:this.statisticsType,
          Daterangepickvalue:this.Daterangepickvalue.toString(),
          userID:this.userID
        }
      }).then(res => {
        console.log(res.data)
        this.chart.setOption({
          aria: {
            show: true,
            enabled:true,
            label:{
              //可自定义无障碍语音描述
              description:'这是一个统计不同用户所浏览各个新闻类型的数目表格'
            },
            decal: {    //无障碍花纹
              show: false
            }
          },
          title: {
            text: '用户'+this.userName+'在'+this.Daterangepickvalue.toString().split(',')[0]+' 至 '+
                this.Daterangepickvalue.toString().split(',')[1]+' 内所浏览的各类别新闻数量',
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
    //初始化统计表格
    getJson(){
      let echarts = inject("echarts")
      this.Daterangepickvalue=[this.nowDateTime,this.tomorrowDateTime]
      request.get("/userStatistics",{
        params:{
          statisticsType:this.statisticsType,
          Daterangepickvalue:this.Daterangepickvalue.toString(),
          userID:this.userID
        }
      }).then(res => {
        console.log(res)
        this.chart = echarts.init(document.getElementById('chart1'),'westeros',{
          width: 900,
          height: 600
        })
        this.chart.showLoading()
        this.chart.setOption({
          aria: {
            show: true,
            enabled:true,
            label:{
              //可自定义无障碍语音描述
              description:'这是一个统计不同用户所浏览各个新闻类型的数目表格'
            },
            decal: {    //无障碍花纹
              show: false
            }
          },
          title: {
            text: '用户'+this.userName+'在'+this.nowDateTime+' 至 '+
                this.tomorrowDateTime+' 内所浏览的各类别新闻数量',
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
        this.chart.hideLoading()
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