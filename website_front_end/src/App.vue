<template>
  <div id="app"
       v-loading="$store.state.loading"
       element-loading-text="加载中"
       element-loading-background="rgba(255, 255, 255, 0.5)">
    <router-view/>
  </div>
</template>

<script>

export default {
  name: 'App',
  components: {}
}
</script>

<style lang="scss">

@import "./assets/css/common.scss";

#app {
  width: 100%;
  height: 100%;
  min-height: 500px;
}

.el-loading-mask {
  background-color: rgba(220, 220, 220, .6);
}
</style>
