import VueRouter from 'vue-router'
import Vue from 'vue'

import ViewUI from 'view-design';
Vue.use(ViewUI);

const Index = () => import('@/views/sign-in')
const Home = () => import('@/views/home-page')
const Page404 = () => import('@/views/error/404')
const Admin = () => import('@/views/admin')
const Staff = () => import('@/views/staff')
const Contract = () => import('@/views/contract')
const Workday = () => import('@/views/workday')
const Overtime = () => import('@/views/overtime')
const Evection = () => import('@/views/evection')
const Salaries = () => import('@/views/salaries')

const routerPush = VueRouter.prototype.push
VueRouter.prototype.replace = function push(location) {
  return routerPush.call(this, location).catch(error => error)
}
const routerReplace = VueRouter.prototype.replace
VueRouter.prototype.replace = function replace(location) {
  return routerReplace.call(this, location).catch(error => error)
}


Vue.use(VueRouter)


const routes = [
  {
    path: '',
    redirect: '/index'
  }, {
    path: '/index',
    component: Index,
    meta: {
      title: '企业管理系统-登录',
    }
  }, {
    path: '/home',
    component: Home,
    redirect: '/home/admin',
    children: [
      {
        path: 'admin',
        component: Admin,
        meta: {
          title: '企业管理系统-管理员管理',
        }
      },{
        path: 'staff',
        component: Staff,
        meta: {
          title: '企业管理系统-员工管理',
        }
      },{
        path: 'contract',
        component: Contract,
        meta: {
          title: '企业管理系统-合同管理',
        }
      },{
        path: 'workday',
        component: Workday,
        meta: {
          title: '企业管理系统-打卡详情',
        }
      },{
        path: 'overtime',
        component: Overtime,
        meta: {
          title: '企业管理系统-加班记录',
        }
      },{
        path: 'evection',
        component: Evection,
        meta: {
          title: '企业管理系统-员工出差',
        }
      },{
        path: 'salaries',
        component: Salaries,
        meta: {
          title: '企业管理系统-员工薪水',
        }
      }
    ]
  }, {
    path: '*',
    component: Page404,
    meta: {
      title: '找不到该页面',
    }
  }
]


const router = new VueRouter({
  routes,
  mode: 'history'

})

router.beforeEach((to, from, next) => {
  document.title = to.meta.title
  ViewUI.LoadingBar.start();
  next()
})

router.afterEach( () => {
  ViewUI.LoadingBar.finish();
});

export default router
