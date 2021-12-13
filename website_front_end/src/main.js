import Vue from 'vue'
import router from "@/router";
import store from "@/store";
import api from '@/api';
import utils from '@/utils';
import i18n from '@/i18n';
import '@/assets/css/base.scss'
import ViewUI from 'view-design';
import 'view-design/dist/styles/iview.css';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import App from './App.vue'

Vue.prototype.api = api
Vue.prototype.utils = utils

Vue.use(ViewUI);
Vue.use(ElementUI)

Vue.config.productionTip = false

new Vue({
  render: h => h(App),router,store,i18n
}).$mount('#app')

