import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'


import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'


import echarts from 'echarts'  //引入Echarts，

import jquery from "jquery";

import '@/assets/css/global.css'

import myEvent from './assets/js/index.js'


createApp(App).use(myEvent).use(store).use(jquery).use(ElementPlus).use(router).mount('#app')
