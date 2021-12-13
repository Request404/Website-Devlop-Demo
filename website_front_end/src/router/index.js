import VueRouter from 'vue-router'
import Vue from 'vue'

import ViewUI from 'view-design';

Vue.use(ViewUI);

const Index = () => import('@/views/index')
const Home = () => import('@/views/home')
const BookPage = () => import('@/views/books')
const MoviesPage = () => import('@/views/movies')
const MusicsPage = () => import('@/views/musics')
const ProductsPage = () => import('@/views/product-info');
const UserInformation = () => import('@/views/user/information')
// const Avatar = () => import('@/components/avatar')
const Account = () => import('@/views/user/account')
const SignIn = () => import('@/views/user/account/sign-in')
const SignUp = () => import('@/views/user/account/sign-up')
const AlterPassword = () => import('@/views/user/account/alter-password')
const Page401 = () => import('@/views/error/401')
const Page404 = () => import('@/views/error/404')

const Administration = () => import('@/views/administration');
const AdminLogin = () => import('@/views/administration/account');
const AdminContent = () => import('@/views/administration/contents');
const AdminHome = () => import('@/views/administration/contents/home');
const AdminDBooks = () => import('@/views/administration/contents/d-books');
const AdminDMovies = () => import('@/views/administration/contents/d-movies');
const AdminDMusics = () => import('@/views/administration/contents/d-musics');
const AdminDRecord = () => import('@/views/administration/contents/d-record');
const AdminUCollections = () => import('@/views/administration/contents/u-collections');
const AdminUComments = () => import('@/views/administration/contents/u-comments');
const AdminUComplaints = () => import('@/views/administration/contents/u-complaints');
const AdminUReport = () => import('@/views/administration/contents/u-report');
const AdminUScore = () => import('@/views/administration/contents/u-score');
const AdminUser = () => import('@/views/administration/contents/user-admin');
const AdminClientUser = () => import('@/views/administration/contents/user-client');


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
    redirect: '/start',
  },
  {
    path: '/start',
    component: Index,
    meta: {
      title: 'Request404',
    }
  }, {
    path: '/home',
    component: Home,
    meta: {
      title: 'Request404 - Home',
    }
  }, {
    path: '/books',
    component: BookPage,
    meta: {
      title: 'Request404 - Books',
    }
  }, {
    path: '/movies',
    component: MoviesPage,
    meta: {
      title: 'Request404 - Movies',
    }
  }, {
    path: '/musics',
    component: MusicsPage,
    meta: {
      title: 'Request404 - Musics',
    }
  }, {
    path: '/product-information',
    component: ProductsPage,
    meta: {
      title: 'Request404 - Product Information',
    }
  }, {
    path: '/account-information',
    component: UserInformation,
    meta: {
      title: 'Request404 - User Information',
    }
  }, {
    path: '/account',
    component: Account,
    redirect: '/account/sign-in',
    children: [
      {
        path: 'sign-in',
        component: SignIn,
        meta: {
          title: 'Request404 - Sign In',
        }
      }, {
        path: 'sign-up',
        component: SignUp,
        meta: {
          title: 'Request404 - Sign Up',
        }
      }, {
        path: 'alter-password',
        component: AlterPassword,
        meta: {
          title: 'Request404 - Alter Password',
        }
      }
    ]
  }, {
    path: '/administration',
    component: Administration,
    children: [
      {
        path: "login",
        component: AdminLogin,
        meta: {
          title: 'Request404 管理端，请登录',
        }
      }, {
        path: '',
        component: AdminContent,
        redirect: '/administration/home',
        meta: {
          title: 'Request404 管理端，主页',
        },
        children: [
          {
            path: 'home',
            component: AdminHome,
            meta: {
              title: 'Request404 管理端，主页',
            }
          }, {
            path: 'admin-user',
            component: AdminUser,
            meta: {
              title: 'Request404 管理端，用户',
            }
          }, {
            path: 'client-user',
            component: AdminClientUser,
            meta: {
              title: 'Request404 管理端，用户',
            }
          }, {
            path: 'd-books',
            component: AdminDBooks,
            meta: {
              title: 'Request404 管理端，书籍数据',
            }
          }, {
            path: 'd-movies',
            component: AdminDMovies,
            meta: {
              title: 'Request404 管理端，电影数据',
            }
          }, {
            path: 'd-musics',
            component: AdminDMusics,
            meta: {
              title: 'Request404 管理端，音乐数据',
            }
          }, {
            path: 'd-record',
            component: AdminDRecord,
            meta: {
              title: 'Request404 管理端，推荐记录',
            }
          }, {
            path: 'u-collections',
            component: AdminUCollections,
            meta: {
              title: 'Request404 管理端，用户收藏',
            }
          }, {
            path: 'u-comments',
            component: AdminUComments,
            meta: {
              title: 'Request404 管理端，用户评论',
            }
          }, {
            path: 'u-complaints',
            component: AdminUComplaints,
            meta: {
              title: 'Request404 管理端，用户投诉',
            }
          }, {
            path: 'u-report',
            component: AdminUReport,
            meta: {
              title: 'Request404 管理端，用户举报',
            }
          }, {
            path: 'u-score',
            component: AdminUScore,
            meta: {
              title: 'Request404 管理端，用户评分数据',
            }
          }
        ]
      }
    ]
  }, {
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

// let locate = localStorage.getItem("locate");
const router = new VueRouter({
  routes,
  base: process.env.BASE_URL,
  params: {
    en: '1'
  },
  mode: 'history'

})

let locate = localStorage.getItem("locale") || 'zh'
const query = {language: locate}
const transitionTo = router.history.transitionTo // 临时存储底层方法
router.history.transitionTo = function (location, onComplete, onAbort) {
  location = typeof location === 'object' ? {...location, query: {...location.query, ...query}} : {
    path: location,
    query
  }
  transitionTo.call(router.history, location, onComplete, onAbort)
}

router.beforeEach((to, from, next) => {
  document.title = to.meta.title
  ViewUI.LoadingBar.start();
  next()
})

router.afterEach(() => {
  ViewUI.LoadingBar.finish();
});

export default router
