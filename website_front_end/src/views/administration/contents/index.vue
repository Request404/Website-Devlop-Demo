<template>
  <div id="home_page_pane">
    <side-bar/>
    <div id="home_page_container">
      <nav-bar/>
      <router-view/>
    </div>
  </div>
</template>

<script>
import SideBar from '../components/side-bar'
import NavBar from '../components/nav-bar'

export default {
  name: "index",
  components: {SideBar, NavBar},
  beforeCreate() {
    if (!sessionStorage.getItem("website-user-info")) {
      this.$router.push('/administration/login')
    }
  }
}
</script>

<style scoped lang="scss">
#home_page_pane {
  width: 100%;
  height: 100%;
  display: flex;

  #home_page_container {
    width: calc(100% - 240px);
    height: 100%;
  }
}
</style>
