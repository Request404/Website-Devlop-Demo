import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getters'
import mutations from './mutations'
import actions from './actions'

Vue.use(Vuex)

const  state = {
  backEndUrl: 'http://127.0.0.1:8081/api/',
  loading: false,
  user: {
    username: '',
    password: ''
  }
}

const store = new Vuex.Store({
  state,
  getters,
  mutations,
  actions
})

export default store

