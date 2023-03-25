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
      <el-divider content-position="center" style="margin-top: 40px">新闻类型列表</el-divider>
      <div>
        <el-table ref="multipleTableRef"
                  :data="tableData"
                  width="100"
                  height="calc(100vh - 100px)"
                  max-height="500"
                  @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="100" />
          <el-table-column prop="blank" label="" width="70"/>
          <el-table-column prop="type" label="标签" width="100"/>
          <el-table-column prop="blank2" label="" width="130"/>
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
            format="YYYY-MM-DD"
            value-format="YYYY-MM-DD"
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
  name: "PublishTrendView",
  setup(){

  },
  data() {
    return {
      Flag:false,

      Daterangepickvalue:ref(''),
      DatepickShape:'large',//时间选择器尺寸大小
      chart:null,     //统计表格

      timelist:[],
      serieslist:[],

      search:'',          //搜索新闻类型关键字
      tableData:[],
      typeData:'',

      wfDateTime:this.getWeekFirstDay(),
      weDateTime:this.getWeekLastDay(),
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
    //加载新闻种类
    load(){
      request.get("/publishTrend/newType",{
        params:{
          search : this.search
        }
      }).then(res => {
        //将后台数据赋给表格
        console.log(res)
        this.tableData = res.data
      })
    },
    //获取表格选中行数据
    handleSelectionChange(val){
      if(val != null){
        this.typeData=''
        if (val.length==0){ //全选取消时重新发出请求，用了两个空格符避免与接口一个空格的情况相符
          this.typeData="  "
        }
        for (let i = 0; i < val.length; i++){
          this.typeData=this.typeData+'&'+val[i].type
        }
        console.log(this.typeData)
      }
      this.getTimerangeselected()
    },
    getTimerangeselected(){
      request.get('/publishTrend',{
        params:{
          Daterangepickvalue:this.Daterangepickvalue.toString(),
          newsTypeSelected:this.typeData
        }
      }).then(res => {
        console.log(res)
        this.timelist=[]
        for(let i=0;i<res.data[1];i++){
          this.timelist.push(res.data[0][i].toString().split('-')[2])
        }

        this.serieslist =[]
        this.serieslist = res.data[2]

        this.chart.setOption({
          title: {
            text: this.Daterangepickvalue.toString().split(',')[0]+' 至 '+
                this.Daterangepickvalue.toString().split(',')[1]+' 内各类别新闻的发布趋势',
            x: 'center'
          },
          xAxis: {
            data: this.timelist
          },
          yAxis: {},
          series: this.serieslist
        },true) //    一定要加true！！默认是false 即融合数据
      })
    },
    //获取新闻分类
    getJson(){
      let echarts = inject("echarts")
      this.Daterangepickvalue=[this.wfDateTime,this.weDateTime]
      request.get("/browserTrend",{
        params:{
          Daterangepickvalue:this.Daterangepickvalue.toString(),
          newsTypeSelected:this.typeData
        }
      }).then(res => {
        console.log(res)
        for(let i=0;i<res.data[1];i++){
          this.timelist.push(res.data[0][i].toString().split('-')[2])
        }
        this.chart = echarts.init(document.getElementById('chart1'),'westeros',{
          width: 900,
          height: 600
        })
        this.chart.showLoading()
        this.chart.setOption({
          title: {
            text: this.wfDateTime+' 至 '+
                this.weDateTime+' 内各类别新闻的发布趋势',
            x: 'center'
          },
          xAxis: {
            data: this.timelist
          },
          yAxis: {},
          series: this.serieslist
        })
        this.chart.hideLoading()
      })
    },
    getWeekFirstDay(){
      let now = new Date()
      let nowTime = now.getTime()
      let day = now.getDay() || 7
      let oneDayTime = 24*60*60*1000
      let MondayTime = nowTime - (day-1)*oneDayTime;
      let Monday = new Date(MondayTime)
      let MondayString = Monday.getFullYear()+'-'+(Monday.getMonth()+1)+'-'+Monday.getDate()

      return MondayString
    },
    getWeekLastDay(){
      let now = new Date()
      let nowTime = now.getTime()
      let day = now.getDay() || 7
      let oneDayTime = 24*60*60*1000
      let SundayTime = nowTime + (7-day)*oneDayTime
      let Sunday = new Date(SundayTime)
      let SundayString = Sunday.getFullYear()+'-'+(Sunday.getMonth()+1)+'-'+Sunday.getDate()

      return SundayString
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