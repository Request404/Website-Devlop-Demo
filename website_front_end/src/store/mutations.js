export default {
  needLoading(state, payload) {
    this.state.loading = payload
  },

  adminLogin(state, payload) {
    this.state.admin = payload
  },

  adminLogout(state, payload) {
    this.state.admin = payload
  }
}
