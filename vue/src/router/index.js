import { createRouter, createWebHistory } from 'vue-router'
import Layout from '../Layout/Layout.vue'

const routes = [
  {
    path: '/',
    name: 'Layout',
    component: Layout,
    redirect:'/News',
    children:[
      {
        path: 'News',
        name: 'News',
        component: () => import('../views/NewsView'),
      },
      {
        path: 'User',
        name: 'User',
        component: () => import('../views/UserView'),
      },
      {
        path: 'Statistics',
        name: 'Statistics',
        component: () => import('../views/StatisticsView'),
      },
      {
        path: 'UserStatistics',
        name: 'UserStatistics',
        component: () => import('../views/UserStatisticsView'),
      },
      {
        path: 'BrowserTrend',
        name: 'BrowserTrend',
        component: () => import('../views/BrowserTrendView'),
      },
      {
        path: 'PublishTrend',
        name: 'PublishTrend',
        component: () => import('../views/PublishTrendView'),
      },
    ]
  },
  {
    path: '/Login',
    name: 'Login',
    component: ()=> import('../views/Login')
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
