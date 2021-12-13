import Vue from 'vue';
import VueI18n from "vue-i18n";

Vue.use(VueI18n)

import zh from './config/zh';
import en from './config/en';

const i18n = new VueI18n({
  //设置默认语言
  locale: localStorage.getItem('locale') || 'zh',
  messages: {
    zh, //中文
    en  //英语
  }
})

export default i18n;
