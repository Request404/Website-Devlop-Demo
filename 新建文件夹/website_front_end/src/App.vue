<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>

export default {
  name: 'App',
}
</script>

<style lang="scss">

@import "./assets/css/common";

#app {
  width: 100%;
  height: 100%;
  min-height: 500px;
}

.el-loading-mask {
  background-color: rgba(220, 220, 220, .6);
}
</style>
