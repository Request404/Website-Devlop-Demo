import VueRouter from 'vue-router'
import Vue from 'vue'

import ViewUI from 'view-design';
Vue.use(ViewUI);

const Home = () => import('@/views/home')
// const Avatar = () => import('@/components/avatar')
const Page401 = () => import('@/views/error/401')
const Page404 = () => import('@/views/error/404')

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
    path: '/',
    component: Home,
    meta: {
      title: 'Request404',
    }
  },
  {
    path: '/401',
    component: Page401,
    meta: {
      title: 'AccessDenied-访问权限不足',
    }
  },
  {
    path: '*',
    component: Page404,
    meta: {
      title: '找不到该页面',
    }
  }
]


const router = new VueRouter({
  routes,
  base: process.env.BASE_URL,
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
